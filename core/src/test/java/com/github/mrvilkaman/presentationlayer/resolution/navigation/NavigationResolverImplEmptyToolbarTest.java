package com.github.mrvilkaman.presentationlayer.resolution.navigation;

import android.app.Activity;

import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper.LeftDrawerHelperCallback;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;


public class NavigationResolverImplEmptyToolbarTest extends BaseTestCase {

	@Mock Activity currentActivity;
	@Mock FragmentResolver fragmentManager;
	@Mock LeftDrawerHelper drawerHelper;
	@Mock UIResolver uiResolver;
	@Mock BaseActivityView activityView;

	private NavigationResolver resolver;

	@Override
	public void init() {
		resolver = Mockito.spy(
				new NavigationResolverImpl(currentActivity, fragmentManager, drawerHelper,
						null, uiResolver, activityView, () -> mock(BaseFragment.class)));
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
		when(drawerHelper.hasDrawer()).thenReturn(false);
		BaseFragment mock = mock(BaseFragment.class);
		when(resolver.createStartFragment()).thenReturn(mock);

		// Act
		resolver.init();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper);
		inOrder.verify(fragmentManager,never()).setCallback(any());
		inOrder.verify(fragmentManager).hasFragment();
		inOrder.verify(fragmentManager).showRootFragment(mock);
		inOrder.verify(drawerHelper).hasDrawer();
	}

	@Test
	public void testInit_noFragment_withDrawer() {
		// Arrange
		when(fragmentManager.hasFragment()).thenReturn(false);
		when(drawerHelper.hasDrawer()).thenReturn(true);
		BaseFragment mock = mock(BaseFragment.class);
		when(resolver.createStartFragment()).thenReturn(mock);
		when(drawerHelper.getDrawerContentFrame()).thenReturn(11);
		BaseFragment mockDrawer = mock(BaseFragment.class);
		when(drawerHelper.getDrawerFragment()).thenReturn(mockDrawer);

		// Act
		resolver.init();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper);
		inOrder.verify(fragmentManager,never()).setCallback(any());
		inOrder.verify(fragmentManager).hasFragment();
		inOrder.verify(fragmentManager).showRootFragment(mock);
		inOrder.verify(drawerHelper).hasDrawer();
		inOrder.verify(fragmentManager).addDrawer(eq(11), eq(mockDrawer));
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
		when(drawerHelper.isOpen()).thenReturn(false);

		// Act
		resolver.back();

		// Assert
		InOrder inOrder = Mockito.inOrder(drawerHelper,resolver, fragmentManager);
		inOrder.verify(drawerHelper).isOpen();
		inOrder.verify(drawerHelper, never()).close();
		inOrder.verify(resolver).onBackPressed();
	}

	/*TOOLS*/
	private InOrder testShowFragmentBase(Runnable action) {
		// Arrange
		when(drawerHelper.isOpen()).thenReturn(true);

		// Act
		action.run();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper);

		inOrder.verify(drawerHelper).isOpen();

		ArgumentCaptor<LeftDrawerHelperCallback> argument =
				forClass(LeftDrawerHelperCallback.class);
		inOrder.verify(drawerHelper).close(argument.capture());
		LeftDrawerHelperCallback value = argument.getValue();
		value.onClose();
		return inOrder;
	}

	private InOrder testShowFragmentBaseNoDrawer(Runnable action) {
		// Arrange
		when(drawerHelper.isOpen()).thenReturn(false);

		// Act
		action.run();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper);

		inOrder.verify(drawerHelper).isOpen();
		inOrder.verify(drawerHelper, never()).close(any());
		return inOrder;
	}



}