package net.platform.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import net.platform.dao.BeanTransformerAdapter;
import net.platform.dao.IBaseDao;
import net.platform.utils.page.PageResults;
import net.platform.utils.page.RowMapper;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @ClassName: BaseDao
 * @Description: baseDao实现
 * @author
 * @date 2016-3-17 上午11:20:25
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
// @Service("baseService")
@Repository
@Transactional
public class BaseDao<T, ID extends Serializable> implements IBaseDao<T, ID> {
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SessionFactory sessionFactory;
	protected Class<T> entityClass;

	public BaseDao() {
	}

	protected Class getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}

	/**
	 * <保存实体> <完整保存实体>
	 * 
	 * @param t
	 *            实体参数
	 * @see net.platform.dao.IBaseDao#save(java.lang.Object)
	 */
	@Override
	public void save(T t) {
		this.getSession().save(t);
	}

	/**
	 * <保存或者更新实体>
	 * 
	 * @param t
	 *            实体
	 * @see net.platform.dao.IBaseDao#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(T t) {
		this.getSession().saveOrUpdate(t);
		getSession().flush();
	}

	/**
	 * <load> <加载实体的load方法>
	 * 
	 * @param id
	 *            实体的id
	 * @return 查询出来的实体
	 * @see net.platform.dao.IBaseDao#load(java.io.Serializable)
	 */
	@Override
	public T load(ID id) {
		T load = (T) this.getSession().load(getEntityClass(), id);
		return load;
	}

	public T load(Class<T> entityClass, final Serializable id) {
		return (T) getSession().load(entityClass, id);

	}

	private Criteria createCriteria(Class<T> entityClass,
			Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/*
	 * 根据类获取所有记录
	 */
	public List<T> loadAll(final Class<T> entityClass) {
		Criteria criteria = createCriteria(entityClass);
		return criteria.list();
	}

	/*
	 * 根据bean的某些值，到数据库获取对应的所有行和字段 List<TsUser> tsUsers =
	 * userService.findByProperty( TsUser.class, "TsUser.id", u.getId());
	 */
	public List<T> findByProperty(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return (List<T>) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * <get> <查找的get方法>
	 * 
	 * @param id
	 *            实体的id
	 * @return 查询出来的实体
	 * @see net.platform.dao.IBaseDao#get(java.io.Serializable)
	 */
	@Override
	public T get(ID id) {
		T load = (T) this.getSession().get(getEntityClass(), id);
		return load;
	}

	/**
	 * 
	 * 功能描述：根据ID获取entity实例, 如果一级缓存不存在, 则从数据库中读取
	 * 
	 * @author yiting lin
	 * @Email linyt@foxmail.com
	 *        <p>
	 *        创建日期 ：2016-7-18 下午03:44:01
	 *        </p>
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param id
	 * @return 返回相应的持久化PO实例
	 * 
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public T get(Class<T> entityClass, final Serializable id) {
		return (T) getSession().get(entityClass, id);

	}

	/**
	 * <contains>
	 * 
	 * @param t
	 *            实体
	 * @return 是否包含
	 * @see net.platform.dao.IBaseDao#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(T t) {
		return this.getSession().contains(t);
	}

	/**
	 * <delete> <删除表中的t数据>
	 * 
	 * @param t
	 *            实体
	 * @see net.platform.dao.IBaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T t) {
		this.getSession().delete(t);
	}

	public boolean delete(ID Id) {
		T t = get(Id);
		if (t == null) {
			return false;
		}
		delete(t);
		return true;
	}

	/**
	 * <根据ID删除数据>
	 * 
	 * @param Id
	 *            实体id
	 * @return 是否删除成功
	 * @see net.platform.dao.IBaseDao#deleteById(java.io.Serializable)
	 */
	@Override
	public boolean deleteById(ID Id) {
		T t = get(Id);
		if (t == null) {
			return false;
		}
		delete(t);
		return true;
	}

	/**
	 * <删除所有>
	 * 
	 * @param entities
	 *            实体的Collection集合
	 * @see net.platform.dao.IBaseDao#deleteAll(java.util.Collection)
	 */
	@Override
	public void deleteAll(Collection<T> entities) {
		for (Object entity : entities) {
			this.getSession().delete(entity);
		}
		getSession().flush();
	}

	/**
	 * 
	 * 功能描述：批量删除
	 * 
	 * @author yiting lin
	 * @Email linyt@foxmail.com
	 *        <p>
	 *        创建日期 ：2016-7-18 下午03:45:45
	 *        </p>
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param id
	 *            格式为: 1,2,3,4
	 * @return true or false
	 * 
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public Boolean deleteAll(Class<T> entityClass, String id) {
		Boolean flag = false;
		if (id.indexOf(",") > 0) {
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				T t = (T) this.get(entityClass, ids[i]);
				this.getSession().delete(t);
			}
			flag = true;
		} else {
			T t = (T) this.get(entityClass, id);
			this.getSession().delete(t);
			flag = true;
		}
		this.getSession().flush();
		return flag;
	}

	/**
	 * <执行Hql语句>
	 * 
	 * @param hqlString
	 *            hql
	 * @param values
	 *            不定参数数组
	 * @see net.platform.dao.IBaseDao#executeHql(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public void executeHql(String hqlString, Object... values) {
		Query query = this.getSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		query.executeUpdate();
	}

	/**
	 * <执行Sql语句>
	 * 
	 * @param sqlString
	 *            sql
	 * @param values
	 *            不定参数数组
	 * @see net.platform.dao.IBaseDao#executeSql(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public void executeSql(String sqlString, Object... values) {
		Query query = this.getSession().createSQLQuery(sqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		query.executeUpdate();
	}

	/**
	 * <根据HQL语句查找唯一实体>
	 * 
	 * @param hqlString
	 *            HQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询实体
	 * @see net.platform.dao.IBaseDao#getByHQL(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public T getByHQL(String hqlString, Object... values) {
		Query query = this.getSession().createQuery(hqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (T) query.uniqueResult();
	}

	/**
	 * <根据SQL语句查找唯一实体>
	 * 
	 * @param sqlString
	 *            SQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询实体
	 * @see net.platform.dao.IBaseDao#getBySQL(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public T getBySQL(String sqlString, Object... values) {
		Query query = this.getSession().createSQLQuery(sqlString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (T) query.uniqueResult();
	}

	/**
	 * <根据HQL语句，得到对应的list>
	 * 
	 * @param hqlString
	 *            HQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询多个实体的List集合
	 * @see net.platform.dao.IBaseDao#getListByHQL(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public List<T> getListByHQL(String hqlString, Object... values) {
		Query query = this
				.getSession()
				.createQuery(hqlString)
				.setResultTransformer(
						new BeanTransformerAdapter(getEntityClass()));
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	/**
	 * <根据SQL语句，得到对应的list<Entity>
	 * 
	 * @param sqlString
	 *            SQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询多个实体的List集合 setResultTransformer 返回bean的列表集合，默认是数组列表不是bean列表
	 *         BeanTransformerAdapter防止不同数据库字段大小
	 *         setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	 *         //返回一个map,KEY:为DB中名称一致（大小写一致）遍历list时就可以 Map map =
	 *         (Map)list.get[i]; map.get("id");map.get("name");
	 * @see net.platform.dao.IBaseDao#getListBySQL(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public List<T> getListBySQL(String sqlString, Object... values) {
		Query query = this
				.getSession()
				.createSQLQuery(sqlString)
				.setResultTransformer(
						new BeanTransformerAdapter(getEntityClass()));
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}

	/**
	 * 由sql语句得到List
	 * 
	 * @param sql
	 * @param map
	 * @param values
	 * @return List
	 * @see net.platform.dao.IBaseDao#findListBySql(java.lang.String,
	 *      net.platform.dao.RowMapper, java.lang.Object[])
	 */
	@Override
	public List<Object[]> findListBySql(final String sql, final RowMapper map,
			final Object... values) {
		final List list = new ArrayList();
		// 执行JDBC的数据批量保存
		Work jdbcWork = new Work() {
			public void execute(Connection connection) throws SQLException {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = connection.prepareStatement(sql);
					for (int i = 0; i < values.length; i++) {
						setParameter(ps, i, values[i]);
					}
					rs = ps.executeQuery();
					int index = 0;
					while (rs.next()) {
						Object obj = map.mapRow(rs, index++);
						list.add(obj);
					}
				} finally {
					if (rs != null) {
						rs.close();

					}
					if (ps != null) {
						ps.close();
					}
				}
			}
		};
		this.getSession().doWork(jdbcWork);
		return list;
	}

	/*
	 * 与getListBySQL的区别是不转换成T类，引起字段转换报错 不需要翻页就传入 sql,0,0
	 */
	public List<Object[]> findListBySql(String sql, int pageNo, int pageSize,
			Object... values) {

		Query query = getSession().createSQLQuery(sql);
		if (pageNo != 0 && pageSize != 0) {
			query.setFirstResult(pageNo);
			query.setMaxResults(pageSize);
		}
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		List list = query.list();
		// if (list != null && !list.isEmpty()) {
		// for (Object objs : list) {
		// getSession().evict(objs);
		// }
		// }
		return list;
	}

	/*
	 * 不需要翻页就传入 hql,0,0
	 */
	public List<Object[]> findListByHql(String hql, int pageNo, int pageSize,
			Object... values) {
		Query query = getSession().createQuery(hql);
		if (pageNo != 0 && pageSize != 0) {
			query.setFirstResult(pageNo);
			query.setMaxResults(pageSize);
		}
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		List list = query.list();
		// if (list != null && !list.isEmpty()) {
		// for (Object obj : list) {
		// getSession().evict(obj);
		// }
		// }
		return list;
	}

	/**
	 * <refresh>
	 * 
	 * @param t
	 *            实体
	 * @see net.platform.dao.IBaseDao#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(T t) {
		this.getSession().refresh(t);
	}

	/**
	 * <update>
	 * 
	 * @param t
	 *            实体
	 * @see net.platform.dao.IBaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(T t) {
		this.getSession().update(t);
	}

	/**
	 * <根据HQL得到记录数>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 记录总数
	 * @see net.platform.dao.IBaseDao#countByHql(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public Number countByHql(String hql, Object... values) {
		Query query = this.getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (Number) query.uniqueResult();
	}

	public Number countBySql(String sql, Object... values) {
		Query query = this.getSession().createSQLQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (Number) query.uniqueResult();
	}

	/**
	 * <HQL分页查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param countHql
	 *            查询记录条数的HQL语句
	 * @param pageNo
	 *            下一页
	 * @param pageSize
	 *            一页总条数
	 * @param values
	 *            不定Object数组参数
	 * @return PageResults的封装类，里面包含了页码的信息以及查询的数据List集合
	 * @see net.platform.dao.IBaseDao#findPageByHql(java.lang.String,
	 *      java.lang.String, int, int, java.lang.Object[])
	 */
	@Override
	public PageResults<T> findPageByHql(String hql, String countHql,
			int pageNo, int pageSize, Object... values) {
		PageResults<T> retValue = new PageResults<T>();
		Query query = this.getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		int currentPage = pageNo > 1 ? pageNo : 1;
		retValue.setCurrentPage(currentPage);
		retValue.setPageSize(pageSize);
		if (countHql == null) {
			ScrollableResults results = query.scroll();
			results.last();
			retValue.setTotal(results.getRowNumber() + 1);// 设置总记录数
		} else {
			Number count = countByHql(countHql, values);
			retValue.setTotal(count.intValue());
		}
		retValue.resetPageNo();
		List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize)
				.setMaxResults(pageSize).list();
		if (itemList == null) {
			itemList = new ArrayList<T>();
		}
		retValue.setRows(itemList);

		return retValue;
	}

	@Override
	public PageResults<T> findPageByHql(String hql, int totalCount, int pageNo,
			int pageSize, Object... values) {
		PageResults<T> retValue = new PageResults<T>();
		Query query = this.getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		int currentPage = pageNo > 1 ? pageNo : 1;
		retValue.setCurrentPage(currentPage);
		retValue.setPageSize(pageSize);
		retValue.setTotal(totalCount);// 设置总记录数

		retValue.resetPageNo();
		List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize)
				.setMaxResults(pageSize).list();
		if (itemList == null) {
			itemList = new ArrayList<T>();
		}
		retValue.setRows(itemList);

		return retValue;
	}

	/**
	 * <SQL分页查询>
	 * 
	 * @param sql
	 *            SQL语句
	 * @param countSql
	 *            查询记录条数的SQL语句
	 * @param pageNo
	 *            下一页
	 * @param pageSize
	 *            一页总条数
	 * @param values
	 *            不定Object数组参数
	 * @return PageResults的封装类，里面包含了页码的信息以及查询的数据List集合
	 * @see net.platform.dao.IBaseDao#findPageBySql(java.lang.String,
	 *      java.lang.String, int, int, java.lang.Object[])
	 */
	@Override
	public PageResults<T> findPageBySql(String sql, String countSql,
			int pageNo, int pageSize, Object... values) {
		PageResults<T> retValue = new PageResults<T>();
		Query query = this
				.getSession()
				.createSQLQuery(sql)
				.setResultTransformer(
						new BeanTransformerAdapter(getEntityClass()));
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		int currentPage = pageNo > 1 ? pageNo : 1;
		retValue.setCurrentPage(currentPage);
		retValue.setPageSize(pageSize);
		if (countSql == null) {
			ScrollableResults results = query.scroll();
			results.last();
			retValue.setTotal(results.getRowNumber() + 1);// 设置总记录数
		} else {
			Number count = countBySql(countSql, values);
			retValue.setTotal(count.intValue());
		}
		retValue.resetPageNo();
		List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize)
				.setMaxResults(pageSize).list();
		if (itemList == null) {
			itemList = new ArrayList<T>();
		}
		retValue.setRows(itemList);

		return retValue;
	}

	@Override
	public PageResults<T> findPageBySql(String sql, int totalCount, int pageNo,
			int pageSize, Object... values) {
		PageResults<T> retValue = new PageResults<T>();
		Query query = this
				.getSession()
				.createSQLQuery(sql)
				.setResultTransformer(
						new BeanTransformerAdapter(getEntityClass()));
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		int currentPage = pageNo > 1 ? pageNo : 1;
		retValue.setCurrentPage(currentPage);
		retValue.setPageSize(pageSize);
		retValue.setTotal(totalCount);// 设置总记录数

		retValue.resetPageNo();
		List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize)
				.setMaxResults(pageSize).list();
		if (itemList == null) {
			itemList = new ArrayList<T>();
		}
		retValue.setRows(itemList);

		return retValue;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 
	 * @return session
	 */
	public Session getSession() {
		// 需要开启事物，才能得到CurrentSession
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 
	 * 设置每行批处理参数
	 * 
	 * @param ps
	 * @param pos
	 *            ?占位符索引，从0开始
	 * @param data
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	private void setParameter(PreparedStatement ps, int pos, Object data)
			throws SQLException {
		if (data == null) {
			ps.setNull(pos + 1, Types.VARCHAR);
			return;
		}
		Class dataCls = data.getClass();
		if (String.class.equals(dataCls)) {
			ps.setString(pos + 1, (String) data);
		} else if (boolean.class.equals(dataCls)) {
			ps.setBoolean(pos + 1, ((Boolean) data));
		} else if (int.class.equals(dataCls)) {
			ps.setInt(pos + 1, (Integer) data);
		} else if (double.class.equals(dataCls)) {
			ps.setDouble(pos + 1, (Double) data);
		} else if (Date.class.equals(dataCls)) {
			Date val = (Date) data;
			ps.setTimestamp(pos + 1, new Timestamp(val.getTime()));
		} else if (BigDecimal.class.equals(dataCls)) {
			ps.setBigDecimal(pos + 1, (BigDecimal) data);
		} else {
			// 未知类型
			ps.setObject(pos + 1, data);
		}

	}

	/**
	 * 返回此类的列的属性名称,不包含静态属性和Transient
	 * 
	 * @param entity
	 * @return
	 */
	private List<String> getEntityColumnNameList(Class<?> cls) {
		List<String> list = new ArrayList<String>();
		Class<?> clazz = cls;
		Field[] fs = clazz.getDeclaredFields();
		String filedName = null;
		for (Field field : fs) {
			boolean isStatic = Modifier.isStatic(field.getModifiers());
			if (isStatic)
				continue;
			field.setAccessible(true);
			filedName = field.getName();
			Annotation[] as = field.getAnnotations();
			boolean isTransaction = false;
			for (int i = 0; i < as.length; i++) {
				Annotation a = as[i];
				if (a instanceof Transient) {
					isTransaction = true;
					break;
				}
			}
			if (!isTransaction) {
				list.add(filedName);
			}
		}
		return list;
	}

	/**
	 * 得到除开指定名称的属性列
	 */
	protected List<String> getEntityColumnNames(Class<?> cls,
			String... exceptCoulumns) {
		List<String> nameList = getEntityColumnNameList(cls);
		if (exceptCoulumns != null) {
			for (String s : exceptCoulumns) {
				nameList.remove(s);
			}
		}
		return nameList;
	}

	/**
	 * 得到除开指定名称的属性列
	 */
	protected List<String> getEntityColumnNames(Class<?> cls,
			List<String> exceptCoulumns) {
		List<String> nameList = getEntityColumnNameList(cls);
		if (exceptCoulumns != null) {
			for (String s : exceptCoulumns) {
				nameList.remove(s);
			}
		}
		return nameList;
	}

	public List<T> callProcedure(String procString, List<Object> params)
			throws Exception {

		ResultSet rs = null;
		CallableStatement stmt = null;
		try {
			stmt = (CallableStatement) SessionFactoryUtils
					.getDataSource(this.getSessionFactory()).getConnection()
					.prepareCall(procString);
			if (params != null) {
				int idx = 1;
				for (Object obj : params) {
					if (obj != null) {
						stmt.setObject(idx, obj);
					} else {
						stmt.setNull(idx, Types.NULL);
					}
					idx++;
				}
			}
			rs = stmt.executeQuery();
			List list = new ArrayList();
			ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
			int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
			Map rowData = new HashMap();
			while (rs.next()) {
				rowData = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("调用存储过程的时候发生错误[sql = " + procString + "]", e);
		} finally {
			rs.close();
			stmt.close();
		}
	}
}
