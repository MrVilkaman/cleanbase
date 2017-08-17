package com.github.mrvilkaman.presentationlayer.app;


public class CleanBaseSettings {

	private static Builder builder = new Builder();

	public static Builder changeSettings() {
		return new Builder();
	}

	public static boolean needSubscribeLogs() {
		return builder.subscribeLogs;
	}

	public static void save(Builder builder) {
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

		public Builder setRxBusLogs(boolean rxBusLogs) {
			this.rxBusLogs = rxBusLogs;
			return this;
		}

		public Builder setSubscribeLogs(boolean logs) {
			this.subscribeLogs = logs;
			return this;
		}

		public Builder setImageLoadingLogs(boolean imageLoadingLogs) {
			this.imageLoadingLogs = imageLoadingLogs;
			return this;
		}

		public Builder setHttpLogging(boolean httpLogging) {
			this.httpLogging = httpLogging;
			return this;
		}

		private void save() {
			builder = this;
		}
	}
}
