package com.github.mrvilkaman.presentationlayer.resolution.navigation;

import android.app.Activity;
import android.content.Intent;

import com.github.mrvilkaman.core.BuildConfig;
import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18,
		manifest = "src/main/AndroidManifest.xml")
public class NavigationResolverImplRoboTest extends BaseTestCase {


	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Mock Activity currentActivity;
	@Mock FragmentResolver fragmentManager;
	@Mock LeftDrawerHelper drawerHelper;
	@Mock ToolbarResolver toolbarResolver;
	@Mock UIResolver uiResolver;
	@Mock BaseActivityView activityView;

	private NavigationResolver resolver;

	@Override
	public void init() {
		resolver = Mockito.spy(
				new NavigationResolverImpl(currentActivity, fragmentManager, drawerHelper,
						toolbarResolver, uiResolver, activityView, () -> mock(BaseFragment.class)));
	}

	@Test
	public void testOpenLink_noscheme() {
		// Act
		resolver.openLinkInBrowser("ya.ru");

		// Assert
		ArgumentCaptor<Intent> argument = forClass(Intent.class);
		verify(currentActivity).startActivity(argument.capture());
		Intent value = argument.getValue();
		assertThat(value.getAction()).isEqualTo(Intent.ACTION_VIEW);
		assertThat(value.getData().toString()).isEqualTo("http://ya.ru");
	}

	@Test
	public void testOpenLink_http() {
		// Act
		resolver.openLinkInBrowser("http://ya.ru");

		// Assert
		ArgumentCaptor<Intent> argument = forClass(Intent.class);
		verify(currentActivity).startActivity(argument.capture());
		Intent value = argument.getValue();
		assertThat(value.getAction()).isEqualTo(Intent.ACTION_VIEW);
		assertThat(value.getData().toString()).isEqualTo("http://ya.ru");
	}

	@Test
	public void testOpenLink_httpwww() {
		// Act
		resolver.openLinkInBrowser("http://www.ya.ru");

		// Assert
		ArgumentCaptor<Intent> argument = forClass(Intent.class);
		verify(currentActivity).startActivity(argument.capture());
		Intent value = argument.getValue();
		assertThat(value.getAction()).isEqualTo(Intent.ACTION_VIEW);
		assertThat(value.getData().toString()).isEqualTo("http://www.ya.ru");
	}

	@Test
	public void testOpenLink_https() {
		// Act
		resolver.openLinkInBrowser("https://ya.ru");

		// Assert
		ArgumentCaptor<Intent> argument = forClass(Intent.class);
		verify(currentActivity).startActivity(argument.capture());
		Intent value = argument.getValue();
		assertThat(value.getAction()).isEqualTo(Intent.ACTION_VIEW);
		assertThat(value.getData().toString()).isEqualTo("https://ya.ru");
	}
}