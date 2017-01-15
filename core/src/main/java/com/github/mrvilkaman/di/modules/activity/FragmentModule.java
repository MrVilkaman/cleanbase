package com.github.mrvilkaman.di.modules.activity;


import android.support.v4.app.FragmentManager;

import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolverImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

	private FragmentManager fm;
	private int contentId;

	public FragmentModule(FragmentManager fm, int contentId) {
		this.fm = fm;
		this.contentId = contentId;
	}

	@Provides
	@PerActivity
	public FragmentResolver createFragmentResolver() {
		return new FragmentResolverImpl(fm, contentId);
	}

}
