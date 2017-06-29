package com.github.mrvilkaman.domainlayer.interactor.cache;


import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;

public class StringDataWrapper extends DataErrorWrapper<String> {

	public StringDataWrapper(boolean progress) {
		super(progress);
	}

	public StringDataWrapper(String value, boolean progress) {
		super(value);
		setProgress(progress);
	}

	public StringDataWrapper(Throwable throwable, boolean progress) {
		super(throwable);
		setProgress(progress);
	}
}
