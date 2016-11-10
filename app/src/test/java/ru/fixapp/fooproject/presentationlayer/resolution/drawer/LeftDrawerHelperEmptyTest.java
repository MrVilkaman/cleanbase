package ru.fixapp.fooproject.presentationlayer.resolution.drawer;

import org.junit.Assert;
import org.junit.Test;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.testsutils.BaseTestCase;


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