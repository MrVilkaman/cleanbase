package com.github.mrvilkaman.namegenerator.datalayer.store;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Type;


/**
 * Created by Zahar on 18.03.16.
 */
public class MemoryStorageImpl implements MemoryStorage {

	private Map<LocalCacheItemType,Object> friends = new HashMap<>();

	@Override
	public <T> void save(LocalCacheItemType type, T object) {
		friends.put(type,object);
	}

	@Override
	public <T> T get(LocalCacheItemType type) {
		return (T)friends.get(type);
	}

	@Override
	public boolean has(LocalCacheItemType type) {
		return friends.containsKey(type);
	}

}
