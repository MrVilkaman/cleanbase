package com.github.mrvilkaman.presentationlayer.resolution.navigation;

import android.app.Activity;

import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;


public class NavigationResolverImplEmptyToolbarTest extends BaseTestCase {

	@Mock BaseFragment baseFragment;
	@Mock Activity currentActivity;
	@Mock FragmentResolver fragmentManager;
	@Mock UIResolver uiResolver;
	@Mock BaseActivityView activityView;

	private NavigationResolver resolver;

	@Override
	public void init() {
		resolver = Mockito.spy(
				new NavigationResolverImpl(currentActivity, fragmentManager, null,
						null, uiResolver, activityView, () -> baseFragment));
	}

	@Test
	public void testInit_hasFragment() {
		// Arrange
		when(fragmentManager.hasFragment()).thenReturn(true);

		// Act
		resolver.init();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager);
		inOrder.verify(fragmentManager,never()).setCallback(any());
		inOrder.verify(fragmentManager).hasFragment();
	}

	@Test
	public void testInit_noFragment_noDrawer() {
		// Arrange
		when(fragmentManager.hasFragment()).thenReturn(false);

		// Act
		resolver.init();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager);
		inOrder.verify(fragmentManager,never()).setCallback(any());
		inOrder.verify(fragmentManager).hasFragment();
		inOrder.verify(fragmentManager).showRootFragment(baseFragment);
	}

	@Test
	public void testInit_noFragment_withDrawer() {
		// Arrange
		when(fragmentManager.hasFragment()).thenReturn(false);

		// Act
		resolver.init();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager);
		inOrder.verify(fragmentManager,never()).setCallback(any());
		inOrder.verify(fragmentManager).hasFragment();
		inOrder.verify(fragmentManager).showRootFragment(baseFragment);
		inOrder.verify(fragmentManager,never()).addStaticFragment(anyInt(), any());
	}


	@Test
	public void testOnBackPressed_noFragmentActionHasOnBackPressedTrue() {
		// Arrange
		when(fragmentManager.processBackFragment()).thenReturn(true);
		when(fragmentManager.onBackPressed()).thenReturn(true);

		// Act
		resolver.onBackPressed();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, activityView, resolver);
		inOrder.verify(fragmentManager).processBackFragment();
		inOrder.verify(activityView).hideProgress();
		inOrder.verify(fragmentManager).onBackPressed();

		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;
		inOrder.verify(resolver, never()).exit();
	}


	@Test
	public void testShowFragment_hasDrawer() {
		testShowFragmentBase(() -> resolver.showFragment(null)).verify(fragmentManager)
				.showFragment(any());
	}

	@Test
	public void testShowFragment_noDrawer() {
		testShowFragmentBaseNoDrawer(() -> resolver.showFragment(null)).verify(fragmentManager)
				.showFragment(any());
	}

	@Test
	public void testShowFragmentWithoutBackStack_hasDrawer() {
		testShowFragmentBase(() -> resolver.showFragmentWithoutBackStack(null))
				.verify(fragmentManager).showFragmentWithoutBackStack(any());
	}

	@Test
	public void testShowFragmentWithoutBackStack_noDrawer() {
		testShowFragmentBaseNoDrawer(() -> resolver.showFragmentWithoutBackStack(null))
				.verify(fragmentManager).showFragmentWithoutBackStack(any());
	}


	@Test
	public void testShowRootFragment_hasDrawer() {
		testShowFragmentBase(() -> resolver.showRootFragment(null)).verify(fragmentManager)
				.showRootFragment(any());
	}

	@Test
	public void testShowRootFragment_noDrawer() {
		testShowFragmentBaseNoDrawer(() -> resolver.showRootFragment(null)).verify(fragmentManager)
				.showRootFragment(any());
	}


	@Test
	public void testBack_DrawerIsClose() {
		// Arrange
		// Act
		resolver.back();

		// Assert
		InOrder inOrder = Mockito.inOrder(resolver, fragmentManager);
		inOrder.verify(resolver).onBackPressed();
	}

	/*TOOLS*/
	private InOrder testShowFragmentBase(Runnable action) {
		// Arrange

		// Act
		action.run();

		// Assert
		return Mockito.inOrder(fragmentManager);
	}

	private InOrder testShowFragmentBaseNoDrawer(Runnable action) {
		// Arrange

		// Act
		action.run();

		// Assert

		return Mockito.inOrder(fragmentManager);
	}



}