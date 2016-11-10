package ru.fixapp.fooproject.presentationlayer.photoulits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop.CropImageFragment;
import ru.fixapp.fooproject.presentationlayer.resolution.navigation.NavigationResolver;
import ru.fixapp.fooproject.presentationlayer.utils.StorageUtils;

public class PhotoHelperImpl implements PhotoHelper {


	public static final int SELECT_PICTURE_REQUEST_CODE = 9000;
	public static final int TAKE_PHOTO_REQUEST_CODE = 9001;
	public static final int CROP_PHOTO_REQUEST_CODE = 9002;
	public static final int CROP_ERROR = 404;


	public static final String IMAGE_TEMP_FILE_NAME = "cameraTemp.jpg";
	public static final String IMAGE_TEMP = "photos";

	private static final String TAG = "PhotoUtils";
	private static final String TEMP_NAME = "qwer.jpg";

	private static String lastFileName = "";

	private static CropImageFragment.MODE mode = CropImageFragment.MODE.FREE;

	private final Context context;
	private final NavigationResolver resolver;

	public PhotoHelperImpl(Context context, NavigationResolver resolver) {
		this.context = context;
		this.resolver = resolver;
	}

	public static void copy(File src, File dst) throws IOException {
		FileInputStream inStream = new FileInputStream(src);
		FileOutputStream outStream = new FileOutputStream(dst);
		FileChannel inChannel = inStream.getChannel();
		FileChannel outChannel = outStream.getChannel();
		inChannel.transferTo(0, inChannel.size(), outChannel);
		inStream.close();
		outStream.close();
	}

	private String getPathToTempFiles() {
		return StorageUtils.getStoragePath(context) + File.separator + IMAGE_TEMP + File.separator;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data,
								 PhotoHelperCallback callback) {
		String pathToTempFiles = getPathToTempFiles();
		if (requestCode == SELECT_PICTURE_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				final Uri selectedImage = data.getData();

				if (selectedImage.getScheme().equals("file")) {
					showCrop(selectedImage.getEncodedPath(), pathToTempFiles + lastFileName, mode);
					return;
				}

				String[] filePathColumn = {MediaStore.Images.Media.DATA};
				Cursor cursor = context.getContentResolver()
						.query(selectedImage, filePathColumn, null, null, null);
				if (cursor != null) {
					if (cursor.moveToFirst()) {
						int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
						String filePath = cursor.getString(columnIndex);
						showCrop(filePath, pathToTempFiles + lastFileName, mode);
					}
					cursor.close();
				}
			}

		} else if (requestCode == TAKE_PHOTO_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				showCrop(pathToTempFiles + IMAGE_TEMP_FILE_NAME, pathToTempFiles + lastFileName,
						mode);
			}
		} else if (requestCode == CROP_PHOTO_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_CANCELED) {
				clear(lastFileName);
			}
			clear(IMAGE_TEMP_FILE_NAME);
		}

		if (requestCode == CROP_PHOTO_REQUEST_CODE && Activity.RESULT_OK == resultCode) {
			callback.onGetPath(pathToTempFiles + lastFileName);
		}
	}

	private void showCrop(final String path, String pathTo, CropImageFragment.MODE mode) {
		File from = new File(path);
		File to = new File(pathTo);
		try {
			copy(from, to);
		} catch (IOException e) {
			Log.d(TAG, "copy file error", e);
		}

		resolver.setTargetFragment(CROP_PHOTO_REQUEST_CODE);
		resolver.showFragment(
				CropImageFragment.newInstance(from.getAbsolutePath(), to.getAbsolutePath(), mode));
	}

	@Override
	public void openCamera(CropImageFragment.MODE cropMode) {
		openCamera(cropMode, TEMP_NAME);
	}

	@Override
	public void openCamera(CropImageFragment.MODE cropMode, String fileName) {
		File dir = new File(getPathToTempFiles());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		mode = cropMode;
		lastFileName = fileName;
		String imageFilePath = dir + File.separator + IMAGE_TEMP_FILE_NAME;
		File originalFile = new File(imageFilePath);
		Uri imageFileUri = Uri.fromFile(originalFile);

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
		resolver.startActivityForResultFormFragment(cameraIntent, TAKE_PHOTO_REQUEST_CODE);
	}

	@Override
	public void openGallery(CropImageFragment.MODE cropMode) {
		openGallery(cropMode, TEMP_NAME);
	}

	@Override
	public void openGallery(CropImageFragment.MODE cropMode, String fileName) {
		File dir = new File(getPathToTempFiles());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		mode = cropMode;

		Intent intent = new Intent(Intent.ACTION_PICK);
		lastFileName = fileName;
		intent.setType("image/*");
		resolver.startActivityForResultFormFragment(intent, SELECT_PICTURE_REQUEST_CODE);
	}

	public void clear(String avatarFileName) {
		File file = new File(getPathToTempFiles(), avatarFileName);
		file.delete();
	}

}
