package com.github.mrvilkaman.presentationlayer.app;


public class CleanBaseSettings {

	private static Builder builder = new Builder();

	static Builder changeSettings() {
		return new Builder();
	}

	public static boolean needSubscribeLogs() {
		return builder.subscribeLogs;
	}

	static void save(Builder builder) {
		builder.save();
	}

	public static boolean rxBusLogs() {
		return builder.rxBusLogs;
	}

	public static boolean imageLoadingLogs() {
		return builder.imageLoadingLogs;
	}

	public static boolean httpLogging() {
		return builder.httpLogging;
	}

	public static class Builder {

		private boolean subscribeLogs;
		private boolean rxBusLogs;
		private boolean imageLoadingLogs;
		private boolean httpLogging;

		private Builder() {
		}

		Builder setRxBusLogs(boolean rxBusLogs) {
			this.rxBusLogs = rxBusLogs;
			return this;
		}

		Builder setSubscribeLogs(boolean logs) {
			this.subscribeLogs = logs;
			return this;
		}

		Builder setImageLoadingLogs(boolean imageLoadingLogs) {
			this.imageLoadingLogs = imageLoadingLogs;
			return this;
		}

		Builder setHttpLogging(boolean httpLogging) {
			this.httpLogging = httpLogging;
			return this;
		}

		private void save() {
			builder = this;
		}
	}
}
