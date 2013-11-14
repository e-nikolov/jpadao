package util.jpa;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: emile_000
 * Date: 13/11/13
 * Time: 04:13
 * To change this template use File | Settings | File Templates.
 */
public interface Repository<T extends Identifiable<T>> {
	Long insert(T item);

	List<T> getAll();

	List<T> getAll(String orderBy);

	Option<T> get(long id);
}
