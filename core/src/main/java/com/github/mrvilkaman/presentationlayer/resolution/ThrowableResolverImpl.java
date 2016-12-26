package com.github.mrvilkaman.presentationlayer.resolution;


import com.github.mrvilkaman.core.R2;

import com.github.mrvilkaman.domainlayer.exceptions.InternetConnectionException;
import com.github.mrvilkaman.domainlayer.exceptions.NotFoundException;
import com.github.mrvilkaman.domainlayer.exceptions.ServerException;
import com.github.mrvilkaman.domainlayer.exceptions.ServerNotAvailableException;
import com.github.mrvilkaman.domainlayer.exceptions.UnauthorizedException;
import com.github.mrvilkaman.domainlayer.exceptions.UncheckedException;

public class ThrowableResolverImpl implements ThrowableResolver {

	private UIResolver uiResolver;

	//	@Inject
	public ThrowableResolverImpl(UIResolver uiResolver) {
		this.uiResolver = uiResolver;
	}

	@Override
	public void handleError(Throwable throwable) {
		if (throwable instanceof ServerException) {
			uiResolver.showMessage(R2.string.dialog_server_error);
		} else if (throwable instanceof ServerNotAvailableException) {
			uiResolver.showMessage(R2.string.dialog_server_notavailable_error);
		} else if (throwable instanceof InternetConnectionException) {
			uiResolver.showSnackbar(R2.string.dialog_internet_error);
		} else if (throwable instanceof NotFoundException) {
			uiResolver.showMessage(R2.string.dialog_default_404_error);
		} else if (throwable instanceof UncheckedException) {
			uiResolver.showMessage(R2.string.dialog_default_error, throwable.getMessage());
		} else if (throwable instanceof UnauthorizedException) {
			uiResolver.showToast(R2.string.dialog_default_unauthorized);
		} else {
			uiResolver.showMessage(R2.string.dialog_default_error, throwable.getMessage());
		}
	}
}
