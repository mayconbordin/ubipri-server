package dao.ebean;

import java.util.List;

import com.avaje.ebean.Ebean;

import dao.ActionDAO;
import models.AccessLevel;
import models.Action;
import models.Environment;

public class ActionEbeanDAO extends BaseEbeanDAO<Action, Long> implements ActionDAO {

	public List<Action> getCustomActions(Environment environment) {
		List<Action> actions = Ebean.find(Action.class)
		     .fetch("accessLevel")
		     .fetch("functionality")
		     .fetch("environment")
			 .fetch("args")
			 .where().eq("custom_environment_id", environment.getId())
		     .findList();
		
		return actions;
	}
	
	public List<Action> getCustomActions(Environment environment, AccessLevel accessLevel) {
		List<Action> actions = Ebean.find(Action.class)
				.fetch("accessLevel")
		     .fetch("functionality")
		     .fetch("environment")
				.fetch("args")
				.where()
		     	.eq("custom_environment_id", environment.getId())
		     	.eq("access_level_id", accessLevel.getId())
		     .findList();
		
		return actions;
	}

	public List<Action> getDefaultActions(AccessLevel accessLevel) {
		List<Action> actions = Ebean.find(Action.class)
				.fetch("functionality")
				.fetch("args")
				.where().eq("access_level_id", accessLevel.getId())
				.isNull("environment")
				.findList();

		return actions;
	}
}
