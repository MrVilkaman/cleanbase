package ru.fixapp.fooproject.presentationlayer.fragments;


import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.fixapp.fooproject.BuildConfig;
import ru.fixapp.fooproject.TestApplication;
import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.toolbar.IToolbar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = TestApplication.class,
		manifest = "src/main/AndroidManifest.xml")
public abstract class BaseFragmentTest {

	@Mock protected IToolbar toolbar;
	@Mock protected BaseActivityView activityView;
	@Mock protected UIResolver uiRes;
	@Mock protected ThrowableResolver throwable;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	protected void initDep(BaseFragment fragment) {
		doNothing().when(fragment)
				.daggerInject();
		doReturn(activityView).when(fragment)
				.getActivityView();
		doReturn(toolbar).when(fragment)
				.getToolbar();
		doReturn(throwable).when(fragment)
				.createThrowableResolver(any());
		doReturn(uiRes).when(fragment)
				.createUiResolver(any());
	}

}
