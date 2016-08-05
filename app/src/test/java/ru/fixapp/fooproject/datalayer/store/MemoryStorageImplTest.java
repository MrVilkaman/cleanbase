package ru.fixapp.fooproject.datalayer.store;

import org.junit.Test;

import ru.fixapp.fooproject.BaseTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zahar on 19.04.16.
 */
public class MemoryStorageImplTest extends BaseTestCase{

	private MemoryStorageImpl memoryStorage;

	@Override
	public void init() {
		memoryStorage = new MemoryStorageImpl();
	}

	@Test
	public void testSave() throws Exception {
		boolean has = memoryStorage.has(LocalCacheItemType.TOKEN);
		assertFalse(has);

		String token = memoryStorage.get(LocalCacheItemType.TOKEN);
		assertNull(token);

		memoryStorage.save(LocalCacheItemType.TOKEN, "token");
		token = memoryStorage.get(LocalCacheItemType.TOKEN);
		assertEquals("token", token);

		has = memoryStorage.has(LocalCacheItemType.TOKEN);
		assertTrue(has);
	}

	@Test
	public void test2() {
		memoryStorage.save(LocalCacheItemType.TOKEN, "token");
		assertTrue(memoryStorage.has(LocalCacheItemType.TOKEN));
		assertFalse(memoryStorage.has(LocalCacheItemType.USER));
	}
}