package com.github.mrvilkaman.presentationlayer.app;

import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.junit.Assert;
import org.junit.Test;


public class CleanBaseSettingsTest extends BaseTestCase {


	@Override
	public void init() {
		CleanBaseSettings.save(CleanBaseSettings.changeSettings());
	}

	@Test
	public void testDefaultState() {
		Assert.assertFalse(CleanBaseSettings.httpLogging());
		Assert.assertFalse(CleanBaseSettings.imageLoadingLogs());
		Assert.assertFalse(CleanBaseSettings.rxBusLogs());
		Assert.assertFalse(CleanBaseSettings.needSubscribeLogs());
	}

	@Test
	public void testChange_httpLogin() {
		CleanBaseSettings.Builder builder = CleanBaseSettings.changeSettings()
				.setHttpLogging(true);
		CleanBaseSettings.save(builder);

		Assert.assertTrue(CleanBaseSettings.httpLogging());
		Assert.assertFalse(CleanBaseSettings.imageLoadingLogs());
		Assert.assertFalse(CleanBaseSettings.rxBusLogs());
		Assert.assertFalse(CleanBaseSettings.needSubscribeLogs());
	}

	@Test
	public void testChange_imageLoadingLogs() {
		CleanBaseSettings.Builder builder = CleanBaseSettings.changeSettings()
				.setImageLoadingLogs(true);
		CleanBaseSettings.save(builder);

		Assert.assertFalse(CleanBaseSettings.httpLogging());
		Assert.assertTrue(CleanBaseSettings.imageLoadingLogs());
		Assert.assertFalse(CleanBaseSettings.rxBusLogs());
		Assert.assertFalse(CleanBaseSettings.needSubscribeLogs());
	}

	@Test
	public void testChange_rxBusLogs() {
		CleanBaseSettings.Builder builder = CleanBaseSettings.changeSettings()
				.setRxBusLogs(true);
		CleanBaseSettings.save(builder);

		Assert.assertFalse(CleanBaseSettings.httpLogging());
		Assert.assertFalse(CleanBaseSettings.imageLoadingLogs());
		Assert.assertTrue(CleanBaseSettings.rxBusLogs());
		Assert.assertFalse(CleanBaseSettings.needSubscribeLogs());
	}

	@Test
	public void testChange_needSubscribeLogs() {
		CleanBaseSettings.Builder builder = CleanBaseSettings.changeSettings()
				.setSubscribeLogs(true);
		CleanBaseSettings.save(builder);

		Assert.assertFalse(CleanBaseSettings.httpLogging());
		Assert.assertFalse(CleanBaseSettings.imageLoadingLogs());
		Assert.assertFalse(CleanBaseSettings.rxBusLogs());
		Assert.assertTrue(CleanBaseSettings.needSubscribeLogs());
	}

}