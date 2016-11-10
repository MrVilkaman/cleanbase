package ru.fixapp.fooproject.presentationlayer.resolution.navigation;

import org.junit.Test;
import org.mockito.Mock;

import ru.fixapp.fooproject.presentationlayer.toolbar.ToolbarResolver;
import ru.fixapp.fooproject.testsutils.BaseTestCase;

import static org.mockito.Mockito.verify;


public class MyFragmentResolverCallbackTest extends BaseTestCase {

	private MyFragmentResolverCallback callback;
	@Mock ToolbarResolver toolbarResolver;

	@Override
	public void init() {
		callback = new MyFragmentResolverCallback(toolbarResolver);
	}

	@Test
	public void testOnRootFragment() {
		// Act
		callback.onRootFragment();

		// Assert
		verify(toolbarResolver).showHomeIcon();
	}

	@Test
	public void testOnNotRootFragment() {
		// Act
		callback.onNotRootFragment();

		// Assert
		verify(toolbarResolver).showBackIcon();
	}


}