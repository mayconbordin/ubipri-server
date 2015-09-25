package controllers

import javax.inject.Inject

import oauth2.OAuth2DataHandler
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}

import scalaoauth2.provider.OAuth2Provider

import scala.concurrent.Future
import scalaoauth2.provider._
import scalaoauth2.provider.OAuth2ProviderActionBuilders._

class OAuthController @Inject() (dataHandler: OAuth2DataHandler) extends Controller with OAuth2Provider {

  def accessToken = Action.async { implicit request =>
    issueAccessToken(dataHandler)
  }

}
