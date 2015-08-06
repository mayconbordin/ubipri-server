package dao.ebean;

import com.avaje.ebean.Query;

import dao.AccessLevelDAO;
import models.AccessLevel;
import models.AccessType;
import models.EnvironmentType;

public class AccessLevelEbeanDAO extends BaseEbeanDAO<AccessLevel, Integer> implements AccessLevelDAO {
	public AccessLevel find(EnvironmentType environmentType, AccessType accessType) {
		return createQuery().where().eq("environmentType", environmentType)
			.where().eq("accessType", accessType).findUnique();
	}
	
	public AccessLevel findWith(EnvironmentType environmentType, AccessType accessType, String...relations) {
		Query<AccessLevel> query = createQuery();
		
		for (String relation : relations) {
			query.fetch(relation);
		}
		
		return query.where().eq("environmentType", environmentType)
				    .where().eq("accessType", accessType)
				    .findUnique();
	}
}
