package com.github.mrvilkaman.presentationlayer.fragments.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mrvilkaman.core.BuildConfig;
import com.github.mrvilkaman.core.R;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.assertj.android.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19,
		manifest = "src/main/AndroidManifest.xml")
public class BaseCustomViewTest extends BaseTestCase {

	@Mock BasePresenter presenter;
	@Mock IProgressState parentView;
	@Mock View mockView;

	private Context context;
	private BaseCustomView customView;

	@Override
	public void init() {
		context = Mockito.spy(RuntimeEnvironment.application);
		LayoutInflater mock = mock(LayoutInflater.class);
		when(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(mock);
		when(mock.inflate(anyInt(), any(), anyBoolean())).thenReturn(mockView);

	}

	public void bind() {
		customView = new CustomView(context, null);
		customView.presenter = presenter;
		customView.bind(parentView);
	}

	@Test
	public void testHideProgress_fromParent() throws Exception {
		// Arrange
		bind();

		// Act
		customView.hideProgress();

		// Assert
		Mockito.verify(parentView).hideProgress();
	}

	@Test
	public void testShowProgress_fromParent() throws Exception {
		// Arrange
		bind();

		// Act
		customView.showProgress();

		// Assert
		Mockito.verify(parentView).showProgress();
	}

	@Test
	public void testHideProgress_custom() throws Exception {
		// Arrange
		View mock = new View(context);
		when(mockView.findViewById(R.id.progress_wheel_widget)).thenReturn(mock);
		mock.setVisibility(View.VISIBLE);
		bind();

		// Act
		customView.hideProgress();

		// Assert
		Mockito.verify(parentView,never()).hideProgress();
		Assertions.assertThat(mock).isGone();
	}

	@Test
	public void testShowProgress_custom() throws Exception {
		// Arrange
		View mock = new View(context);
		when(mockView.findViewById(R.id.progress_wheel_widget)).thenReturn(mock);
		mock.setVisibility(View.GONE);
		bind();

		// Act
		customView.showProgress();

		// Assert
		Mockito.verify(parentView,never()).showProgress();
		Assertions.assertThat(mock).isVisible();
	}


	@Test
	public void testWithOutPresenter_HideProgress_fromParent() throws Exception {
		// Arrange
		bind();

		// Act
		customView.hideProgress();

		// Assert
		Mockito.verify(parentView).hideProgress();
	}

	@Test
	public void testWithOutPresenter_ShowProgress_fromParent() throws Exception {
		// Arrange
		customView = new CustomView(context, null);
		customView.bind(parentView);

		// Act
		customView.showProgress();

		// Assert
		Mockito.verify(parentView).showProgress();
	}


	public class CustomView extends BaseCustomView {

		public CustomView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
		}

		@Override
		protected void onViewCreate(View inflate, Context context, AttributeSet attrs) {

		}

		@Override
		protected int getLayoutId() {
			return -1;
		}
	}

}