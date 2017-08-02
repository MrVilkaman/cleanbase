package com.github.mrvilkaman.di.modules.activity;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.github.mrvilkaman.di.PerActivity;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.AndroidFragmentResolver;
import com.github.mrvilkaman.presentationlayer.resolution.fragments.FragmentResolver;
import com.github.mrvilkaman.utils.bus.Bus;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

	private final FragmentManager fm;
	private final int contentId;
	private final @Nullable StaticFragments callback;

	public FragmentModule(@NonNull FragmentManager fm, int contentId) {
		this(fm, contentId, null);
	}

	public FragmentModule(@NonNull FragmentManager fm, int contentId,
						  @Nullable StaticFragments callback) {
		this.fm = fm;
		this.contentId = contentId;
		this.callback = callback;
	}

	@Provides
	@PerActivity
	public FragmentResolver createFragmentResolver(Bus bus) {
		AndroidFragmentResolver androidFragmentResolver =
				new AndroidFragmentResolver(fm, contentId,bus);
		if (callback != null) {
			callback.addFragment(androidFragmentResolver);
		}
		return androidFragmentResolver;
	}

	public interface StaticFragments {
		void addFragment(FragmentResolver resolver);
	}

}
