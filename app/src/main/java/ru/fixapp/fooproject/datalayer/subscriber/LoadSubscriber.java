package ru.fixapp.fooproject.datalayer.subscriber;


import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseView;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BindType;

/**
 * Created by root on 16.03.16.
 */
public class LoadSubscriber<V extends BaseView, T> extends ViewSubscriber<V,T> {

	private boolean skipNextError;

	public LoadSubscriber(V view) {
		super(view);
		if (needProgress()) {
			view().showProgress();
		}
	}

	@Override
	public void onNext(T item) {
		V view = view();
		if (view instanceof BindType) {
			((BindType<T>) view).bind(item);
		}
	}

	@Override
	public void onError(Throwable e) {
		super.onError(e);
		BaseView view = view();
		if (view  == null) return;


		if (needProgress()) {
			view.hideProgress();
		}
		if (showError()) {
			view.handleError(e);
		}
	}

	protected boolean needProgress() {
		return true;
	}

	protected boolean showError() {
		return !skipNextError;
	}

	@Override
	public void onCompleted() {
		super.onCompleted();
		if (needProgress()) {
			view().hideProgress();
		}
	}

	protected void skipNextError() {
		skipNextError = true;
	}


}
