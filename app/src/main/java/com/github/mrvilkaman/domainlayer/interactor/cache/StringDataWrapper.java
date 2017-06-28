package com.github.mrvilkaman.domainlayer.interactor.cache;


import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;

public class StringDataWrapper extends DataErrorWrapper<String> {

	public StringDataWrapper(boolean progress) {
		super(progress);
	}

	public StringDataWrapper(String value) {
		super(value);
	}

	public StringDataWrapper(Throwable throwable) {
		super(throwable);
	}
}
