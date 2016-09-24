package ru.fixapp.fooproject.domainlayer.providers;

import org.junit.Test;
import org.mockito.Mock;

import ru.fixapp.fooproject.testsutils.BaseTestCase;
import ru.fixapp.fooproject.datalayer.api.RestApi;
import ru.fixapp.fooproject.datalayer.providers.SessionDataProviderImpl;
import ru.fixapp.fooproject.datalayer.store.LocalCacheItemType;
import ru.fixapp.fooproject.datalayer.store.LocalStorage;
import ru.fixapp.fooproject.datalayer.store.MemoryStorage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Zahar on 07.04.16.
 */
public class SessionDataProviderImplTest extends BaseTestCase {

	private SessionDataProvider dataProvider;
	@Mock
	private RestApi api;
	@Mock
	private LocalStorage local;
	@Mock
	private MemoryStorage memory;

	@Override
	public void init() {
		dataProvider = new SessionDataProviderImpl(local, memory);
	}

	@Test
	public void testGetTokenEmpty() throws Exception {
		// Act
		String token = dataProvider.getToken();
		// Assert

		assertEquals(null, token);
	}

	@Test
	public void testGetTokenFromMemory() throws Exception {
		// Arrange
		when(local.getToken()).thenReturn("local");
		when(memory.get(LocalCacheItemType.TOKEN)).thenReturn("memory");
		// Act
		String token = dataProvider.getToken();
		// Assert

		assertEquals("memory", token);
	}

	@Test
	public void testGetTokenFromLocal() throws Exception {
		// Arrange
		when(local.getToken()).thenReturn("local");
		// Act
		String token = dataProvider.getToken();
		// Assert

		assertEquals("local", token);
	}

	@Test
	public void testGetTokenFromLocalAndSaveInMemory() throws Exception {
		// Arrange
		when(local.getToken()).thenReturn("local");
		// Act
		dataProvider.getToken();
		// Assert

		verify(memory).save(LocalCacheItemType.TOKEN, "local");
	}

	@Test
	public void testSaveToken() throws Exception {
		// Act
		dataProvider.saveToken("token");

		// Assert
		verify(memory).save(LocalCacheItemType.TOKEN, "token");
		verify(local).saveToken("token");
	}
}