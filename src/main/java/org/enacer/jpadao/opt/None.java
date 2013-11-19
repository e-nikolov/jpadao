package org.enacer.jpadao.opt;

/**
 * todo
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
