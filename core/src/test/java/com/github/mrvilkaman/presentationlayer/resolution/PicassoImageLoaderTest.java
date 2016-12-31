package com.github.mrvilkaman.presentationlayer.resolution;

import android.widget.ImageView;

import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.testsutils.BaseTestCase;
import com.github.mrvilkaman.testsutils.Tutils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PicassoImageLoaderTest extends BaseTestCase {

	@Mock(answer = Answers.RETURNS_MOCKS) Picasso picasso;

 RequestCreator creator = Tutils.mockBuilder(RequestCreator.class);
	@Mock ImageView view;

	private ImageLoader imageLoader;

	@Override
	public void init() {
		super.init();
		imageLoader = new PicassoImageLoader(picasso);
		when(picasso.load(any(String.class))).thenReturn(creator);
		when(picasso.load(any(File.class))).thenReturn(creator);
	}

	@Test
	public void testLoadUrl_minWay() {
		// Act
		imageLoader.loadUrl("url")
				.into(view);

		// Assert
		InOrder inOrder = Mockito.inOrder(creator, picasso);
		inOrder.verify(picasso)
				.load(eq("url"));
		inOrder.verify(creator)
				.into(view);
		verify(creator, never()).centerCrop();
		verify(creator, never()).resize(anyInt(), anyInt());

	}


	@Test
	public void testLoadUrl_withFullCustomSizeAndErrorAndLoadHolders() {
		// Act
		imageLoader.loadUrl("url")
				.size(150, 250)
				.holder(R.drawable.ic_home)
				.error(R.drawable.ic_back)
				.into(view);

		// Assert
		InOrder inOrder = Mockito.inOrder(creator, picasso);
		inOrder.verify(picasso)
				.load(eq("url"));
		inOrder.verify(creator)
				.resize(150, 250);
		inOrder.verify(creator)
				.placeholder(R.drawable.ic_home);
		inOrder.verify(creator)
				.error(R.drawable.ic_back);
		inOrder.verify(creator)
				.into(view);

		verify(creator).centerCrop();
	}

	@Test
	public void testLoadFile_withWidth() {
		// Arrange

		// Act
		imageLoader.loadFile("path")
				.width(250)
				.into(view);

		// Assert
		InOrder inOrder = Mockito.inOrder(creator, picasso);
		inOrder.verify(picasso)
				.load(any(File.class));
		inOrder.verify(creator)
				.resize(250, 0);
		inOrder.verify(creator)
				.onlyScaleDown();
		inOrder.verify(creator)
				.into(view);

		verify(creator, never()).centerCrop();
	}


	@Test
	public void testLoadFile_withheight() {
		// Arrange

		// Act
		imageLoader.loadFile("path")
				.height(250)
				.into(view);

		// Assert
		InOrder inOrder = Mockito.inOrder(creator, picasso);

		ArgumentCaptor<File> argument = ArgumentCaptor.forClass(File.class);
		inOrder.verify(picasso).load(argument.capture());
		File value = argument.getValue();
		assertThat(value.getPath()).isEqualTo("path");

		inOrder.verify(creator).resize(0, 250);
		inOrder.verify(creator).onlyScaleDown();
		inOrder.verify(creator).into(view);

		verify(creator, never()).centerCrop();
	}


}