package com.github.mrvilkaman.presentationlayer.fragments.simplelist;

import com.github.mrvilkaman.di.ActivityComponent;
import com.github.mrvilkaman.di.PerScreen;

import dagger.Component;

@PerScreen
@Component(dependencies = ActivityComponent.class)
public interface SimpleListScreenComponent {

	void inject(SimpleListScreenFragment fragment);

}