package ru.fixapp.fooproject.datalayer.store;

import android.app.Application;
import android.content.Context;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import ru.fixapp.fooproject.BuildConfig;
import ru.fixapp.fooproject.TestApplication;


/**
 * Created by Zahar on 18.04.16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = TestApplication.class,
		manifest = "src/main/AndroidManifest.xml")
public class LocalStorageImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	private LocalStorageImpl localStorage;

	@Before
	public void init() {
		Application application = RuntimeEnvironment.application;
		localStorage = new LocalStorageImpl(application);
	}

	@After
	public void clear() {
		RuntimeEnvironment.application
				.getSharedPreferences("pref", Context.MODE_PRIVATE)
				.edit()
				.clear()
				.commit();
	}

	@Test
	public void testGetTokenNull() throws Exception {
		// Act
		String token = localStorage.getToken();

		// Assert
		Assert.assertNull(token);
	}

	@Test
	public void testGetTokenFull() throws Exception {
		// Arrange
		RuntimeEnvironment.application
				.getSharedPreferences("pref", Context.MODE_PRIVATE)
				.edit()
				.putString("token", "token")
				.clear()
				.commit();

		// Act
		String token = localStorage.getToken();

		// Assert
		Assert.assertEquals("token", token);
	}

	@Test
	public void testSaveToken() throws Exception {
		// Act
		localStorage.saveToken("qwer");

		// Assert
		String token = localStorage.getToken();
		Assert.assertEquals("qwer", token);

	}

}