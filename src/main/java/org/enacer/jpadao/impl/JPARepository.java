package org.enacer.jpadao.impl;

import org.enacer.jpadao.Identifiable;
import org.enacer.jpadao.Repository;
import org.enacer.opt.None;
import org.enacer.opt.Option;
import org.enacer.opt.Some;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by student on 11/12/13.
 */
public class JPARepository<T extends Identifiable<T>> implements Repository<T> {
	@PersistenceContext
	private EntityManager em;

	protected Class<T> entityClass;
	protected String entityName;

	public JPARepository() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];

		// todo make it get the entity name from the annotation instead of the class name
		this.entityName = entityClass.getSimpleName();
	}

	protected final void insert(Object o) {
		em.persist(o);
	}

	public final Long insert(T t) {
		em.persist(t);
		return t.getId();
	}

	public final Option<T> get(long id) {
		T t = em.find(entityClass, id);

		if (t == null)
			return None.none();
		else {
			return new Some<>(t);
		}
	}

	@Override
	public Option<T> get(String columnName, String value) {
		String jpql = "SELECT d FROM " + entityName + " AS d WHERE d." + columnName + " = :value";

		TypedQuery<T> query = getEntityManager().createQuery(jpql, entityClass);
		query.setParameter("value", value);

		T t = query.getSingleResult();

		if (t == null)
			return None.none();
		else {
			return new Some<>(t);
		}
	}

	public final List<T> getAll() {
		String jpql = "SELECT d FROM " + entityName + " AS d";

		TypedQuery<T> query = getEntityManager().createQuery(jpql, entityClass);

		List<T> list = query.getResultList();

		return list;
	}

	public final List<T> getAll(String orderBy) {
		String jpql = "SELECT d FROM " + entityName + " AS d ORDER BY d." + orderBy;

		TypedQuery<T> query = getEntityManager().createQuery(jpql, entityClass);

		List<T> list = query.getResultList();

		return list;
	}

	protected final EntityManager getEntityManager() {
		return em;
	}

	public void update(T t) {
		this.em.merge(t);
	}

	public void remove(T t) {
		t = this.em.merge(t);
		this.em.remove(t);
	}

	@Override
	public boolean exists(String columnName, String value) {
		String jpql = "SELECT COUNT(d) FROM " + entityName + " AS d WHERE d." + columnName + " = :value";

		TypedQuery<Long> query = getEntityManager().createQuery(jpql, Long.class);
		query.setParameter("value", value);

		long count = query.getSingleResult();

		return count > 0;
	}

	@Override
	public List<T> getList(String columnName, String value) {
		String jpql = "SELECT d FROM " + entityName + " AS d WHERE d." + columnName + " = :value";

		TypedQuery<T> query = getEntityManager().createQuery(jpql, entityClass);
		query.setParameter("value", value);

		return query.getResultList();
	}
//
//	private Class<?> getChildClass() {
//		return Utils.getClassOfT(JPARepository.class, getClass());
//	}
}
