package com.github.mrvilkaman.presentationlayer.resolution.drawer;

import org.junit.Assert;
import org.junit.Test;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.testsutils.BaseTestCase;


public class LeftDrawerHelperEmptyTest extends BaseTestCase {



	// also for good test coverage
	@Test
	public void testAll(){
	    // Arrange
		LeftDrawerHelper drawerHelper = new LeftDrawerHelperEmpty();

		// Act
		drawerHelper.open();
		drawerHelper.close();
		drawerHelper.isOpen();
		drawerHelper.close(() -> {});
		drawerHelper.getDrawerLayout();
		drawerHelper.getDrawerContentFrame();
		BaseFragment drawerFragment = drawerHelper.getDrawerFragment();
		boolean hasDrawer = drawerHelper.hasDrawer();

		// Assert
		Assert.assertFalse(hasDrawer);
		Assert.assertNull(drawerFragment);
	}
}