package ru.fixapp.fooproject.presentationlayer.resolution;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.domainlayer.exceptions.InternetConnectionException;
import ru.fixapp.fooproject.domainlayer.exceptions.NotFoundException;
import ru.fixapp.fooproject.domainlayer.exceptions.ServerException;
import ru.fixapp.fooproject.domainlayer.exceptions.ServerNotAvailableException;
import ru.fixapp.fooproject.domainlayer.exceptions.UnauthorizedException;
import ru.fixapp.fooproject.domainlayer.exceptions.UncheckedException;

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
