package com.github.mrvilkaman.presentationlayer.resolution;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.domainlayer.exceptions.InternetConnectionException;
import com.github.mrvilkaman.domainlayer.exceptions.NotFoundException;
import com.github.mrvilkaman.domainlayer.exceptions.ServerException;
import com.github.mrvilkaman.domainlayer.exceptions.ServerNotAvailableException;
import com.github.mrvilkaman.domainlayer.exceptions.UnauthorizedException;
import com.github.mrvilkaman.domainlayer.exceptions.UncheckedException;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class ThrowableResolverImplTest extends BaseTestCase {

	@Mock UIResolver ui;
	ThrowableResolver resolver;

	@Override
	public void init() {
		resolver = new ThrowableResolverImpl(ui);
	}

	@Test
	public void testHandleInternetException() {
		// Act
		resolver.handleError(new InternetConnectionException());

		// Assert
		verify(ui).showSnackbar(R.string.dialog_internet_error);
	}


	@Test
	public void testHandleServerException_withMessage() {
		// Act
		resolver.handleError(new ServerException("message", null));

		// Assert
		verify(ui).showMessage(R.string.cleanbase_simple_text, "message");
	}

	@Test
	public void testHandleServerException_withThrowableMessage() {
		// Act
		resolver.handleError(new ServerException(null, new RuntimeException("message Runtime")));

		// Assert
		verify(ui).showMessage(R.string.cleanbase_simple_text, "message Runtime");
	}

	@Test
	public void testHandleServerException_withThrowable() {
		// Act
		resolver.handleError(new ServerException(null, new RuntimeException()));

		// Assert
		verify(ui).showMessage(R.string.dialog_server_error);
	}

	@Test
	public void testHandleServerException_unknown() {
		// Act
		resolver.handleError(new ServerException());

		// Assert
		verify(ui).showMessage(R.string.dialog_server_error);
	}

	@Test
	public void testHandleServerNotAvailableException() {
		// Act
		resolver.handleError(new ServerNotAvailableException());

		// Assert
		verify(ui).showMessage(R.string.dialog_server_notavailable_error);
	}

	@Test
	public void testHandleNotFoundException() {
		// Act
		resolver.handleError(new NotFoundException());

		// Assert
		verify(ui).showMessage(R.string.dialog_default_404_error);
	}

	@Test
	public void testHandleUncheckException() {
		// Act
		resolver.handleError(new UncheckedException("qwer"));

		// Assert
		verify(ui).showMessage(eq(R.string.dialog_default_error), eq("qwer"));
	}

	@Test
	public void testHandleUnauthorizedException() {
		// Act
		resolver.handleError(new UnauthorizedException());

		// Assert
		verify(ui).showToast(eq(R.string.dialog_default_unauthorized));
	}

	@Test
	public void testHandleException() {
		// Act
		resolver.handleError(new RuntimeException("qwer222"));

		// Assert
		verify(ui).showMessage(eq(R.string.dialog_default_error), eq("qwer222"));
	}


	@Test
	public void testHandleException_customHandleIsNotWork() {
		// Arrange
		resolver = new ThrowableResolverImpl(ui, throwable -> false);

		// Act
		resolver.handleError(new RuntimeException("qwer222"));

		// Assert
		verify(ui).showMessage(eq(R.string.dialog_default_error), eq("qwer222"));
	}

	@Test
	public void testHandleException_customHandleIsWork() {
		// Arrange
		resolver = new ThrowableResolverImpl(ui, throwable -> true);

		// Act
		resolver.handleError(new RuntimeException("qwer222"));

		// Assert
		verify(ui, never()).showMessage(anyInt());
		verify(ui, never()).showMessage(anyInt(), any(Object.class));
		verify(ui, never()).showMessage(anyInt(), any(Runnable.class));
		verify(ui, never()).showToast(anyInt());
		verify(ui, never()).showToast(anyInt(), any(Object.class));
		verify(ui, never()).showSnackbar(anyInt());
		verify(ui, never()).showSnackbar(anyInt(), any(Object.class));
		verify(ui, never()).showSnackbar(anyInt(), any(Object.class), any());
	}

}