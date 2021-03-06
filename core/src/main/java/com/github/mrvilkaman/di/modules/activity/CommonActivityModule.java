package com.github.mrvilkaman.di.modules.activity;


import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mrvilkaman.datalayer.providers.PermissionManagerImpl;
import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.domainlayer.providers.PermissionManager;
import com.github.mrvilkaman.presentationlayer.activities.BaseActivityView;
import com.github.mrvilkaman.presentationlayer.resolution.ProvideFragmentCallback;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolver;
import com.github.mrvilkaman.presentationlayer.resolution.UIResolverImpl;
import com.github.mrvilkaman.presentationlayer.resolution.drawer.LeftDrawerHelper;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolver;
import com.github.mrvilkaman.presentationlayer.resolution.navigation.NavigationResolverImpl;
import com.github.mrvilkaman.presentationlayer.resolution.toolbar.ToolbarResolver;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonActivityModule {

	protected AppCompatActivity activity;
	protected BaseActivityView baseActivityView;
	protected View view;
	private ProvideFragmentCallback callback;

	public CommonActivityModule(AppCompatActivity activity, BaseActivityView baseActivityView,
								View view, ProvideFragmentCallback callback) {
		this.activity = activity;
		this.baseActivityView = baseActivityView;
		this.view = view;
		this.callback = callback;
	}

	@Provides
	@PerActivity
	public UIResolver createUiResolver() {
		return new UIResolverImpl(view.getContext(), view);
	}

	@Provides
	@PerActivity
	public NavigationResolver createNavigationResolver(FragmentResolver fragmentResolver,
													   @Nullable LeftDrawerHelper drawer,
													   @Nullable ToolbarResolver toolbarResolver,
													   UIResolver ui) {
		return new NavigationResolverImpl(activity, fragmentResolver, drawer, toolbarResolver, ui,
				baseActivityView, callback);
	}

	@Provides
	@PerActivity
	public BaseActivityView getBaseActivityView() {
		return baseActivityView;
	}

	@Provides
	@PerActivity
	public PermissionManager getPermissionManager() {
		return new PermissionManagerImpl(activity);
	}

}
