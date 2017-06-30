package com.github.mrvilkaman.domainlayer.models;


public class DataErrorWrapper<D> {
	protected D value;
	protected Throwable throwable;
	protected boolean progress;

	//	public static DataErrorWrapper


	protected DataErrorWrapper(DataErrorWrapper<D> wrapper) {
		throwable = wrapper.getThrowable();
		value = wrapper.getValue();
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

	protected void setProgress(boolean progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		return "DataErrorWrapper{" + "value=" + value + ", throwable=" + throwable + ", " +
				"progress=" + progress + '}';
	}
}
