package com.github.mrvilkaman.presentationlayer.fragments.core;


import android.support.v7.util.DiffUtil;

import java.util.List;

public abstract class IDiffCallback<Type> extends DiffUtil.Callback {

	public abstract void update(List<Type> oldItems, List<Type> newItems);
}
