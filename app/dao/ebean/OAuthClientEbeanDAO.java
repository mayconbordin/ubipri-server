package dao.ebean;

import dao.OAuthClientDAO;
import models.OAuthClient;
import models.OAuthGrantType;
import models.User;

public class OAuthClientEbeanDAO extends BaseEbeanDAO<OAuthClient, Integer> implements OAuthClientDAO {
    @Override
    public boolean validate(String clientId, String clientSecret, String grantType) {
        OAuthClient client = createQuery()
                .where().eq("clientId", clientId)
                .where().eq("clientSecret", clientSecret)
                .findUnique();

        if (client != null) {
            return (grantType.equals(client.getGrantType()) || grantType.equals(OAuthGrantType.REFRESH_TOKEN));
        }

        return false;
    }

    @Override
    public OAuthClient findByClientId(String clientId) {
        return this.findByField("clientId", clientId);
    }

    @Override
    public User findClientCredentials(String clientId, String clientSecret) {
        OAuthClient client = createQuery().fetch("owner")
                .where().eq("clientId", clientId)
                .where().eq("clientSecret", clientSecret)
                .where().eq("grantType", OAuthGrantType.CLIENT_CREDENTIALS)
                .findUnique();

        if (client != null) {
            return client.getOwner();
        }

        return null;
    }

}
