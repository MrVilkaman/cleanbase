package com.github.mrvilkaman.domainlayer.notification;


import android.content.Context;
import android.support.annotation.RequiresPermission;

import com.github.mrvilkaman.domainlayer.providers.BaseInteractor;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;

public class GcmPushNotificationImpl extends BaseInteractor implements GcmPushNotification {


	private final GoogleCloudMessaging gcm;

	@Inject
	public GcmPushNotificationImpl(Context context) {
		gcm = GoogleCloudMessaging.getInstance(context);
	}

	@Override
	@RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
	public Single<String> register(String senderId) {
		return Single.<String>create(subscriber -> {
			try {
				String register = gcm.register(senderId);
				subscriber.onSuccess(register);
			} catch (IOException e) {
				subscriber.onError(e);
			}
		}).subscribeOn(schedulers.io());
	}

	@Override
	@RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
	public Completable logout() {
		CompletableOnSubscribe completableOnSubscribe = subscriber -> {
			try {
				gcm.unregister();
				subscriber.onComplete();
			} catch (IOException e) {
				subscriber.onError(e);
			}
		};
		return Completable.create(completableOnSubscribe)
				.subscribeOn(schedulers.io());
	}
}
