package com.github.mrvilkaman.domainlayer.notification;


import io.reactivex.Completable;
import io.reactivex.Single;

public interface GcmPushNotification {

	Single<String> register(String senderId);

	Completable logout();
}
