package com.github.mrvilkaman.domainlayer.interactor.cache;


import com.github.mrvilkaman.datalayer.provider.StringBody;
import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;

public class StringDataWrapper extends DataErrorWrapper<String> {

	private StringDataWrapper(DataErrorWrapper wrapper) {
		super(wrapper);
	}

	public static StringDataWrapper fromString(DataErrorWrapper<String> wrapper) {
		StringDataWrapper w = new StringDataWrapper(wrapper);
		w.value = wrapper.getValue();
		return w;
	}

	public static StringDataWrapper fromStringWrapper(DataErrorWrapper<StringBody> wrapper) {
		StringDataWrapper w = new StringDataWrapper(wrapper);
		if (wrapper.isSuccess()) {
			w.value = wrapper.getValue().value;
		}
		return w;
	}
}
