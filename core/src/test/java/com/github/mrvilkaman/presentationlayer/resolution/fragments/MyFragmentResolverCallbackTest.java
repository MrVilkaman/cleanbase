package com.github.mrvilkaman.presentationlayer.resolution.fragments;

import org.junit.Test;
import org.mockito.Mock;

import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;
import com.github.mrvilkaman.testsutils.BaseTestCase;

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