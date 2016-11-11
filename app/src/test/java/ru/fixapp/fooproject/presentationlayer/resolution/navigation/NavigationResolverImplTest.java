package ru.fixapp.fooproject.presentationlayer.resolution.navigation;

import android.app.Activity;
import android.content.Intent;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.activities.MainActivity;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.fragments.FragmentResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelper;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelper.LeftDrawerHelperCallback;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.toolbar.ToolbarResolver;
import ru.fixapp.fooproject.testsutils.BaseTestCase;

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

	private NavigationResolver resolver;

	@Override
	public void init() {
		resolver = Mockito.spy(
				new NavigationResolverImpl(currentActivity, fragmentManager, drawerHelper,
						toolbarResolver, uiResolver, activityView));
	}

	@Test
	public void testInit_hasFragment() {
		// Arrange
		when(fragmentManager.hasFragment()).thenReturn(true);

		// Act
		resolver.init();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, toolbarResolver);
		inOrder.verify(fragmentManager).setCallback(any());
		inOrder.verify(toolbarResolver).setCallback(any());
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
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper, toolbarResolver);
		inOrder.verify(fragmentManager).setCallback(any());
		inOrder.verify(toolbarResolver).setCallback(any());
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
		InOrder inOrder = Mockito.inOrder(fragmentManager, toolbarResolver, drawerHelper);
		inOrder.verify(fragmentManager).setCallback(any());
		inOrder.verify(toolbarResolver).setCallback(any());
		inOrder.verify(fragmentManager).hasFragment();
		inOrder.verify(fragmentManager).showRootFragment(mock);
		inOrder.verify(drawerHelper).hasDrawer();
		inOrder.verify(fragmentManager).addDrawer(eq(11), eq(mockDrawer));
	}

	@Test
	public void testCreateStartFragment() {
		// Act
		BaseFragment startFragment = resolver.createStartFragment();

		// Assert
		Assertions.assertThat(startFragment).isNotNull();
	}

	@Test
	public void testOnBackPressed_hasFragmentAction() {
		// Arrange
		when(fragmentManager.processBackFragment()).thenReturn(false);

		// Act
		resolver.onBackPressed();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, activityView);
		inOrder.verify(fragmentManager).processBackFragment();
		inOrder.verify(activityView, never()).hideProgress();
	}

	@Test
	public void testOnBackPressed_noFragmentActionHasOnBackPressedTrue() {
		// Arrange
		when(fragmentManager.processBackFragment()).thenReturn(true);
		when(fragmentManager.onBackPressed()).thenReturn(true);

		// Act
		resolver.onBackPressed();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, activityView, resolver, toolbarResolver);
		inOrder.verify(fragmentManager).processBackFragment();
		inOrder.verify(activityView).hideProgress();
		inOrder.verify(fragmentManager).onBackPressed();
		inOrder.verify(toolbarResolver).updateIcon();

		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;
		inOrder.verify(resolver, never()).exit();
	}

	@Test
	public void testOnBackPressed_noFragmentActionHasOnBackPressedFalse() {
		// Arrange
		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;
		when(fragmentManager.processBackFragment()).thenReturn(true);
		when(fragmentManager.onBackPressed()).thenReturn(false);
		doNothing().when(resolver).exit();

		// Act
		resolver.onBackPressed();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, activityView, resolver, toolbarResolver);
		inOrder.verify(fragmentManager).processBackFragment();
		inOrder.verify(activityView).hideProgress();
		inOrder.verify(fragmentManager).onBackPressed();
		inOrder.verify(toolbarResolver, never()).updateIcon();
		inOrder.verify(resolver).exit();
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
	public void testSetTargetFragment() {
		// Act
		resolver.setTargetFragment(99);

		// Assert
		verify(fragmentManager).setTargetFragmentCode(eq(99));
	}

	@Test
	public void testOpenActivity() {
		// Act
		resolver.openActivity(MainActivity.class);

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
		InOrder inOrder = Mockito.inOrder(drawerHelper,resolver, fragmentManager, toolbarResolver);
		inOrder.verify(drawerHelper).isOpen();
		inOrder.verify(drawerHelper).close();
		inOrder.verify(resolver, never()).onBackPressed();
		inOrder.verify(toolbarResolver, never()).updateIcon();
	}

	@Test
	public void testBack_DrawerIsClose() {
		// Arrange
		when(drawerHelper.isOpen()).thenReturn(false);

		// Act
		resolver.back();

		// Assert
		InOrder inOrder = Mockito.inOrder(drawerHelper,resolver, fragmentManager, toolbarResolver);
		inOrder.verify(drawerHelper).isOpen();
		inOrder.verify(drawerHelper, never()).close();
		inOrder.verify(resolver).onBackPressed();
		inOrder.verify(toolbarResolver).updateIcon();
	}


	@Test
	public void testOnExit_oneClick() {
		// Arrange
		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;

		// Act
		resolver.exit();

		// Assert
		verify(uiResolver).showToast(R.string.exit_toast);
	}

	@Test
	public void testOnExit_twoClick() {
		// Arrange
		NavigationResolverImpl resolver = (NavigationResolverImpl) this.resolver;

		// Act
		resolver.exit();
		resolver.exit();

		// Assert
		InOrder inOrder = inOrder(uiResolver, currentActivity);
		inOrder.verify(uiResolver,times(1)).showToast(R.string.exit_toast);
		inOrder.verify(currentActivity,times(1)).finish();
	}

	@Test
	public void testOnClickHome() {
		// Arrange

		// Act
		resolver.onBackPressed();

		// Assert

	}

	@Test
	public void testUpdateIcon() {

	}

	/*TOOLS*/
	private InOrder testShowFragmentBase(Runnable action) {
		// Arrange
		when(drawerHelper.isOpen()).thenReturn(true);

		// Act
		action.run();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper, toolbarResolver);

		inOrder.verify(drawerHelper).isOpen();

		ArgumentCaptor<LeftDrawerHelperCallback> argument =
				ArgumentCaptor.forClass(LeftDrawerHelperCallback.class);
		inOrder.verify(drawerHelper).close(argument.capture());
		LeftDrawerHelperCallback value = argument.getValue();
		value.onClose();
		inOrder.verify(toolbarResolver).clear();
		return inOrder;
	}

	private InOrder testShowFragmentBaseNoDrawer(Runnable action) {
		// Arrange
		when(drawerHelper.isOpen()).thenReturn(false);

		// Act
		action.run();

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, drawerHelper, toolbarResolver);

		inOrder.verify(drawerHelper).isOpen();
		inOrder.verify(drawerHelper, never()).close(any());
		inOrder.verify(toolbarResolver).clear();
		return inOrder;
	}



}