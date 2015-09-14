package dao.ebean;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.Query;
import com.avaje.ebean.Update;

import dao.BaseDAO;

/**
 * Implements the generic CRUD data access operations.
 * <p>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Of course, assuming that you have a traditional 1:1 approach for Entity:DAO design.
 * <p>
 */
public abstract class BaseEbeanDAO<T, ID extends Serializable> implements BaseDAO<T, ID> {
	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	protected BaseEbeanDAO() {
		persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	// create ------------------------------------------------------------------
	
	public T create(T entity) {
		Ebean.save(entity);
		return entity;
	}
	
	public int createAll(List<T> entities) {
		return Ebean.save(entities.iterator());
	}

	// find --------------------------------------------------------------------
	
	public T find(ID id) {
		return Ebean.find(getPersistentClass(), id);
	}
	
	public T findWith(ID id, String...relations) {
		Query<T> query = createQuery();
		
		for (String relation : relations) {
			query.fetch(relation);
		}
		
		return filterByPk(query, id).findUnique();
	}
	
	public T findByQuery(Query<T> query) {
		return query.findUnique();
	}
	
	public T findByField(String fieldName, Object fieldValue) {
		return createQuery().where().eq(fieldName, fieldValue).findUnique();
	}
	
	public T findByFieldWith(String fieldName, Object fieldValue, String...relations) {
		Query<T> query =  createQuery();
		
		for (String relation : relations) {
			query.fetch(relation);
		}
		
		return query.where().eq(fieldName, fieldValue).findUnique();
	}
	
	public List<T> findAllByField(String fieldName, Object fieldValue) {
		return createQuery().where().eq(fieldName, fieldValue).findList();
	}
	
	public List<T> findAllByQuery(Query<T> query) {
		return query.findList();
	}
	
	public PagedList<T> findAllByQuery(Query<T> query, int pageIndex, int pageSize) {
		return query.findPagedList(pageIndex, pageSize);
	}
	
	public List<T> findByExample(T example) {
		Query<T> query = createQuery();
		if (example != null) {
			query.where().iexampleLike(example);
		}
		return query.findList();
	}
	
	public PagedList<T> findByExample(T example, int pageIndex, int pageSize) {
		Query<T> query = createQuery();
		if (example != null) {
			query.where().iexampleLike(example);
		}
		return query.findPagedList(pageIndex, pageSize);
	}
	
	public List<T> findAll() {
		return Ebean.find(getPersistentClass()).findList(); 
	}
	
	public PagedList<T> findAll(int pageIndex, int pageSize) {
		return Ebean.find(getPersistentClass()).findPagedList(pageIndex, pageSize);
	}

	public PagedList<T> findAllWith(int pageIndex, int pageSize, String...relations) {
		Query<T> query = Ebean.find(getPersistentClass());

		for (String relation : relations) {
			query.fetch(relation);
		}

		return query.findPagedList(pageIndex, pageSize);
	}

	// update ------------------------------------------------------------------
	
	public T update(T entity) {
		Ebean.save(entity);
		return entity;
	}
	
	public int updateAll(List<T> entities) {
		return Ebean.save(entities.iterator());
	}

	// delete ------------------------------------------------------------------
	
	public void delete(T entity) {
		Ebean.delete(entity);
	}
	
	public void deleteAll(List<T> entities) {
		Ebean.delete(entities.iterator());
	}

    public void deleteById(ID id) {
    	T entity = find(id);
    	Ebean.delete(entity);
	}
    
    public void deleteByIds(List<ID> ids) {
    	Query<T> query = Ebean.createQuery(getPersistentClass());
    	query.where().in(getPkFieldName(), ids);
    	deleteByQuery(query);
	}
    
	public void deleteByQuery(Query<T> query) {
		List<T> entities = findAllByQuery(query);
		Ebean.delete(entities.iterator());
	}
	
	public void deleteAll() {
		String query = "delete from " + this.getPersistentClass().getSimpleName();
		Update<T> deleteAll = Ebean.createUpdate(persistentClass, query);
		deleteAll.execute();
	}
	
	// boolean -----------------------------------------------------------------
	public boolean exists(ID id) {
		Query<T> query = Ebean.find(getPersistentClass());
		
		int count = filterByPk(query, id).findRowCount();

		return (count != 0);
	}
	
	// query -------------------------------------------------------------------
	public Query<T> createQuery() {
		return Ebean.createQuery(getPersistentClass());
	}
	
	// others ------------------------------------------------------------------
	public int count() {
        return createQuery().findRowCount();
    }
	
	// utility methods ---------------------------------------------------------
	protected ExpressionList<T> filterByPk(Query<T> query, ID id) {
		return query.where().eq(getPkFieldName(), id);
	}

	// schema info -------------------------------------------------------------
	
	/**
	 * Override it if the primary key has a different field name.
	 * @return The name of the primary key field
	 */
	public String getPkFieldName() {
		return "id";
	}
}