package com.github.mrvilkaman.domainlayer.notification;


import android.content.Context;
import android.support.annotation.RequiresPermission;

import com.github.mrvilkaman.domainlayer.providers.BaseInteractor;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import javax.inject.Inject;

import rx.Observable;

public class GcmPushNotificationImpl extends BaseInteractor implements GcmPushNotification {


	private final GoogleCloudMessaging gcm;

	@Inject
	public GcmPushNotificationImpl(Context context) {
		gcm = GoogleCloudMessaging.getInstance(context);
	}

	@Override
	@RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
	public Observable<String> register(String senderId) {
		return Observable.<String>unsafeCreate(subscriber -> {
			try {
				String register = gcm.register(senderId);
				subscriber.onNext(register);
				subscriber.onCompleted();
			} catch (IOException e) {
				subscriber.onError(e);
			}
		}).subscribeOn(schedulers.io());
	}

	@Override
	@RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
	public Observable<Void> logout() {
		return Observable.<Void>unsafeCreate(subscriber -> {
			try {
				gcm.unregister();
				subscriber.onCompleted();
			} catch (IOException e) {
				subscriber.onError(e);
			}
		}).subscribeOn(schedulers.io());
	}
}
