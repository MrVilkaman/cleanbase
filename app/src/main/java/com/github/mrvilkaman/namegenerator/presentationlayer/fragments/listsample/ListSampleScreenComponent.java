package com.github.mrvilkaman.namegenerator.presentationlayer.fragments.listsample;


import com.github.mrvilkaman.namegenerator.presentationlayer.app.AppComponent;
import com.github.mrvilkaman.namegenerator.presentationlayer.app.PerActivity;

import dagger.Component;

/**
 * Created by Zahar on 24.03.16.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ListSampleScreenModule.class})
public interface ListSampleScreenComponent {
	void inject(ListSampleScreenFragment fragment);
}