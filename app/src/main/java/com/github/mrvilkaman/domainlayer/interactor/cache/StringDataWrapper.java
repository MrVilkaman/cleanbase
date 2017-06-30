package com.github.mrvilkaman.domainlayer.interactor.cache;


import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;

public class StringDataWrapper extends DataErrorWrapper<String> {

	public StringDataWrapper(DataErrorWrapper<String> wrapper) {
		super(wrapper);
	}
}
