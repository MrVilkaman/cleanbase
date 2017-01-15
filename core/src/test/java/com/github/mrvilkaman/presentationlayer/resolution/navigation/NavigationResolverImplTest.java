package com.github.mrvilkaman.presentationlayer.resolution.navigation;

import android.app.Activity;
import android.content.Intent;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.presentationlayer.activities.BaseActivity;
import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper.LeftDrawerHelperCallback;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class NavigationResolverImplTest extends BaseTestCase {

	@Mock Activity currentActivity;
	@Mock FragmentResolver fragmentManager;
	@Mock LeftDrawerHelper drawerHelper;
	@Mock ToolbarResolver toolbarResolver;
	@Mock UIResolver uiResolver;
	@Mock BaseActivityView activityView;
	@Mock BaseFragment baseFragment;

	private NavigationResolver resolver;

	@Override
	public void init() {
		resolver = Mockito.spy(
				new NavigationResolverImpl(currentActivity, fragmentManager, drawerHelper,
						toolbarResolver, uiResolver, activityView, () -> baseFragment));
		when(drawerHelper.hasDrawer()).thenReturn(true);

	}

	@Test
	public void testInit_hasFragment() {
		// Arrange
		when(fragmentManager.hasFragment()).thenReturn(true);

		// Act
		resolver.init();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, toolbarResolver);
		inOrder.verify(fragmentManager)
				.setCallback(any());
		inOrder.verify(toolbarResolver)
				.setCallback(any());
		inOrder.verify(fragmentManager)
				.hasFragment();
	}

	@Test
	public void testInit_noFragment_noDrawer() {
		// Arrange
		when(fragmentManager.hasFragment()).thenReturn(false);
		when(drawerHelper.hasDrawer()).thenReturn(false);

		// Act
		resolver.init();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper, toolbarResolver);
		inOrder.verify(fragmentManager)
				.setCallback(any());
		inOrder.verify(toolbarResolver)
				.setCallback(any());
		inOrder.verify(fragmentManager)
				.hasFragment();
		inOrder.verify(fragmentManager)
				.showRootFragment(baseFragment);
		inOrder.verify(drawerHelper)
				.hasDrawer();
	}

	@Test
	public void testInit_noFragment_withDrawer() {
		// Arrange
		when(fragmentManager.hasFragment()).thenReturn(false);
		when(drawerHelper.hasDrawer()).thenReturn(true);
		when(drawerHelper.getDrawerContentFrame()).thenReturn(11);
		BaseFragment mockDrawer = mock(BaseFragment.class);
		when(drawerHelper.getDrawerFragment()).thenReturn(mockDrawer);

		// Act
		resolver.init();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, toolbarResolver, drawerHelper);
		inOrder.verify(fragmentManager)
				.setCallback(any());
		inOrder.verify(toolbarResolver)
				.setCallback(any());
		inOrder.verify(fragmentManager)
				.hasFragment();
		inOrder.verify(fragmentManager)
				.showRootFragment(baseFragment);
		inOrder.verify(drawerHelper)
				.hasDrawer();
		inOrder.verify(fragmentManager)
				.addStaticFragment(eq(11), eq(mockDrawer));
	}

	@Test
	public void testOnBackPressed_hasFragmentAction() {
		// Arrange
		when(fragmentManager.processBackFragment()).thenReturn(false);

		// Act
		resolver.onBackPressed();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, activityView);
		inOrder.verify(fragmentManager)
				.processBackFragment();
		inOrder.verify(activityView, never())
				.hideProgress();
	}

	@Test
	public void testOnBackPressed_noFragmentActionHasOnBackPressedTrue() {
		// Arrange
		when(fragmentManager.processBackFragment()).thenReturn(true);
		when(fragmentManager.onBackPressed()).thenReturn(true);

		// Act
		resolver.onBackPressed();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, activityView, resolver,
				toolbarResolver);
		inOrder.verify(fragmentManager)
				.processBackFragment();
		inOrder.verify(activityView)
				.hideProgress();
		inOrder.verify(fragmentManager)
				.onBackPressed();
		inOrder.verify(toolbarResolver)
				.updateIcon();
		inOrder.verify(toolbarResolver)
				.clear();

		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;
		inOrder.verify(resolver, never())
				.exit();
	}

	@Test
	public void testOnBackPressed_noFragmentActionHasOnBackPressedFalse() {
		// Arrange
		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;
		when(fragmentManager.processBackFragment()).thenReturn(true);
		when(fragmentManager.onBackPressed()).thenReturn(false);
		doNothing().when(resolver)
				.exit();

		// Act
		resolver.onBackPressed();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, activityView, resolver,
				toolbarResolver);
		inOrder.verify(fragmentManager)
				.processBackFragment();
		inOrder.verify(activityView)
				.hideProgress();
		inOrder.verify(fragmentManager)
				.onBackPressed();
		inOrder.verify(toolbarResolver, never())
				.updateIcon();
		inOrder.verify(toolbarResolver,never())
				.clear();
		inOrder.verify(resolver)
				.exit();
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
		testShowFragmentBase(() -> resolver.showFragmentWithoutBackStack(null)).verify(
				fragmentManager)
				.showFragmentWithoutBackStack(any());
	}

	@Test
	public void testShowFragmentWithoutBackStack_noDrawer() {
		testShowFragmentBaseNoDrawer(() -> resolver.showFragmentWithoutBackStack(null)).verify(
				fragmentManager)
				.showFragmentWithoutBackStack(any());
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
	public void testSetTargetFragment() {
		// Act
		resolver.setTargetFragment(99);

		// Assert
		verify(fragmentManager).setTargetFragmentCode(eq(99));
	}

	@Test
	public void testOpenActivity() {
		// Act
		resolver.openActivity(BaseActivity.class);

		// Assert
		verify(currentActivity).startActivity(any());
		currentActivity.finish();
	}

	@Test
	public void testStartActivityForResult() {
		// Act
		Intent mock = mock(Intent.class);
		resolver.startActivityForResult(mock, 99);

		// Assert
		verify(currentActivity).startActivityForResult(eq(mock), eq(99));
	}

	@Test
	public void testStartActivityForResultFormFragment() {
		// Act
		Intent mock = mock(Intent.class);
		resolver.startActivityForResultFormFragment(mock, 99);

		// Assert
		verify(fragmentManager).startActivityForResult(eq(mock), eq(99));
	}

	@Test
	public void testBack_DrawerIsOpen() {
		// Arrange
		when(drawerHelper.isOpen()).thenReturn(true);

		// Act
		resolver.back();

		// Assert
		InOrder inOrder = Mockito.inOrder(drawerHelper, resolver, fragmentManager,
				toolbarResolver);
		inOrder.verify(drawerHelper)
				.isOpen();
		inOrder.verify(drawerHelper)
				.close();
		inOrder.verify(resolver, never())
				.onBackPressed();
		inOrder.verify(toolbarResolver, never())
				.updateIcon();
	}

	@Test
	public void testBack_DrawerIsClose() {
		// Arrange
		when(drawerHelper.isOpen()).thenReturn(false);

		// Act
		resolver.back();

		// Assert
		InOrder inOrder = Mockito.inOrder(drawerHelper, resolver, fragmentManager,
				toolbarResolver);
		inOrder.verify(drawerHelper)
				.isOpen();
		inOrder.verify(drawerHelper, never())
				.close();
		inOrder.verify(resolver)
				.onBackPressed();
		inOrder.verify(toolbarResolver)
				.updateIcon();
	}


	@Test
	public void testOnExit_oneClick() {
		// Arrange
		when(currentActivity.isTaskRoot()).thenReturn(true);
		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;

		// Act
		resolver.exit();

		// Assert
		verify(uiResolver).showToast(R.string.toast_exit);
	}

	@Test
	public void testOnExitFrom_NotRoot_oneClick() {
		// Arrange
		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;

		when(currentActivity.isTaskRoot()).thenReturn(false);

		// Act
		resolver.exit();

		// Assert
		verify(uiResolver, never())
				.showToast(R.string.toast_exit);
		verify(currentActivity, times(1))
				.finish();
	}

	@Test
	public void testOnExit_twoClick() {
		// Arrange
		when(currentActivity.isTaskRoot()).thenReturn(true);
		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;

		// Act
		resolver.exit();
		resolver.exit();

		// Assert
		InOrder inOrder = inOrder(uiResolver, currentActivity);
		inOrder.verify(uiResolver, times(1))
				.showToast(R.string.toast_exit);
		inOrder.verify(currentActivity, times(1))
				.finish();
	}

	@Test
	public void testOnClickHome() {
		// Arrange

		// Act
		resolver.onBackPressed();

		// Assert

	}

	/*TOOLS*/
	private InOrder testShowFragmentBase(Runnable action) {
		// Arrange
		when(drawerHelper.isOpen()).thenReturn(true);

		// Act
		action.run();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper, toolbarResolver);

		inOrder.verify(drawerHelper)
				.isOpen();

		ArgumentCaptor<LeftDrawerHelperCallback> argument =
				forClass(LeftDrawerHelperCallback.class);
		inOrder.verify(drawerHelper)
				.close(argument.capture());
		LeftDrawerHelperCallback value = argument.getValue();
		value.onClose();
		inOrder.verify(toolbarResolver)
				.clear();
		return inOrder;
	}

	private InOrder testShowFragmentBaseNoDrawer(Runnable action) {
		// Arrange
		when(drawerHelper.isOpen()).thenReturn(false);

		// Act
		action.run();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper, toolbarResolver);

		inOrder.verify(drawerHelper)
				.isOpen();
		inOrder.verify(drawerHelper, never())
				.close(any());
		inOrder.verify(toolbarResolver)
				.clear();
		return inOrder;
	}


}