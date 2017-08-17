package com.github.mrvilkaman.domainlayer.notification;


import android.content.Context;
import android.support.annotation.RequiresPermission;

import com.github.mrvilkaman.domainlayer.providers.BaseInteractor;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class GcmPushNotificationImpl extends BaseInteractor implements GcmPushNotification {


	private final GoogleCloudMessaging gcm;

	@Inject
	public GcmPushNotificationImpl(Context context) {
		gcm = GoogleCloudMessaging.getInstance(context);
	}

	@Override
	@RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
	public Single<String> register(final String senderId) {
		return Single.<String>create(new SingleOnSubscribe<String>() {
			@Override
			@RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
			public void subscribe(@NonNull SingleEmitter<String> subscriber) throws Exception {
				try {
					String register = gcm.register(senderId);
					subscriber.onSuccess(register);
				} catch (IOException e) {
					subscriber.onError(e);
				}
			}
		}).subscribeOn(schedulers.io());
	}

	@Override
	@RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
	public Completable logout() {
		CompletableOnSubscribe completableOnSubscribe = new CompletableOnSubscribe() {
			@Override
			@RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
			public void subscribe(@NonNull CompletableEmitter subscriber) throws Exception {
				try {
					gcm.unregister();
					subscriber.onComplete();
				} catch (IOException e) {
					subscriber.onError(e);
				}
			}
		};
		return Completable.create(completableOnSubscribe)
				.subscribeOn(schedulers.io());
	}
}
