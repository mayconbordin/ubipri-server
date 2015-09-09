package dao;

import java.util.List;

import models.AccessLevel;
import models.Action;
import models.Environment;

public interface ActionDAO extends BaseDAO<Action, Long> {
	public List<Action> getCustomActions(Environment environment);
	public List<Action> getCustomActions(Environment environment, AccessLevel accessLevel);
	public List<Action> getDefaultActions(AccessLevel accessLevel);
}
