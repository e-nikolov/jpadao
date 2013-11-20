package org.enacer.opt;

/**
* With permission from http://www.edc4it.com/2011/08/02/a-scala-style-option-class-for-java/
*
*/
public class Some<T> extends Option<T> {
	public Some(T value) {
		super(value);
	}
}
