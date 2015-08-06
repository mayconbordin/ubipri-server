package dao.ebean;

import com.avaje.ebean.Query;

import dao.AccessTypeClassifierDAO;
import models.AccessTypeClassifier;
import models.EnvironmentType;
import models.UserProfileEnvironment;

public class AccessTypeClassifierEbeanDAO extends BaseEbeanDAO<AccessTypeClassifier, Integer> implements AccessTypeClassifierDAO {
	public AccessTypeClassifier find(UserProfileEnvironment userProfile, EnvironmentType environmentType,
			char shift, int weekday, char workday) {
		Query<AccessTypeClassifier> query = createQuery();
		
		return query.where().eq("userProfile", userProfile)
					.where().eq("environmentType", environmentType)
					.where().eq("shift", shift)
					.where().eq("weekday", weekday)
					.where().eq("workday", workday).findUnique();
	}
}
