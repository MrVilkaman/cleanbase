package com.github.mrvilkaman.presentationlayer.app;


public class CleanBaseSettings {

	private static Builder builder;

	public static boolean needSubscribeLogs() {
		return builder.subscribeLogs;
	}

	static Builder changeSettings() {
		return new Builder();
	}

	static void save(Builder builder) {
		builder.save();
	}

	public static class Builder {

		private boolean subscribeLogs;

		public Builder() {
		}

		public Builder setSubscribeLogs(boolean logs) {
			this.subscribeLogs = logs;
			return this;
		}

		private void save() {
			builder = this;
		}
	}
}
