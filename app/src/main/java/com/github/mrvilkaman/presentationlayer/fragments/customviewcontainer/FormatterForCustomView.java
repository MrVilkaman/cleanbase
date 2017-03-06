package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;


import android.util.Log;

import com.github.mrvilkaman.di.PerScreen;

import java.util.UUID;

import javax.inject.Inject;

@PerScreen
public class FormatterForCustomView {

	@Inject
	public FormatterForCustomView() {
		Log.d("QWER","FormatterForCustomView");
	}

	public String getString(){
		return UUID.randomUUID().toString();
	}
}
