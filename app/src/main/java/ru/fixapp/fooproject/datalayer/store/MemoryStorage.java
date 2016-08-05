package ru.fixapp.fooproject.datalayer.store;

/**
 * Created by Zahar on 18.03.16.
 */
public interface MemoryStorage {

	<T> void save(LocalCacheItemType type, T object);

	<T> T get(LocalCacheItemType type);

	boolean has(LocalCacheItemType type);
}
