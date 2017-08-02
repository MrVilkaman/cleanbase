package com.github.mrvilkaman.utils.bus;



import io.reactivex.Observable;
import io.reactivex.subjects.Subject;

/**
 * Event notification system which enforces use of dedicated queues to perform type safe pub/sub.
 */
@SuppressWarnings("WeakerAccess")
public interface Bus {

	/**
	 * Publishes event onto the given queue.
	 */
	<T> void publish(Queue<T> queue, T event);

	/**
	 * Converts the given {@link Queue} to a {@link Subject} to be used
	 * outside the bus context (when combining Rx streams).
	 */
	<T> Observable<T> queue(Queue<T> queue);
}
