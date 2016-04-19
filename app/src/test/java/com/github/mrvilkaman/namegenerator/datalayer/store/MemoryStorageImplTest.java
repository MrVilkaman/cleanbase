package com.github.mrvilkaman.namegenerator.datalayer.store;

import com.github.mrvilkaman.namegenerator.BaseTestCase;

import org.junit.Test;

import static org.junit.Assert.*;

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