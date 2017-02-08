package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.mrvilkaman.core.BuildConfig;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.assertj.android.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19,
		manifest = "src/main/AndroidManifest.xml")
public class ViewHolderTest extends BaseTestCase {


	private MySimpleAdapter.ViewHolder<String> holder;
	private View view;

	@Override
	public void init() {
		view = LayoutInflater.from(RuntimeEnvironment.application)
				.inflate(android.R.layout.simple_list_item_1, null);
		holder = new MySimpleAdapter.ViewHolder<>(view);
	}

	@Test
	public void testBind() {
		// Arrange
		TextView textView = (TextView) view.findViewById(android.R.id.text1);

		// Act
		holder.bind("qwer",0,null);

		// Assert
		Assertions.assertThat(textView).hasText("qwer");
	}

	@Test
	public void testOnClick() {
		// Arrange
		ItemListener<String> mock = mock(ItemListener.class);
		ItemListener<String> mockLong = mock(ItemListener.class);
		holder.setListeners(view, mock,mockLong);

		// Act
		view.callOnClick();
		view.performLongClick();

		// Assert
		verify(mock).click(any());
		verify(mockLong).click(any());
	}

}