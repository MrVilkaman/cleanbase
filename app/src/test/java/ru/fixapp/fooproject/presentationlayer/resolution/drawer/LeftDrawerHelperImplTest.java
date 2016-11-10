package ru.fixapp.fooproject.presentationlayer.resolution.drawer;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.testsutils.BaseTestCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class LeftDrawerHelperImplTest extends BaseTestCase {

	@Mock View view;
	@Mock View contentView;
	@Mock DrawerLayout drawerLayout;
	private LeftDrawerHelperImpl drawerHelper;

	@Override
	public void init() {
		when(view.findViewById(R.id.drawer_layout)).thenReturn(drawerLayout);
		when(view.findViewById(R.id.all_content)).thenReturn(contentView);

		drawerHelper = new LeftDrawerHelperImpl(view) {
			@Override
			public BaseFragment getDrawerFragment() {
				return null;
			}
		};
	}

	@Test
	public void testInit() {
		// Act
		drawerHelper.init();

		// Assert
		verify(drawerLayout).addDrawerListener(drawerHelper);
	}

	@Test
	public void testOpen() {
		// Act
		drawerHelper.open();
		//
		verify(drawerLayout).openDrawer(Gravity.LEFT);
	}

	@Test
	public void testGetDrawerContentFrame() {
		// Act
		int drawerContentFrame = drawerHelper.getDrawerContentFrame();

		// Assert
		assertThat(drawerContentFrame).isEqualTo(R.id.menu_frame);
	}

	@Test
	public void testHasDrawer() {
		// Act
		boolean hasDrawer = drawerHelper.hasDrawer();

		// Assert
		Assert.assertTrue(hasDrawer);
	}

	@Test
	public void testGetDrawerFragment() {
		// Act
		BaseFragment drawerFragment = drawerHelper.getDrawerFragment();

		// Assert
		Assert.assertNull(drawerFragment);
	}

	@Test
	public void testIsOpen() {
		// Arrange
		when(drawerLayout.isDrawerOpen(Gravity.LEFT)).thenReturn(true);
		// Act
		boolean open = drawerHelper.isOpen();

		//
		Assert.assertTrue(open);
		verify(drawerLayout).isDrawerOpen(Gravity.LEFT);
	}

	@Test
	public void testClose() {
		// Act
		drawerHelper.close();
		//
		verify(drawerLayout).closeDrawers();
	}

	@Test
	public void testCloseWithParam() {
		// Act
		drawerHelper.close(() -> {
		});
		//
		verify(drawerLayout).closeDrawers();
		Assert.assertNotNull(drawerHelper.leftDrawerHelperCallback);
	}

	@Test
	public void testGetDrawerLayout() {
		// Act
		int drawerLayout = drawerHelper.getDrawerLayout();

		// Assert
		assertThat(drawerLayout).isEqualTo(R.id.drawer_layout);
	}

	@Test
	public void testOnDrawerSlide() {
		View draView = mock(View.class);
		doReturn(R.id.menu_frame).when(draView).getId();
		when(draView.getWidth()).thenReturn(100);

		drawerHelper.onDrawerSlide(draView, 0.5f);

		verify(contentView).setTranslationX(eq(50f));
	}

	@Test
	public void testOnDrawerOpened() {
		drawerHelper.onDrawerOpened(null);
		//do nothing
	}

	@Test
	public void testOnDrawerClosed() {
		// Arrange
		LeftDrawerHelper.LeftDrawerHelperCallback mock =
				mock(LeftDrawerHelper.LeftDrawerHelperCallback.class);
		drawerHelper.leftDrawerHelperCallback = mock;

		// Act
		drawerHelper.onDrawerClosed(view);

		// Assert
		verify(mock).onClose();
		Assert.assertNull(drawerHelper.leftDrawerHelperCallback);
	}

	@Test
	public void testOnDrawerStateChanged() {
		drawerHelper.onDrawerStateChanged(DrawerLayout.STATE_DRAGGING);
		//do nothing
	}


}