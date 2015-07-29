package dao;

import com.avaje.ebean.Query;

import dao.ebean.EbeanBaseDAO;
import models.AccessLevel;
import models.AccessType;
import models.EnvironmentType;

public class AccessLevelDAO extends EbeanBaseDAO<AccessLevel, Integer> {
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
