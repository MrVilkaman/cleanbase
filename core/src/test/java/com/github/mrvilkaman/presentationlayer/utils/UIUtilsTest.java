package com.github.mrvilkaman.presentationlayer.utils;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.github.mrvilkaman.presentationlayer.utils.ui.UIUtils;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SuppressWarnings("WrongConstant")
public class UIUtilsTest extends BaseTestCase {

	@Mock EditText editText;

	@Test
	public void testAsString() {

		String s = UIUtils.asString(null);
		Assertions.assertThat(s).isNotNull().isEmpty();

		s = UIUtils.asString(editText);
		Assertions.assertThat(s).isNotNull().isEmpty();

		Editable mock = mock(Editable.class);
		when(mock.toString()).thenReturn("qwer");
		when(editText.getText()).thenReturn(mock);
		s = UIUtils.asString(editText);
		Assertions.assertThat(s).isNotNull().contains("qwer");

	}

	@Test
	public void testChangeVisibility() {
		// Arrange

		InOrder inOrder = Mockito.inOrder(editText);
		// Act
		// Assert
		UIUtils.changeVisibility(editText,true);
		inOrder.verify(editText).setVisibility(eq(View.VISIBLE));
		UIUtils.changeVisibility(editText,false);
		inOrder.verify(editText).setVisibility(eq(View.GONE));
	}


}