package dao.ebean;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;

import dao.UserEnvironmentDAO;
import models.UserEnvironment;
import models.UserEnvironmentPK;

public class UserEnvironmentEbeanDAO extends BaseEbeanDAO<UserEnvironment, UserEnvironmentPK>
		implements UserEnvironmentDAO {

	@Override
	protected ExpressionList<UserEnvironment> filterByPk(Query<UserEnvironment> query,
			UserEnvironmentPK id) {
		return query.where().eq("user_id", id.getUserId())
				    .where().eq("environment_id", id.getEnvironmentId());
	}
	
}
