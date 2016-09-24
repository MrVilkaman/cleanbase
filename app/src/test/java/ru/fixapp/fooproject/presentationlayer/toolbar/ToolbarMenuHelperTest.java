package ru.fixapp.fooproject.presentationlayer.toolbar;

import android.view.Menu;
import android.view.MenuItem;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import ru.fixapp.fooproject.BaseTestCase;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ToolbarMenuHelperTest extends BaseTestCase {

	@Mock Runnable callback;
	private ToolbarMenuHelper helper;

	@Override
	public void init() {
		helper = new ToolbarMenuHelper(callback);
	}

	@SuppressWarnings("ResourceType")
	@Test
	public void testOnPrepareOptionsMenu() {
		// Arrange
		helper.runnableMap.put(2312, () -> {
		});

		MenuItem menuItem = mock(MenuItem.class);
		Menu menu = mock(Menu.class);
		when(menu.add(anyInt(), anyInt(), anyInt(), anyString())).thenReturn(menuItem);

		// Act
		helper.onPrepareOptionsMenu(menu);
		// Assert
		verify(menu).clear();
		verify(menuItem).setIcon(2312);
		verify(menuItem).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


	}

	@Test
	public void testOnOptionsItemSelected() {
		// Arrange
		MenuItem menuItem = mock(MenuItem.class);
		when(menuItem.getItemId()).thenReturn(2312);
		Runnable mo = mock(Runnable.class);
		helper.runnableMap.put(2312, mo);

		// Act
		helper.onOptionsItemSelected(menuItem);

		// Assert
		verify(mo).run();
	}

	@Test
	public void testShowIcon() {
		// Arrange

		// Act
		helper.showIcon(1234, () -> {
		});

		// Assert
		boolean b = helper.runnableMap.containsKey(1234);
		Assert.assertTrue(b);
	}

	@Test
	public void testClear() {
		// Arrange
		helper.runnableMap.put(2312, null);

		// Act
		Assertions.assertThat(helper.runnableMap)
				.isNotEmpty();
		helper.clear();

		// Assert
		verify(callback).run();
		Assertions.assertThat(helper.runnableMap)
				.isEmpty();

	}

	@Test
	public void testRemove() {
		// Arrange
		helper.runnableMap.put(2312, null);

		// Act
		Assertions.assertThat(helper.runnableMap)
				.isNotEmpty();
		helper.remove(2312);

		// Assert
		verify(callback).run();
		Assertions.assertThat(helper.runnableMap)
				.isEmpty();
	}


}