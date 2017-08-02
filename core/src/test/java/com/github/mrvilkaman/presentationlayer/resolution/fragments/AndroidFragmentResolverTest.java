package com.github.mrvilkaman.presentationlayer.resolution.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.fragments.core.ISingletonFragment;
import com.github.mrvilkaman.testsutils.BaseTestCase;
import com.github.mrvilkaman.testsutils.Tutils;
import com.github.mrvilkaman.utils.bus.Bus;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SuppressWarnings("ResourceType")
@SuppressLint("CommitTransaction")
public class AndroidFragmentResolverTest extends BaseTestCase {


	private static final int containerID = 90;
	@Mock FragmentManager fragmentManager;
	private FragmentResolver resolver;
	@Mock Bus bus;

	@Override
	public void init() {
		resolver = new AndroidFragmentResolver(fragmentManager, containerID,bus);
	}

	@Test
	public void testProcessBackFragment_withoutOnBackPressedListener() {
		// Arrange
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock(Fragment.class));

		// Act
		boolean backFragment = resolver.processBackFragment();

		// Assert
		Assert.assertTrue(backFragment);
	}

	@Test
	public void testProcessBackFragment_OnBackPressedListenerFragmentNotUse() {
		// Arrange
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock(BaseFragment.class));

		// Act
		boolean backFragment = resolver.processBackFragment();

		// Assert
		Assert.assertTrue(backFragment);
	}

	@Test
	public void testProcessBackFragment_OnBackPressedListenerFragmentHandleAction() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		when(mock.onBackPressed()).thenReturn(true);
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock);

		// Act
		boolean backFragment = resolver.processBackFragment();

		// Assert
		Assert.assertFalse(backFragment);
	}

	@Test
	public void testOnBackPressed_withoutCurrentFragment() {
		// Arrange

		// Act
		boolean b = resolver.checkBackStack();

		// Assert
		Assert.assertFalse(b);
	}

	@Test
	public void testOnBackPressed_currentFragmentIsRoot() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		when(mock.getPreviousFragment()).thenReturn(null);
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock);

		// Act
		boolean b = resolver.checkBackStack();

		// Assert
		verify(fragmentManager,never()).popBackStackImmediate(anyString(),anyInt());
		Assert.assertFalse(b);
	}

	@Test
	public void testOnBackPressed_currentFragmentIsNotRoot() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		when(mock.getPreviousFragment()).thenReturn("qwer");
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock);

		// Act
		boolean b = resolver.checkBackStack();

		// Assert
		Assert.assertTrue(b);
	}

	@Test
	public void testPopBackStack() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		when(mock.getPreviousFragment()).thenReturn("qwer");
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock);

		// Act
		resolver.popBackStack();
		// Assert
		verify(fragmentManager).popBackStackImmediate(eq("qwer"),anyInt());

	}



	//	@Test
//	public void testShowFragment() {
//		Assert.fail();
//	}
//
//	@Test
//	public void testShowRootFragment() {
//		Assert.fail();
//	}
//
//	@Test
//	public void testShowFragmentWithoutBackStack() {
//		Assert.fail();
//	}
//
//	@Test
//	public void testLoadRootFragment() {
//		Assert.fail();
//	}
//
//	@Test
//	public void testSetTargetFragmentCode() {
//		Assert.fail();
//	}

	@Test
	public void testStartActivityForResult() {
		// Arrange
		Intent mock = mock(Intent.class);
		BaseFragment mockFrag = mock(BaseFragment.class);
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mockFrag);

		// Act
		resolver.startActivityForResult(mock,99);

		// Assert
		verify(mockFrag).startActivityForResult(eq(mock),eq(99));

	}

	@Test
	public void testStartActivityForResult_fragmentNotFound() {
		// Act
		Intent mock = mock(Intent.class);
		resolver.startActivityForResult(mock,99);

		// Assert
		Assert.assertTrue(true); // no exceptions here
	}

	@Test
	public void testIsRootScreen_notPrevScreen() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		when(mock.getPreviousFragment()).thenReturn(null);
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock);
		when(fragmentManager.getBackStackEntryCount()).thenReturn(0);


		// Act
		boolean rootScreen = resolver.isRootScreen();

		// Assert
		Assert.assertTrue(rootScreen);
	}


	@Test
	public void testIsRootScreen_notPrevScreenAndgetBackStackEntryCount_0() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		when(mock.getPreviousFragment()).thenReturn(null);
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock);
		when(fragmentManager.getBackStackEntryCount()).thenReturn(0);


		// Act
		boolean rootScreen = resolver.isRootScreen();

		// Assert
		Assert.assertTrue(rootScreen);
	}

	@Test
	public void testIsRootScreen_hasPrevScreenAndNullFrags() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		when(mock.getPreviousFragment()).thenReturn("qwer");
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock);
		when(fragmentManager.getFragments()).thenReturn(null);

		// Act
		boolean rootScreen = resolver.isRootScreen();

		// Assert
		Assert.assertTrue(rootScreen);
	}

	@Test
	public void testIsRootScreen_MoreTransactionCount() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		when(mock.getPreviousFragment()).thenReturn(null);
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock);
		when(fragmentManager.getFragments()).thenReturn(Arrays.asList(mock,mock(BaseFragment.class)));

		// Act
		boolean rootScreen = resolver.isRootScreen();

		// Assert
		Assert.assertTrue(rootScreen);
	}

	@Test
	public void testHasFragment_has() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		when(fragmentManager.findFragmentById(containerID)).thenReturn(mock);

		// Act
		boolean b = resolver.hasFragment();

		// Assert
		Assert.assertTrue(b);
	}

	@Test
	public void testHasFragment_hasnt() {

		// Act
		boolean b = resolver.hasFragment();

		// Assert
		Assert.assertFalse(b);
	}

	@Test
	public void testAddStaticFragment() {
		// Arrange
		BaseFragment mock = mock(StaticFragment.class);
		FragmentTransaction transaction = Tutils.mockBuilder(FragmentTransaction.class);
		when(fragmentManager.beginTransaction()).thenReturn(transaction);

		// Act
		resolver.addStaticFragment(R.id.drawer_layout,mock);

		// Assert
		InOrder inOrder = Mockito.inOrder(fragmentManager, transaction);
		inOrder.verify(fragmentManager).beginTransaction();
		inOrder.verify(transaction).add(eq(R.id.drawer_layout),eq(mock));
		inOrder.verify(transaction).commit();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddStaticFragment_fragment_notStatic() {
		// Arrange
		BaseFragment mock = mock(BaseFragment.class);
		FragmentTransaction transaction = Tutils.mockBuilder(FragmentTransaction.class);
		when(fragmentManager.beginTransaction()).thenReturn(transaction);

		// Act
		resolver.addStaticFragment(R.id.drawer_layout,mock);

	}

	public static class StaticFragment extends BaseFragment implements ISingletonFragment{

		@Override
		public void daggerInject() {

		}

		@Override
		protected void onCreateView(View view, Bundle savedInstanceState) {

		}

		@Override
		protected int getLayoutId() {
			return 0;
		}
	}
}