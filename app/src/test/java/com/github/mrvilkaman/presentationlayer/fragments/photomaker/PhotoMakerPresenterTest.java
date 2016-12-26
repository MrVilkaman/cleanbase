package com.github.mrvilkaman.presentationlayer.fragments.photomaker;

import android.os.Bundle;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.github.mrvilkaman.testsutils.BaseTestCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PhotoMakerPresenterTest extends BaseTestCase {

	@Mock PhotoMakerView view;
	private PhotoMakerPresenter presenter;

	@Override
	public void init() {
		super.init();
		presenter = Mockito.spy(new PhotoMakerPresenter());
		presenter.setView(view);
	}

	@Test
	public void testLoadFile_saveImagePath() {
		// Arrange
		assertThat(presenter.imagePath).isNull();

		// Act
		presenter.loadFile("path");

		// Assert
		assertThat(presenter.imagePath).isEqualTo("path");
	}

	@Test
	public void testLoadFile_showImage() {
		// Act
		presenter.loadFile("path");

		// Assert
		verify(view).showImage(eq("path"));
	}

	@Test
	public void testLoadFile_showImagePathIsNull() {
		// Act
		presenter.loadFile(null);

		// Assert
		verify(view, never()).showImage(any());
	}

	@Test
	public void testInit_showImagePathIsNull() {

		// Act
		presenter.init(null);

		// Assert
		verify(presenter,never()).loadFile(any());
	}

	@Test
	public void testInit_pathIsNotEmpty() {
		// Arrange
		doNothing().when(presenter).loadFile(any());
		Bundle bundle = mock(Bundle.class);
		when(bundle.getString("path")).thenReturn("path");

		// Act
		presenter.init(bundle);

		// Assert
		verify(presenter).loadFile(eq("path"));
	}

	@Test
	public void testSaveInstanceState_pathIsNull() {
		// Arrange
		Bundle bundle = mock(Bundle.class);

		// Act
		presenter.saveInstanceState(bundle);

		// Assert
		verify(bundle,never()).putString(eq("path"),anyString());
	}

	@Test
	public void testSaveInstanceState_pathIsNotEmpty() {
		// Arrange
		presenter.imagePath = "qwer";
		Bundle bundle = mock(Bundle.class);

		// Act
		presenter.saveInstanceState(bundle);

		// Assert
		verify(bundle).putString(eq("path"),eq("qwer"));
	}
}