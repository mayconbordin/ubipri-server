package dao.ebean;

import com.avaje.ebean.Query;

import dao.AccessTypeClassifierDAO;
import models.*;

public class AccessTypeClassifierEbeanDAO extends BaseEbeanDAO<AccessTypeClassifier, Integer> implements AccessTypeClassifierDAO {

	public AccessTypeClassifier find(UserProfileEnvironment userProfile, EnvironmentType environmentType,
			char shift, int weekday, char workday, FrequencyLevel frequencyLevel) {
		Query<AccessTypeClassifier> query = createQuery();

		return query.where().eq("userProfile", userProfile)
					.where().eq("environmentType", environmentType)
					.where().eq("shift", shift)
					.where().eq("weekday", weekday)
					.where().eq("workday", workday)
					.where().eq("frequencyLevel", frequencyLevel)
				.findUnique();
	}
}
