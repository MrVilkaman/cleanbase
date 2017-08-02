package com.github.mrvilkaman.utils.bus;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A communication channel for specific event types.
 *
 * @param <T> Type of event this queue supports.
 */
@SuppressWarnings("WeakerAccess")
public final class Queue<T> {

	private static final AtomicInteger RUNNING_ID = new AtomicInteger();
	private final Class<T> eventType;
	private final String name;
	private final int id;
	private final T defaultEvent;
	private final boolean replayLast;

	Queue(String s, Class<T> eventTypeClass, boolean flag, T event) {
		name = s;
		eventType = eventTypeClass;
		replayLast = flag;
		defaultEvent = event;
		id = RUNNING_ID.incrementAndGet();
	}

	/**
	 * Creates a builder of a {@link Queue} for the given event type.
	 */
	public static <T> Builder<T> of(Class<T> eventTypeClass) {
		return new Builder<>(eventTypeClass);
	}

	public Class<T> getEventType() {
		return eventType;
	}

	public String getName() {
		return name;
	}

	int getId() {
		return id;
	}

	T getDefaultEvent() {
		return defaultEvent;
	}

	boolean isReplayLast() {
		return replayLast;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof Queue) && ((Queue) obj).id == id;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return name + "[" + eventType.getCanonicalName() + "]";
	}

	/**
	 * A builder to build customized {@link Queue}s.
	 */
	public static final class Builder<T> {

		private final Class<T> eventType;
		private T defaultEvent;
		private String name;
		private boolean replayLast;

		Builder(Class<T> eventTypeClass) {
			eventType = eventTypeClass;
		}

		public Queue<T> build() {
			if (name == null) {
				name = eventType.getSimpleName() + "Queue";
			}

			return new Queue<>(name, eventType, replayLast, defaultEvent);
		}

		public Builder<T> name(String s) {
			name = s;
			return this;
		}

		public Builder<T> replay() {
			replayLast = true;
			return this;
		}

		public Builder replay(T obj) {
			replayLast = true;
			defaultEvent = obj;
			return this;
		}
	}
}