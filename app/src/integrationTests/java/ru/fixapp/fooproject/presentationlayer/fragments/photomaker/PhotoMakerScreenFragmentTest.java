package ru.fixapp.fooproject.presentationlayer.fragments.photomaker;

import android.content.Intent;
import android.os.Bundle;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.fixapp.fooproject.presentationlayer.fragments.BaseFragmentTest;
import ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop.CropImageFragment;
import ru.fixapp.fooproject.presentationlayer.photoulits.PhotoHelper;
import ru.fixapp.fooproject.presentationlayer.resolution.ImageLoader;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startVisibleFragment;


public class PhotoMakerScreenFragmentTest extends BaseFragmentTest {


	private PhotoMakerScreenFragment fragment;
	private PhotoMakerView view;
	@Mock PhotoMakerPresenter presenter;
	@Mock PhotoHelper photoHelper;
	@Mock ImageLoader imageLoader;

	@Override
	public void init() {
		view = fragment = Mockito.spy(PhotoMakerScreenFragment.open());
		doReturn(presenter).when(fragment).getPresenter();
		fragment.photoHelper = photoHelper;
		fragment.imageLoader = imageLoader;
		initDep(fragment);
		startVisibleFragment(fragment);
	}

	@Test
	public void testOnCreateView() {
		verify(presenter).init(any());
	}

	@Test
	public void testOnSaveInstanceStare() {
		// Arrange
		Bundle mock = mock(Bundle.class);

		// Act
		fragment.onSaveInstanceState(mock);

		// Assert
		verify(presenter).saveInstanceState(mock);
	}


	@Test
	public void testOnClickTake() {
		// Act
		fragment.onClickTake();

		// Assert
		verify(photoHelper).openCamera(CropImageFragment.MODE.FREE);
	}

	@Test
	public void testOnClickGallary() {
		// Act
		fragment.onClickGallary();

		// Assert
		verify(photoHelper).openGallery(CropImageFragment.MODE.FREE);
	}

	@Test
	public void testShowImage() {
		// Act
		view.showImage("path");

		// Assert
		imageLoader.showFromFile(eq("path"),eq(fragment.imageView));
	}

	@Test
	public void testOnActivityResult_checkCall() {
		// Arrange

		// Act
		Intent data = new Intent();
		fragment.onActivityResult(99,-1, data);

		// Assert
		verify(photoHelper).onActivityResult(eq(99),eq(-1),eq(data),any());
	}

	@Test
	public void testOnActivityResult_checkCallback() {
		// Arrange
		doAnswer(invocation -> {
			@SuppressWarnings("unchecked")
			PhotoHelper.PhotoHelperCallback callback = (PhotoHelper.PhotoHelperCallback)invocation.getArguments()[3];

			callback.onGetPath("qwer");
		    return null;
		}).when(photoHelper).onActivityResult(anyInt(),anyInt(),any(Intent.class),any(PhotoHelper.PhotoHelperCallback.class));
		// Act
		fragment.onActivityResult(99,-1, null);

		// Assert
		verify(photoHelper).onActivityResult(eq(99),eq(-1),eq(null),any());
	}
}