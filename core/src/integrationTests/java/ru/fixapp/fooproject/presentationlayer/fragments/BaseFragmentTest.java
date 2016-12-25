package ru.fixapp.fooproject.presentationlayer.fragments;


import com.github.mrvilkaman.core.BuildConfig;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.toolbar.IToolbar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, /*application = TestApplication.class,*/
		manifest = "src/main/AndroidManifest.xml")
public abstract class BaseFragmentTest {

	@Mock protected IToolbar toolbar;
	@Mock protected BaseActivityView activityView;
	@Mock protected UIResolver uiRes;
	@Mock protected ThrowableResolver throwable;
	@Mock protected BaseActivityView baseActivityView;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Before
	public void init(){

	}

	protected void initDep(BaseFragment fragment) {
		doNothing().when(fragment)
				.daggerInject(any());
		doReturn(null).when(fragment)
				.getComponent(any());
		fragment.uiResolver = uiRes;
		fragment.toolbar = toolbar;
		fragment.activityView = activityView;
		fragment.throwableResolver = throwable;
		fragment.activityView = baseActivityView;

		//// TODO: 28.10.16 maybe need use dagger2 for inject test module?
	}

}
