package org.enacer.opt;

/**
* With permission from [http://www.edc4it.com/2011/08/02/a-scala-style-option-class-for-java/](http://www.edc4it.com/2011/08/02/a-scala-style-option-class-for-java/) 
*
*/
public class None<T> extends Option<T> {

	private static None instance = new None();

	public static None none() {
		return instance;
	}


	protected None() {
		super();
	}
}
