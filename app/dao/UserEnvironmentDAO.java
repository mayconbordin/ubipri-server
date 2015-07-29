package dao;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;

import dao.ebean.EbeanBaseDAO;
import models.UserEnvironment;
import models.UserEnvironmentPK;

public class UserEnvironmentDAO extends EbeanBaseDAO<UserEnvironment, UserEnvironmentPK> {

	@Override
	protected ExpressionList<UserEnvironment> filterByPk(Query<UserEnvironment> query,
			UserEnvironmentPK id) {
		return query.where().eq("user_id", id.getUserId())
				    .where().eq("environment_id", id.getEnvironmentId());
	}
	
}
