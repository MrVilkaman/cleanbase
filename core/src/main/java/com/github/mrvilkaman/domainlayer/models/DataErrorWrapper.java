package com.github.mrvilkaman.domainlayer.models;


public class DataErrorWrapper<D> {
	private final D value;
	private final Throwable throwable;
	private final boolean progress;

	//	public static DataErrorWrapper

	public DataErrorWrapper(boolean progress) {
		this.progress = progress;
		throwable = null;
		value = null;
	}

	public DataErrorWrapper(D value) {
		this.value = value;
		throwable = null;
		progress = false;
	}

	public DataErrorWrapper(Throwable throwable) {
		this.throwable = throwable;
		value = null;
		progress = false;
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

	public boolean isProgress() {
		return progress;
	}

	@Override
	public String toString() {
		return "DataErrorWrapper{" + "value=" + value + ", throwable=" + throwable + ", " +
				"progress=" + progress + '}';
	}
}
