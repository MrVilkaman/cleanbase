package ru.fixapp.fooproject.datalayer.providers;

import ru.fixapp.fooproject.datalayer.store.LocalCacheItemType;
import ru.fixapp.fooproject.datalayer.store.LocalStorage;
import ru.fixapp.fooproject.datalayer.store.MemoryStorage;
import ru.fixapp.fooproject.domainlayer.providers.SessionDataProvider;

/**
 * Created by Zahar on 16.03.16.
 */
public class SessionDataProviderImpl implements SessionDataProvider {
	private final LocalStorage localStorage;
	private final MemoryStorage memoryStorage;

	public SessionDataProviderImpl(LocalStorage localStorage, MemoryStorage memoryStorage) {
		this.localStorage = localStorage;
		this.memoryStorage = memoryStorage;
	}

	@Override
	public String getToken() {
		String token = memoryStorage.get(LocalCacheItemType.TOKEN);
		if (token != null)
			return token;
		token = localStorage.getToken();
		memoryStorage.save(LocalCacheItemType.TOKEN, token);
		return token;
	}

	@Override
	public void saveToken(String token) {
		localStorage.saveToken(token);
		memoryStorage.save(LocalCacheItemType.TOKEN, token);
	}
}
