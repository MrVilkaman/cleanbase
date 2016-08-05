package ru.fixapp.fooproject.presentationlayer.fragments.listsample;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;


@Module
public class ListSampleScreenModule {

	@Provides
	@NonNull
	ListSamplePresenter provideListSamplePresenter() {
		return new ListSamplePresenter();
	}
}