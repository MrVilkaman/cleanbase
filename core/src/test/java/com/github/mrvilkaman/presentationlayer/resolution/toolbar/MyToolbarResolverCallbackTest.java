package com.github.mrvilkaman.presentationlayer.resolution.toolbar;

import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MyToolbarResolverCallbackTest extends BaseTestCase {

	@Mock FragmentResolver fragmentManager;
	@Mock LeftDrawerHelper drawerHelper;
	@Mock BaseActivityView activityView;
	@Mock ToolbarResolver toolbarResolver;
	@Mock NavigationResolver navigationResolver;
	private MyToolbarResolverCallback callback;

	@Override
	public void init() {
		callback = new MyToolbarResolverCallback(fragmentManager, drawerHelper, activityView,
				toolbarResolver, navigationResolver);
	}

	@Test
	public void testOnClickHome_rootScreen() {
		// Arrange
		when(fragmentManager.isRootScreen()).thenReturn(true);

		// Act
		callback.onClickHome();

		// Assert
		verify(drawerHelper).open();
		verify(navigationResolver, never()).onBackPressed();
		verify(activityView, never()).hideKeyboard();
	}

	@Test
	public void testOnClickHome_notRootScreen() {
		// Arrange
		when(fragmentManager.isRootScreen()).thenReturn(false);

		// Act
		callback.onClickHome();

		// Assert
		verify(drawerHelper, never()).open();
		InOrder inOrder = Mockito.inOrder(navigationResolver, activityView);
		inOrder.verify(navigationResolver)
				.onBackPressed();
		inOrder.verify(activityView)
				.hideKeyboard();
	}

	@Test
	public void testUpdateIcon_notRootScreen() {
		// Arrange
		when(fragmentManager.isRootScreen()).thenReturn(false);

		// Act
		callback.updateIcon();

		// Assert
		verify(toolbarResolver).showBackIcon();
		verify(toolbarResolver, never()).showHomeIcon();
	}

	@Test
	public void testUpdateIcon_RootScreen() {
		// Arrange
		when(fragmentManager.isRootScreen()).thenReturn(true);

		// Act
		callback.updateIcon();

		// Assert
		verify(toolbarResolver, never()).showBackIcon();
		verify(toolbarResolver).showHomeIcon();
	}


	@Test
	public void testUpdateIcon_withoutToolbar_notRootScreen() {
		// Arrange
		callback = new MyToolbarResolverCallback(fragmentManager, drawerHelper, activityView,
				null, navigationResolver);
		when(fragmentManager.isRootScreen()).thenReturn(false);

		// Act
		callback.updateIcon();

		// Assert
		verify(fragmentManager, never()).isRootScreen();
		verify(toolbarResolver, never()).showBackIcon();
		verify(toolbarResolver, never()).showHomeIcon();
	}

	@Test
	public void testUpdateIcon_withoutToolbar_RootScreen() {
		// Arrange
		callback = new MyToolbarResolverCallback(fragmentManager, drawerHelper, activityView,
				null, navigationResolver);
		when(fragmentManager.isRootScreen()).thenReturn(true);

		// Act
		callback.updateIcon();

		// Assert
		verify(fragmentManager, never()).isRootScreen();
		verify(toolbarResolver, never()).showBackIcon();
		verify(toolbarResolver, never()).showHomeIcon();
	}


}