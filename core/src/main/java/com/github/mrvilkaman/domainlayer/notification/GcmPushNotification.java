package com.github.mrvilkaman.domainlayer.notification;


import rx.Observable;

public interface GcmPushNotification {

	Observable<String> register(String senderId);

	Observable<Void> logout();
}
