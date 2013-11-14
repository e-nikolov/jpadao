package util.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by student on 11/12/13.
 */
public class JPARepository<T extends Identifiable<T>> implements Repository<T> {

	@PersistenceContext(name = "MainRepository")
	private EntityManager em;

	protected final void insert(Object o) {
		em.persist(o);
	}

	public final Long insert(T o) {
		em.persist(o);
		return o.getId();
	}

	public final Option<T> get(long id) {
		T t = (T) em.find(getChildClass(), id);

		if (t == null)
			return None.none();
		else {
			return new Some<>(t);
		}
	}

	public final List<T> getAll() {
		String jpql = "SELECT d FROM " + getChildClass().getSimpleName() + " AS d";

		TypedQuery<T> query = (TypedQuery<T>) getEntityManager().createQuery(jpql, getChildClass());

		List<T> list = query.getResultList();

		return list;
	}

	public final List<T> getAll(String orderBy) {
		String jpql = "SELECT d FROM " + getChildClass().getSimpleName() + " AS d ORDER BY d." + orderBy;

		TypedQuery<T> query = (TypedQuery<T>) getEntityManager().createQuery(jpql, getChildClass());

		List<T> list = query.getResultList();

		return list;
	}

	protected final EntityManager getEntityManager() {
		return em;
	}

	private Class<?> getChildClass() {
		return Utils.getClassOfT(BaseRepositoryJPA.class, getClass());
	}
}
