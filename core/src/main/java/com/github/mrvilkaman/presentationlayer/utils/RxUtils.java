package com.github.mrvilkaman.presentationlayer.utils;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

public final class RxUtils {

	private RxUtils() {
		// пустой
	}

	public static <T1, T2> Observable.Transformer<List<T1>, List<T2>> mapList(Func1<T1, T2> func) {
		return t1Observable -> t1Observable.concatMap(
				t1s -> Observable.from(t1s).map(func).toList());
	}


	public static Observable<Integer> getScrollObservable(RecyclerView recyclerView, int limit, int emptyListCount) {
		Observable<Integer> integerObservable = Observable.unsafeCreate(subscriber -> {
			final RecyclerView.OnScrollListener sl = new RecyclerView.OnScrollListener() {
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					if (!subscriber.isUnsubscribed()) {
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
			subscriber.add(Subscriptions.create(() -> recyclerView.removeOnScrollListener(sl)));
			if (recyclerView.getAdapter()
					.getItemCount() == emptyListCount) {
				subscriber.onNext(recyclerView.getAdapter()
						.getItemCount());
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
