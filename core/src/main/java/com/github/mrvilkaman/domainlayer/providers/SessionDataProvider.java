package com.github.mrvilkaman.domainlayer.providers;

/**
 * Created by Zahar on 16.03.16.
 */
public interface SessionDataProvider {

	String getToken();

	void saveToken(String token);
}
