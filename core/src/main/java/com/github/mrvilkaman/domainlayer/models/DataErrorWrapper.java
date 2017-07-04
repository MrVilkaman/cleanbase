package com.github.mrvilkaman.domainlayer.models;


public class DataErrorWrapper<D> {
	protected D value;
	protected Throwable throwable;
	protected boolean progress;

	protected DataErrorWrapper(DataErrorWrapper wrapper) {
		throwable = wrapper.getThrowable();
		progress = wrapper.isProgress();
	}


	public DataErrorWrapper(D value, boolean progress) {
		this.value = value;
		this.progress = progress;
		throwable = null;
	}

	public DataErrorWrapper(Throwable throwable, boolean progress) {
		this.throwable = throwable;
		this.progress = progress;
		value = null;
	}

	public DataErrorWrapper(boolean progress) {
		this.progress = progress;
		throwable = null;
		value = null;
	}

	public static <T> DataErrorWrapper<T> clone(DataErrorWrapper<T> wrapper) {
		DataErrorWrapper<T> dataErrorWrapper = new DataErrorWrapper<>(wrapper);
		dataErrorWrapper.value = wrapper.value;
		return dataErrorWrapper;
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
