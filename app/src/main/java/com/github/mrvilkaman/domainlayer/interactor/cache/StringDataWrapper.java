package com.github.mrvilkaman.domainlayer.interactor.cache;


import com.github.mrvilkaman.datalayer.provider.StringBody;
import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;

public class StringDataWrapper extends DataErrorWrapper<String> {

	StringDataWrapper(Throwable throwable, boolean progress) {
		super(throwable, progress);
	}

	public static StringDataWrapper fromString(DataErrorWrapper<String> wrapper) {
		StringDataWrapper w = new StringDataWrapper(wrapper.getThrowable(), wrapper.isProgress());
		w.value = wrapper.getValue();
		return w;
	}

	public static StringDataWrapper fromStringWrapper(DataErrorWrapper<StringBody> wrapper) {
		StringDataWrapper w = new StringDataWrapper(wrapper.getThrowable(), wrapper.isProgress());
		if (wrapper.isSuccess()) {
			w.value = wrapper.getValue().value;
		}
		return w;
	}
}
