package oauth2

import java.security.SecureRandom
import javax.inject.Inject

import dao._
import models._
import org.joda.time.DateTime

import scala.concurrent.Future
import scala.util.Random
import scalaoauth2.provider._
import scalaoauth2.provider.OAuth2ProviderActionBuilders._

class OAuth2DataHandler @Inject() (clientDAO: OAuthClientDAO, tokenDAO: OAuthAccessTokenDAO,
                                   authCodeDAO: OAuthAuthorizationCodeDAO, userDAO: UserDAO) extends DataHandler[User] {


  override def validateClient(clientCredential: ClientCredential, grantType: String): Future[Boolean] = {
    Future.successful(clientDAO.validate(clientCredential.clientId, clientCredential.clientSecret.getOrElse(""), grantType))
  }

  override def findClientUser(clientCredential: ClientCredential, scope: Option[String]): Future[Option[User]] = {
    Future.successful(Some(clientDAO.findClientCredentials(clientCredential.clientId, clientCredential.clientSecret.getOrElse(""))))
  }

  override def createAccessToken(authInfo: AuthInfo[User]): Future[AccessToken] = {
    val clientId = authInfo.clientId.getOrElse(throw new InvalidClient())
    val oauthClient = Some(clientDAO.findByClientId(clientId)).getOrElse(throw new InvalidClient())

    def randomString(length: Int) = new Random(new SecureRandom()).alphanumeric.take(length).mkString
    val accessToken = randomString(40)
    val refreshToken = randomString(40)
    val createdAt = new DateTime()

    val oauthAccessToken = new OAuthAccessToken()
    oauthAccessToken.setAccount(authInfo.user)
    oauthAccessToken.setClient(oauthClient)
    oauthAccessToken.setAccessToken(accessToken)
    oauthAccessToken.setRefreshToken(refreshToken)
    oauthAccessToken.setCreatedAt(createdAt.toDate)

    tokenDAO.create(oauthAccessToken)

    Future.successful(toAccessToken(oauthAccessToken))
  }

  override def refreshAccessToken(authInfo: AuthInfo[User], refreshToken: String): Future[AccessToken] = {
    val clientId = authInfo.clientId.getOrElse(throw new InvalidClient())

    tokenDAO.deleteByAuthorized(authInfo.user, clientId)
    createAccessToken(authInfo)
  }

  override def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[User]]] = {
    Future.successful(Option(tokenDAO.findByRefreshToken(refreshToken)).map(toAuthInfo))
  }

  override def getStoredAccessToken(authInfo: AuthInfo[User]): Future[Option[AccessToken]] = {
    Future.successful(Option(tokenDAO.findByAuthorized(authInfo.user, authInfo.clientId.getOrElse(""))).map(toAccessToken))
  }

  override def findAuthInfoByCode(code: String): Future[Option[AuthInfo[User]]] = {
    Future.successful(Some(authCodeDAO.findByCode(code)).map(toAuthInfo))
  }

  override def findUser(username: String, password: String): Future[Option[User]] = {
    Future.successful(Option(userDAO.findByNameAndPassword(username, password)))
  }

  override def deleteAuthCode(code: String): Future[Unit] = {
    Future.successful(authCodeDAO.deleteByCode(code))
  }

  override def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[User]]] = {
    Future.successful(Option(tokenDAO.findByAccessToken(accessToken.token)).map(toAuthInfo))
  }

  override def findAccessToken(token: String): Future[Option[AccessToken]] = {
    Future.successful(Option(tokenDAO.findByAccessToken(token)).map(toAccessToken))
  }

  private val accessTokenExpireSeconds = 3600
  private def toAccessToken(accessToken: OAuthAccessToken) = {
    AccessToken(
      accessToken.getAccessToken,
      Some(accessToken.getRefreshToken),
      None,
      Some(accessTokenExpireSeconds),
      accessToken.getCreatedAt
    )
  }

  private def toAuthInfo(accessToken: OAuthAccessToken): AuthInfo[User] = {
    AuthInfo(
      user = accessToken.getAccount,
      clientId = Some(accessToken.getClient.getClientId),
      scope = None,
      redirectUri = None
    )
  }

  private def toAuthInfo(accessToken: OAuthAuthorizationCode): AuthInfo[User] = {
    AuthInfo(
      user = accessToken.getAccount,
      clientId = Some(accessToken.getClient.getClientId),
      scope = None,
      redirectUri = Some(accessToken.getRedirectUri)
    )
  }
}
