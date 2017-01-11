package com.github.mrvilkaman.presentationlayer.fragments.simplelist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseVH;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;

import java.util.List;

import butterknife.BindView;

public class SimpleModelVH extends BaseVH<SimpleModel> {

	@BindView(R.id.text1) TextView text1;
	@BindView(R.id.text2) TextView text2;
	@BindView(R.id.image) ImageView imageView;

	private final ImageLoader loader;

	public SimpleModelVH(View view, ImageLoader loader) {
		super(view);
		this.loader = loader;
	}

	@Override
	public void bind(SimpleModel item, int position, List<Object> payloads) {
		text1.setText(Integer.toString(item.getNumber()));
		text2.setText(item.getValue());
		loader.load(item.getImage()).height(128).into(imageView);
	}
}
