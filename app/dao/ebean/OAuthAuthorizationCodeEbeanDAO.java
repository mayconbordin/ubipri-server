package dao.ebean;

import dao.OAuthAuthorizationCodeDAO;
import models.OAuthAuthorizationCode;
import org.joda.time.DateTime;

public class OAuthAuthorizationCodeEbeanDAO extends BaseEbeanDAO<OAuthAuthorizationCode, Integer> implements OAuthAuthorizationCodeDAO {

    @Override
    public OAuthAuthorizationCode findByCode(String code) {
        DateTime expireAt = new DateTime().minusMinutes(30);

        return createQuery()
                .where().eq("code", code)
                .where().gt("createdAt", expireAt)
                .findUnique();
    }

    @Override
    public void deleteByCode(String code) {
        OAuthAuthorizationCode authCode = createQuery()
                .where().eq("code", code)
                .findUnique();

        delete(authCode);
    }
}
