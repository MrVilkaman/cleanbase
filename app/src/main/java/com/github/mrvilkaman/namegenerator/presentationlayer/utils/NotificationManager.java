package com.github.mrvilkaman.namegenerator.presentationlayer.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.github.mrvilkaman.namegenerator.BuildConfig;
import com.github.mrvilkaman.namegenerator.R;
import com.github.mrvilkaman.namegenerator.presentationlayer.activities.MainActivity;

public class NotificationManager {

	public static final long PUSH_VIBRO_TIMES_ON = 300L;
	public static final long PUSH_VIBRO_TIMES_OFF = 300L;

	private static NotificationManager instance;
	protected final Context context;

	public NotificationManager(final Context context) {
		this.context = context;
	}

	public static NotificationManager getInstance(Context context) {
		if (instance == null) {
			instance = new NotificationManager(context);
		}
		return instance;
	}

	public void create(String message) {
		create(message, 0);
	}

	@SuppressWarnings("SameParameterValue")
	public void create(String message, long id) {
		String title = context.getString(R.string.app_name);
		long[] pattern = {PUSH_VIBRO_TIMES_ON, PUSH_VIBRO_TIMES_OFF};
		NotificationCompat.Builder nb = new NotificationCompat.Builder(context);

		int iconId = getIconId();
		if (iconId != -1)
			nb.setSmallIcon(iconId);
		nb.setAutoCancel(true) //уведомление закроется по клику на него
		  .setTicker(message) //текст, который отобразится вверху статус-бара при создании
		  // уведомления
		  .setWhen(System.currentTimeMillis()) //отображаемое время уведомленият
		  .setVibrate(pattern).setContentTitle(title) //заголовок уведомления
		  .setContentText(message); //заголовок уведомления

		if (AppUtils.isSamsung()) {
			if (iconId != -1)
				nb.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), iconId));
		}

		if (id != 0) {
			Intent intent = getIntent();
			nb.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
		}

		showNotification(nb, (int) id);
	}

	private int getIconId() {
		return -1;//R.drawable.ic_launcher;
	}

	protected Intent getIntent() {
		return new Intent(context, MainActivity.class);
	}

	private void showNotification(NotificationCompat.Builder nb, int id) {
		Notification notification = showNotification(nb);
		if (notification != null) {
			final android.app.NotificationManager manager = (android.app.NotificationManager)
					context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(id, notification); // отображаем его пользователю.
		}
	}

	protected Notification showNotification(NotificationCompat.Builder nb) {
		Notification notification;
		try {
			if (Build.VERSION_CODES.JELLY_BEAN <= Build.VERSION.SDK_INT) {
				notification = nb.build();
			} else {
				notification = nb.getNotification();
			}
		} catch (Exception e) {
			notification = null;
			if (BuildConfig.DEBUG) {
				Toast.makeText(context, "NotificationManager error", Toast.LENGTH_LONG).show();
			}
		}
		return notification;
	}


	public void cancelNotification() {
		android.app.NotificationManager nMgr = (android.app.NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nMgr.cancelAll();
	}
}
