package com.github.mrvilkaman.namegenerator.datalayer.store;

import android.content.Context;

import javax.inject.Inject;

/**
 * Created by root on 12.03.16.
 */
public class LocalStorageImpl implements LocalStorage {


	private static final String PREF = "pref";
	private static final String TOKEN = "token";
	private Context context;

	@Inject
	public LocalStorageImpl(Context context) {
		this.context = context.getApplicationContext();
	}

	@Override
	public String getToken() {
		return context.getSharedPreferences(PREF, Context.MODE_PRIVATE).getString(TOKEN, null);
	}

	@Override
	public void saveToken(String token) {
		context.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit().putString(TOKEN, token)
			   .apply();
	}
}
