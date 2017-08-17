package com.github.mrvilkaman.presentationlayer.utils;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Function;


public final class RxUtils {

	private RxUtils() {
		// пустой
	}

	public static <T1, T2> ObservableTransformer<List<T1>, List<T2>> mapList(final Function<T1, T2> func) {
		return new ObservableTransformer<List<T1>, List<T2>>() {
			@Override
			public ObservableSource<List<T2>> apply(Observable<List<T1>> t1Observable) {
				return t1Observable.concatMap(
						new Function<List<T1>, ObservableSource<? extends List<T2>>>() {
							@Override
							public ObservableSource<? extends List<T2>> apply(List<T1> t1s) throws Exception {
								return Observable.fromIterable(t1s)
										.map(func)
										.toList()
										.toObservable();
							}
						});
			}
		};
	}


	public static Observable<Integer> getScrollObservable(final RecyclerView recyclerView, final
	int limit, final int emptyListCount) {
		Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(final ObservableEmitter<Integer> subscriber) throws Exception {
				final RecyclerView.OnScrollListener sl = new RecyclerView.OnScrollListener() {
					@Override
					public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
						if (!subscriber.isDisposed()) {
							int position = getLastVisibleItemPosition(recyclerView);
							int updatePosition = recyclerView.getAdapter()
									.getItemCount() - 1 - (limit / 2);
							if (position >= updatePosition) {
								subscriber.onNext(recyclerView.getAdapter()
										.getItemCount());
							}
						}
					}
				};
				recyclerView.addOnScrollListener(sl);
				subscriber.setCancellable(new Cancellable() {
					@Override
					public void cancel() throws Exception {
						recyclerView.removeOnScrollListener(sl);
					}
				});
				if (recyclerView.getAdapter()
						.getItemCount() == emptyListCount) {
					subscriber.onNext(recyclerView.getAdapter()
							.getItemCount());
				}
			}
		});
		return integerObservable.distinctUntilChanged();
	}

	private static int getLastVisibleItemPosition(RecyclerView recyclerView) {
		RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
		Class recyclerViewLMClass = layoutManager.getClass();
		if (recyclerViewLMClass == LinearLayoutManager.class || LinearLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
			LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
			return linearLayoutManager.findLastVisibleItemPosition();
		} else if (recyclerViewLMClass == StaggeredGridLayoutManager.class || StaggeredGridLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
			StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
			int[] into = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
			List<Integer> intoList = new ArrayList<>();
			for (int i : into) {
				intoList.add(i);
			}
			return Collections.max(intoList);
		}
		throw Exceptions.propagate(new Throwable("Unknown LayoutManager class: " + recyclerViewLMClass.toString()));
	}
	//
}
