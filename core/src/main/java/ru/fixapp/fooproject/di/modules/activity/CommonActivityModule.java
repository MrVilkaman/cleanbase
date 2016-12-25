package ru.fixapp.fooproject.di.modules.activity;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dagger.Module;
import dagger.Provides;
import ru.fixapp.fooproject.di.PerActivity;
import ru.fixapp.fooproject.presentationlayer.activities.BaseActivityView;
import ru.fixapp.fooproject.presentationlayer.resolution.ProvideFragmentCallback;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.ThrowableResolverImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.UIResolverImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.drawer.LeftDrawerHelper;
import ru.fixapp.fooproject.presentationlayer.resolution.fragments.FragmentResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.fragments.FragmentResolverImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.navigation.NavigationResolver;
import ru.fixapp.fooproject.presentationlayer.resolution.navigation.NavigationResolverImpl;
import ru.fixapp.fooproject.presentationlayer.resolution.toolbar.ToolbarResolver;

@Module
public class CommonActivityModule {

	protected AppCompatActivity activity;
	protected BaseActivityView baseActivityView;
	protected View view;
	protected FragmentManager fm;
	protected int contentId;
	private ProvideFragmentCallback callback;

	public CommonActivityModule(AppCompatActivity activity, BaseActivityView baseActivityView,
								View view, FragmentManager fm, int contentId,
								ProvideFragmentCallback callback) {
		this.activity = activity;
		this.baseActivityView = baseActivityView;
		this.view = view;
		this.fm = fm;
		this.contentId = contentId;
		this.callback = callback;
	}

	@Provides
	@PerActivity
	public UIResolver createUiResolver() {
		return new UIResolverImpl(view.getContext(), view);
	}

	@Provides
	@PerActivity
	public ThrowableResolver createThrowableResolver(UIResolver ui) {
		return new ThrowableResolverImpl(ui);
	}

	@Provides
	@PerActivity
	public NavigationResolver createNavigationResolver(FragmentResolver fragmentResolver,
													   LeftDrawerHelper drawer,
													   ToolbarResolver toolbarResolver,
													   UIResolver ui) {
		return new NavigationResolverImpl(activity, fragmentResolver, drawer, toolbarResolver, ui,
				baseActivityView, callback);
	}


	@Provides
	@PerActivity
	public FragmentResolver createFragmentResolver() {
		return new FragmentResolverImpl(fm, contentId);
	}

	@Provides
	@PerActivity
	public BaseActivityView getBaseActivityView() {
		return baseActivityView;
	}
}
