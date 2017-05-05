package com.github.mrvilkaman.presentationlayer.subscriber;


import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseView;
import com.github.mrvilkaman.presentationlayer.fragments.core.ItemListener;

import rx.annotations.Experimental;

@Experimental
public class ListSubscriber<T extends DataErrorWrapper<D>, D> extends ViewSubscriber<BaseView, T> {

	private ItemListener<D> listener;

	public ListSubscriber(ItemListener<D> listener) {
		this.listener = listener;
	}

	@Override
	public void onNext(T listItemModels) {
		if (listItemModels.isSuccess()) {
			listener.click(listItemModels.getValue());
		} else {
			view().handleError(listItemModels.getThrowable());
		}
	}
}