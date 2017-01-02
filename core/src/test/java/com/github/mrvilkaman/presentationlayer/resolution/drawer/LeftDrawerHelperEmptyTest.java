package com.github.mrvilkaman.presentationlayer.resolution.drawer;

import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.junit.Assert;
import org.junit.Test;


public class LeftDrawerHelperEmptyTest extends BaseTestCase {



	// also for good test coverage
	@Test
	public void testAll(){
	    // Arrange
		LeftDrawerHelper drawerHelper = new LeftDrawerHelperEmpty();

		// Act
		drawerHelper.init(null);
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