package ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.isseiaoki.simplecropview.CropImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import ru.fixapp.fooproject.R;
import ru.fixapp.fooproject.presentationlayer.activities.ActivityComponent;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CropImageFragment extends BaseFragment<CropImagePresenter> implements CropImageScreenView {

	public enum MODE {
		FREE,
		SQUARE,
		ODOMETR
	}

	private static final float MAX = 1200.0f;

	private static final String EXTRA_INPUT = "input";
	private static final String EXTRA_OUTPUT = "output";
	private static final String EXTRA_AS_SQUARE = "as_square";

	@BindView(R.id.cropImageView) CropImageView cropImageView;

	public static CropImageFragment newInstance(String imageFile, String resultFile, MODE mode) {
		Bundle args = new Bundle();
		args.putString(EXTRA_INPUT, imageFile);
		args.putString(EXTRA_OUTPUT, resultFile);
		args.putString(EXTRA_AS_SQUARE, mode.name());
		CropImageFragment fragment = new CropImageFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_crop_fragment;
	}

	@Override
	protected void onCreateView(View view, Bundle savedInstanceState) {

		Bundle arguments = getArguments();
		if (arguments == null) {
			return;
		}

		MODE mode = MODE.valueOf(arguments.getString(EXTRA_AS_SQUARE));

		switch (mode) {
			case FREE:
				cropImageView.setCropMode(CropImageView.CropMode.FREE);
				break;
			case SQUARE:
				cropImageView.setCropMode(CropImageView.CropMode.SQUARE);
				break;
			case ODOMETR:
				cropImageView.setCropMode(CropImageView.CropMode.SQUARE);
//				cropImageView.setCropMode(CropImageView.CropMode.CUSTOM);
//				cropImageView.setCustomRatio(3,2);
				break;
			default:
		}

		String path = getArguments().getString(EXTRA_INPUT);
		showProgress();

		Observable<BitmapFactory.Options> sizeObs = Observable.just(path)
				.map(file -> {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					BitmapFactory.decodeFile(file, options);
					return options;
				});


		Observable.zip(sizeObs, Observable.just(path), (options, file) -> {
			int max = Math.max(options.outWidth, options.outHeight);
			float scale = max / MAX;
			int newWidth = (int) (options.outWidth / scale);
			int newHeight = (int) (options.outHeight / scale);
			return decodeSampledBitmap(file, newWidth, newHeight);
		})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.doOnTerminate(this::hideProgress)
				.subscribe(bitmap -> cropImageView.setImageBitmap(bitmap));

	}


	public static Bitmap decodeSampledBitmap(String file, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(file, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	private void loadImage(File uri) {
		showProgress();
		Picasso.with(getContext())
				.load(uri)
				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
				.into(cropImageView, new Callback() {
					@Override
					public void onSuccess() {
						hideProgress();
					}

					@Override
					public void onError() {
						hideProgress();
						getNavigation().back();
					}
				});
	}

	@Override
	public void daggerInject(ActivityComponent component) {
		DaggerCropImageScreenComponent.builder()
				.activityComponent(component)
				.build()
				.inject(this);
	}

	@Override
	public boolean onBackPressed() {
		Fragment targetFragment = getTargetFragment();
		int targetRequestCode = getTargetRequestCode();
		if (targetFragment != null) {
			setTargetFragment(null, 0);
			targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null);
		}

		return super.onBackPressed();
	}

	@Override
	public Bitmap getCroppedBitmap() {
		return cropImageView.getCroppedBitmap();
	}

	@Override
	public String getOutPath() {
		return getArguments().getString(EXTRA_OUTPUT);
	}

	@Override
	public void sendResults() {
		Fragment targetFragment = getTargetFragment();
		int targetRequestCode = getTargetRequestCode();
		if (targetFragment != null) {
			setTargetFragment(null, 0);
			getNavigation().back();
			targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, null);
		}
	}

	@OnClick(R.id.ready)
	void onClick() {
		getPresenter().savePhoto();
	}

}
