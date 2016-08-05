package ru.fixapp.fooproject.presentationlayer.fragments.listsample;


import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseView;

import java.util.List;

public interface ListSampleView extends BaseView {

	void bingItems(List<String> strings);
}