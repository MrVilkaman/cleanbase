package com.github.mrvilkaman.datalayer.providers;


import com.github.mrvilkaman.utils.bus.Queue;

public class GlobalBusQuery {
	public static final Queue<Throwable> GLOBAL_ERRORS = Queue.of(Throwable.class).build();
	public static final Queue<String> CURRENT_SCREEN_NAME = Queue.of(String.class).replay().build();
}
