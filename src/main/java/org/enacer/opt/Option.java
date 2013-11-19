package org.enacer.opt;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Option<T> {

	private T value;

	protected Option(T value) {
		this.value = value;
	}

	protected Option() {
	}

	private boolean hasValue() {
		return value != null;
	}

	private T getValue() {
		return value;
	}

	public <F extends T> T getValueOr(F fallBackValue) {
		return hasValue() ? value : fallBackValue;
	}

	public T assertAndGet() {
		return assertAndGet("expected a value");
	}

	public T assertAndGet(String message) {
		if (value == null) throw new IllegalStateException(message);
		return value;
	}


	public <M, E extends Exception> M mapValueOrThrowException(Mapper<T, M> mapper, Class<E> e, String message) throws E {
		if (!hasValue())
			throwException(e, message);
		return mapper.map(value);
	}

	public <M> Option<M> map(Mapper<T, M> mapper) {
		if (!hasValue())
			return new Option<M>();
		return new Option<M>(mapper.map(value));
	}

	public void doWith(DoWith<T> doWith) {
		if (hasValue())
			doWith.run(value);
	}


	public interface Mapper<F, T> {
		T map(F f);
	}

	public interface DoWith<T> {
		void run(T value);
	}

	public <E extends Exception> T getValueOrThrowException(Class<E> e, String message) throws E {
		if (hasValue())
			return value;
		return throwException(e, message);
	}

	private <E extends Exception> T throwException(Class<E> e, String message) throws E {
		final E exception;
		try {
			final Constructor<E> constructor = e.getConstructor(String.class);
			exception = constructor.newInstance(message);
			throw exception;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e1) {
			throw new RuntimeException(e1);

		} catch (NoSuchMethodException e1) {
			throw new RuntimeException(new StringBuilder().append("Class ").append(e.getName()).append(" does not have a constructor taking a String").toString(), e1);

		}
	}
}
