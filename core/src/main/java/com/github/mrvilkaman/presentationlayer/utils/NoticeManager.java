package com.github.mrvilkaman.presentationlayer.utils;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;

import com.github.mrvilkaman.core.R;

import java.util.concurrent.atomic.AtomicInteger;

public class NoticeManager {

	private final Context context;
	private final NotificationManager manager;
	private final AtomicInteger lastId = new AtomicInteger();

	public NoticeManager(Context context, NotificationManager systemService) {
		this.context = context;
		manager = systemService;
	}

	public void cancel(int id) {
		manager.cancel(id);
	}

	public void cancelAll() {
		manager.cancelAll();
	}

	protected Notification showNotification(NotificationCompat.Builder nb) {
		Notification notification;
		try {
			if (Build.VERSION_CODES.JELLY_BEAN <= Build.VERSION.SDK_INT) {
				notification = nb.build();
			} else {
				//noinspection deprecation
				notification = nb.getNotification();
			}
		} catch (Exception e) {
			notification = null;
		}
		return notification;
	}


	int getAndIncrement() {
		return lastId.getAndIncrement();
	}

	public Builder createNew() {
		return new Builder(context, manager, this);
	}

	public static class Builder {

		private final Context context;
		private final NotificationManager manager;
		private final NoticeManager noticeManager;


		private String title;
		private String ticker;
		private int smallIcon;
		private boolean noNeedVibro;
		private String content;
		private Intent intent;


		public Builder(Context context, NotificationManager manager, NoticeManager noticeManager) {
			this.context = context;
			this.manager = manager;
			this.noticeManager = noticeManager;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setSmallIcon(int smallIcon) {
			this.smallIcon = smallIcon;
			return this;
		}

		public Builder setContent(String content) {
			this.content = content;
			return this;
		}

		public Builder setTicker(String ticker) {
			this.ticker = ticker;
			return this;
		}

		public Builder withoutVibro() {
			this.noNeedVibro = true;
			return this;
		}

		public Notification build() {
			NotificationCompat.Builder nb = new NotificationCompat.Builder(context);
			if (smallIcon != 0) {
				nb.setSmallIcon(smallIcon);
			}
			nb.setAutoCancel(true);
			//			if (DevUtils.isSamsung()) {
			if (smallIcon != -1)
				nb.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), smallIcon));
			//			}

			if (ticker != null) {
				nb.setTicker(ticker);
			}

			long[] pattern = {300L, 300L};
			if (!noNeedVibro) {
				nb.setVibrate(pattern);
			}

			if (intent != null) {
				nb.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
			}

			if (title == null) {
				title = context.getString(R.string.app_name);
			}

			nb.setContentTitle(title);

			if (content != null) {
				nb.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
				nb.setContentText(content);
				if (ticker == null) {
					nb.setTicker(ticker);
				}
			}

			nb.setWhen(System.currentTimeMillis());
			return noticeManager.showNotification(nb);
		}

		public int buildAndShow() {
			int andIncrement = noticeManager.getAndIncrement();
			return buildAndShow(andIncrement);
		}

		public int buildAndShow(int id) {
			Notification notification = build();
			if (notification != null) {
				manager.notify(id, notification);
			}
			return id;
		}

		public Builder setContent(@StringRes int resId) {
			content = context.getString(resId);
			return this;
		}

		public Builder setIntent(Intent intent) {
			this.intent = intent;
			return this;
		}
	}

}
