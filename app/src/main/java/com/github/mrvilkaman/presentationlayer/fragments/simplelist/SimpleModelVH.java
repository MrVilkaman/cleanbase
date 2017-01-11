package com.github.mrvilkaman.presentationlayer.fragments.simplelist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mrvilkaman.R;
import com.github.mrvilkaman.presentationlayer.fragments.core.BaseVH;
import com.github.mrvilkaman.presentationlayer.resolution.ImageLoader;

import java.util.Set;

import butterknife.BindView;

public class SimpleModelVH extends BaseVH<SimpleModel> {

	private final ImageLoader loader;
	@BindView(R.id.text1) TextView text1;
	@BindView(R.id.text2) TextView text2;
	@BindView(R.id.image) ImageView imageView;

	public SimpleModelVH(View view, ImageLoader loader) {
		super(view);
		this.loader = loader;
	}

	@Override
	public void bind(SimpleModel item, int position, Set<String> payloads) {
		if (payloads.isEmpty()) {
			text1.setText(Integer.toString(item.getNumber()));
			text2.setText(item.getValue());
			loader.load(item.getImage())
					.height(128)
					.into(imageView);
		} else {
			for (String key : payloads) {
				if (key.equals("value")) {
					text2.setText(item.getValue());
				} else if (key.equals("number")) {
					text1.setText(Integer.toString(item.getNumber()));
				} else if (key.equals("image")) {
					loader.load(item.getImage())
							.height(128)
							.into(imageView);
				}
			}
		}


	}
}
