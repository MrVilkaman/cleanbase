package com.github.mrvilkaman.presentationlayer.resolution;


import com.github.mrvilkaman.core.R;
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
			uiResolver.showMessage(R.string.dialog_server_error);
		} else if (throwable instanceof ServerNotAvailableException) {
			uiResolver.showMessage(R.string.dialog_server_notavailable_error);
		} else if (throwable instanceof InternetConnectionException) {
			uiResolver.showSnackbar(R.string.dialog_internet_error);
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
}
