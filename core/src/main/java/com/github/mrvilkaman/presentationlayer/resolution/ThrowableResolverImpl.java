package com.github.mrvilkaman.presentationlayer.resolution;


import android.support.annotation.Nullable;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.domainlayer.exceptions.InternetConnectionException;
import com.github.mrvilkaman.domainlayer.exceptions.NotFoundException;
import com.github.mrvilkaman.domainlayer.exceptions.ServerException;
import com.github.mrvilkaman.domainlayer.exceptions.ServerNotAvailableException;
import com.github.mrvilkaman.domainlayer.exceptions.UnauthorizedException;
import com.github.mrvilkaman.domainlayer.exceptions.UncheckedException;
import com.github.mrvilkaman.presentationlayer.utils.DevUtils;

import rx.annotations.Experimental;

public class ThrowableResolverImpl implements ThrowableResolver {

	private final UIResolver uiResolver;
	private final Processor customThrowableResolver;

	public ThrowableResolverImpl(UIResolver uiResolver) {
		this(uiResolver,null);
	}

	public ThrowableResolverImpl(UIResolver uiResolver, @Nullable Processor customThrowableResolver) {
		this.uiResolver = uiResolver;
		this.customThrowableResolver = customThrowableResolver;
	}

	@Override
	public void handleError(Throwable throwable) {
		if (customThrowableResolver != null && customThrowableResolver.handleError(throwable)) {
			return;
		}

		if (throwable instanceof ServerException) {
			String message = throwable.getMessage();
			if (message != null) {
				this.uiResolver.showMessage(R.string.cleanbase_simple_text, message);
				return;
			} else {
				Throwable cause = throwable.getCause();
				if (cause != null) {
					String message1 = cause.getMessage();
					if (message1 != null) {
						this.uiResolver.showMessage(R.string.cleanbase_simple_text, message1);
						return;
					}
				}
			}
			uiResolver.showMessage(R.string.dialog_server_error);
		} else if (throwable instanceof ServerNotAvailableException) {
			uiResolver.showMessage(R.string.dialog_server_notavailable_error);
		} else if (throwable instanceof InternetConnectionException) {
			if (DevUtils.isSnackbarInTheClassPath()) {
				uiResolver.showSnackbar(R.string.dialog_internet_error);
			}else{
				uiResolver.showToast(R.string.dialog_internet_error);
			}
		} else if (throwable instanceof NotFoundException) {
			uiResolver.showMessage(R.string.dialog_default_404_error);
		} else if (throwable instanceof UncheckedException) {
			uiResolver.showMessage(R.string.dialog_default_error, throwable.getMessage());
		} else if (throwable instanceof UnauthorizedException) {
			uiResolver.showToast(R.string.dialog_default_unauthorized);
		} else {
			uiResolver.showMessage(R.string.dialog_default_error, throwable.getMessage());
		}
	}

	@Experimental
	public interface Processor {

		// true if error processed
		boolean handleError(Throwable throwable);
	}
}
