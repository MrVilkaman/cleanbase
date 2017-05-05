package com.github.mrvilkaman.domainlayer.models;


public class DataErrorWrapper<D> {
	public final D value;
	public final Throwable throwable;

	public DataErrorWrapper(D value) {
		this.value = value;
		throwable = null;
	}

	public DataErrorWrapper(Throwable throwable) {
		this.throwable = throwable;
		value = null;
	}

	public boolean isSuccess() {
		return value != null;
	}

	public boolean isError() {
		return throwable != null;
	}

	public D getValue() {
		return value;
	}

	public Throwable getThrowable() {
		return throwable;
	}


	@Override
	public String toString() {
		return "DataErrorWrapper{" + "value=" + value + ", throwable=" + throwable + '}';
	}
}
