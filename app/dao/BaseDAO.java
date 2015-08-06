package dao;

import java.io.Serializable;
import java.util.List;

import com.avaje.ebean.PagedList;
import com.avaje.ebean.Query;

public interface BaseDAO<T, ID extends Serializable> {
	public T create(T entity);
	
	public int createAll(List<T> entities);
	
	public T find(ID id);
	
	public T findWith(ID id, String...relations);
	
	public T findByQuery(Query<T> query);
	
	public T findByField(String fieldName, String fieldValue);
	
	public T findByFieldWith(String fieldName, String fieldValue, String...relations);
	
	public List<T> findAllByField(String fieldName, String fieldValue);
	
	public List<T> findAllByQuery(Query<T> query);
	
	public PagedList<T> findAllByQuery(Query<T> query, int pageIndex, int pageSize);
	
	public List<T> findByExample(T example);
	
	public PagedList<T> findByExample(T example, int pageIndex, int pageSize);
	
	public List<T> findAll();
	
	public PagedList<T> findAll(int pageIndex, int pageSize);
	
	public T update(T entity);
	
	public int updateAll(List<T> entities);
	
	public void delete(T entity);
	
	public void deleteAll(List<T> entities);

    public void deleteById(ID id);
    
    public void deleteByIds(List<ID> ids);
    
	public void deleteByQuery(Query<T> query);
	
	public void deleteAll();
	
	public boolean exists(ID id);
	
	public Query<T> createQuery();
	
	public int count();
}
