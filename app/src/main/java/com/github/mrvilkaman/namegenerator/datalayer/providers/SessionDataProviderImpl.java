package com.github.mrvilkaman.namegenerator.datalayer.providers;

import com.github.mrvilkaman.namegenerator.datalayer.store.LocalCacheItemType;
import com.github.mrvilkaman.namegenerator.datalayer.store.LocalStorage;
import com.github.mrvilkaman.namegenerator.datalayer.store.MemoryStorage;
import com.github.mrvilkaman.namegenerator.domainlayer.providers.SessionDataProvider;

/**
 * Created by Zahar on 16.03.16.
 */
public class SessionDataProviderImpl implements SessionDataProvider {
	private final LocalStorage localStorage;
	private final MemoryStorage memoryStorage;

	public SessionDataProviderImpl(LocalStorage localStorage,MemoryStorage memoryStorage) {
		this.localStorage = localStorage;
		this.memoryStorage = memoryStorage;
	}

	@Override
	public String getToken() {
		String token = memoryStorage.get(LocalCacheItemType.TOKEN);
		if (token != null) return token;
		token = localStorage.getToken();
		memoryStorage.save(LocalCacheItemType.TOKEN, token);
		return token;
	}

	@Override
	public void saveToken(String token) {
		localStorage.saveToken(token);
		memoryStorage.save(LocalCacheItemType.TOKEN,token);
	}
}
