package com.github.mrvilkaman.datalayer.providers;


import net.jokubasdargis.rxbus.Queue;

public class GlobalBusQuery {
	public static final Queue<Throwable> GLOBAL_ERRORS = Queue.of(Throwable.class).build();
}
