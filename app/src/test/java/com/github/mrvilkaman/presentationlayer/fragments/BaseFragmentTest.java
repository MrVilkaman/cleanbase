package com.github.mrvilkaman.presentationlayer.fragments;


import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseFragment;
import com.github.mrvilkaman.presentationlayer.resolution.ThrowableResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.IToolbar;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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
		Mockito.doNothing().when(fragment)
				.daggerInject();
		Mockito.doReturn(null).when(fragment)
				.getComponent(Matchers.any());
		fragment.uiResolver = uiRes;
		fragment.toolbar = toolbar;
		fragment.activityView = activityView;
		fragment.activityView = baseActivityView;

		//// TODO: 28.10.16 maybe need use dagger2 for inject test module?
	}

}
