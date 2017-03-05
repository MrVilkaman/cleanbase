package com.github.mrvilkaman.presentationlayer.utils;


import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class RxUtils {

	public static <T1, T2> Observable.Transformer<List<T1>, List<T2>> mapList(Func1<T1, T2> func) {
		return t1Observable -> t1Observable.concatMap(
				t1s -> Observable.from(t1s).map(func).toList());
	}
	//
}
