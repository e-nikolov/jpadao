package test;

import util.jpa.Repository;

import javax.inject.Inject;

public class Test {
	static final long ID = 1;

	@Inject
	Repository<MyClass> repo;

	public static void main(String[] args) {
		repo.insert(new MyClass());
		Option<MyClass> myClass = repo.get(ID);
		List<MyClass> myClassList = repo.getAll();
		List<MyClass> myClassSortedList = repo.getAll("columnName");
	}
}
