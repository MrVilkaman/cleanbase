package ru.fixapp.fooproject.presentationlayer.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.UUID;

import ru.fixapp.fooproject.presentationlayer.fragments.core.BaseFragment;
import ru.fixapp.fooproject.presentationlayer.fragments.core.photocrop.CropImageFragment;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class PhotoUtils {

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

	private PhotoUtils() {
	}

	public static String getLastFileName() {
		return lastFileName;
	}

	public static void openGallery(Fragment fragment, CropImageFragment.MODE newMode) {
		openGallery(fragment, TEMP_NAME,newMode);
	}

	public static void openGallery(Fragment fragment, String fileName,CropImageFragment.MODE newMode) {
		File dir = new File(getPathToTempFiles(fragment.getActivity()));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		mode = newMode;

		Intent intent = new Intent(Intent.ACTION_PICK);
		lastFileName = fileName;
		intent.setType("image/*");
		fragment.startActivityForResult(intent, SELECT_PICTURE_REQUEST_CODE);
	}

	public static void openCamera(Fragment frag, CropImageFragment.MODE mode) {
		openCamera(frag, TEMP_NAME,mode);
	}

	public static void openCamera(Fragment frag, String fileName, CropImageFragment.MODE newMode) {

	}

	public static String getPathToTempFiles(Context context) {
		return StorageUtils.getStoragePath(context) + File.separator + IMAGE_TEMP + File.separator;
	}

	public static void onActivityResult(BaseFragment fragment, int requestCode, int resultCode, Intent data) {



	}

	public static void saveToFile(Bitmap bmp, File filename, Bitmap.CompressFormat format) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filename);
			bmp.compress(format, 90, out); // bmp is your Bitmap instance
		} catch (Exception e) {
			Log.d(TAG, "saveToFile error", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				Log.d(TAG, "saveToFile error", e);
			}
		}
	}


	private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
		Matrix matrix = new Matrix();
		switch (orientation) {
			case ExifInterface.ORIENTATION_NORMAL:
				return bitmap;
			case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
				matrix.setScale(-1, 1);
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				matrix.setRotate(180);
				break;
			case ExifInterface.ORIENTATION_FLIP_VERTICAL:
				matrix.setRotate(180);
				matrix.postScale(-1, 1);
				break;
			case ExifInterface.ORIENTATION_TRANSPOSE:
				matrix.setRotate(90);
				matrix.postScale(-1, 1);
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				matrix.setRotate(90);
				break;
			case ExifInterface.ORIENTATION_TRANSVERSE:
				matrix.setRotate(-90);
				matrix.postScale(-1, 1);
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				matrix.setRotate(-90);
				break;
			default:
				return bitmap;
		}
		try {
			Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			bitmap.recycle();

			return bmRotated;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		}
	}


	// if not use crop
	private static void saveBitmapToFile(Context context, Bitmap croppedImage, Uri saveUri) {
		if (saveUri != null) {
			OutputStream outputStream = null;
			try {
				outputStream = context.getContentResolver()
						.openOutputStream(saveUri);
				if (outputStream != null) {
					croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
				}
			} catch (IOException e) {
				Log.e(TAG, "Cannot newUser file: " + saveUri.toString() + " | " + e.getMessage());
			} finally {
				closeSilently(outputStream);
				croppedImage.recycle();
			}
		}
	}

	private static void closeSilently(@Nullable Closeable c) {
		if (c == null) return;
		try {
			c.close();
		} catch (Throwable t) {
			// Do nothing
		}
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

	public static String getRandomName() {
		return String.format("%s.jpg", UUID.randomUUID());
	}
}
