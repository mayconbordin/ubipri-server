package dao.ebean;

import com.avaje.ebean.Expr;
import dao.OAuthAccessTokenDAO;
import models.OAuthAccessToken;
import models.OAuthGrantType;
import models.User;
import org.joda.time.DateTime;

public class OAuthAccessTokenEbeanDAO extends BaseEbeanDAO<OAuthAccessToken, Integer> implements OAuthAccessTokenDAO {

    @Override
    public OAuthAccessToken findByAccessToken(String accessToken) {
        return createQuery()
                .fetch("account")
                .fetch("client")
                .where().eq("accessToken", accessToken)
                .findUnique();
    }

    @Override
    public OAuthAccessToken findByAuthorized(User user, String clientId) {
        return createQuery()
                .where().eq("account_id", user.getId())
                .where().eq("client.clientId", clientId)
                .findUnique();
    }

    @Override
    public OAuthAccessToken findByRefreshToken(String refreshToken) {
        DateTime expireAt = new DateTime().minusMonths(1);

        return createQuery()
                .where().eq("refreshToken", refreshToken)
                .where().gt("createdAt", expireAt)
                .findUnique();
    }

    @Override
    public void deleteByAuthorized(User user, String clientId) {
        OAuthAccessToken token = createQuery()
                .where().eq("account_id", user.getId())
                .where().eq("client.clientId", clientId)
                .findUnique();

        delete(token);
    }
}
