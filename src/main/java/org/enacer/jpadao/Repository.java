package org.enacer.jpadao;

import org.enacer.jpadao.opt.Option;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: emile_000
 * Date: 13/11/13
 * Time: 04:13
 * To change this template use File | Settings | File Templates.
 */
public interface Repository<T extends Identifiable<T>> {
	Long insert(T t);

	List<T> getAll();

	List<T> getAll(String orderBy);

	Option<T> get(long id);

	void update(T t);

	void remove(T t);

	boolean exists(String columnName, String value);

	Option<T> get(String columnName, String value);

	List<T> getList(String columnName, String value);
}
