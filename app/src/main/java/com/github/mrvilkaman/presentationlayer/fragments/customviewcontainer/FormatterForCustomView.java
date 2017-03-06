package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;


import com.github.mrvilkaman.di.PerScreen;

import java.util.UUID;

import javax.inject.Inject;

@PerScreen
public class FormatterForCustomView {

	@Inject
	public FormatterForCustomView() {
	}

	public String getString(){
		return UUID.randomUUID().toString();
	}
}
