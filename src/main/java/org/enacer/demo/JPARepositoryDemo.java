package org.enacer.demo;

import org.enacer.jpadao.Identifiable;
import org.enacer.jpadao.Repository;
import org.enacer.opt.Option;

import javax.inject.Inject;
import java.util.List;

public class JPARepositoryDemo {
	static final long ID = 1;

	@Inject
	static Repository<MyClass> repo;

	public static void main(String[] args) {
		repo.insert(new MyClass());
		Option<MyClass> myClass = repo.get(ID);
		List<MyClass> myClassList = repo.getAll();
		List<MyClass> myClassSortedList = repo.getAll("columnName");
	}

	public static class MyClass implements Identifiable<MyClass> {

		@Override
		public Long getId() {
			return 3l;

		}
	}
}
