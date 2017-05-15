package com.github.mrvilkaman.presentationlayer.fragments.customviewcontainer;


import android.util.Log;

import java.util.UUID;

import javax.inject.Inject;

public class FormatterForCustomView {

	@Inject
	public FormatterForCustomView() {
		Log.d("QWER","FormatterForCustomView");
	}

	public String getString(){
		return UUID.randomUUID().toString();
	}
}
