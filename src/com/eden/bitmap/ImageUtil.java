/**
 * 
 */
package com.eden.bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.Random;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * @author Administrator
 * 
 */
public class ImageUtil {
	/**
	 * 重置图片大小
	 * 
	 * @param width
	 * @param height
	 * @param pathSrc
	 * @return
	 */
	public static String resizeImageToFile(int width, int height, String pathSrc, Application context) {
		// Get the dimensions of the View
		int targetW = width;
		int targetH = height;

		Bitmap bitmap = getImageThumbnail(pathSrc, targetW, targetH);
		if (bitmap == null)
			return null;

		return saveBMPtoFile(bitmap, context);

	}

	/**
	 * 获取缓存路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getCacheDirPath(Context context) {
		return getCacheDirFile(context).getAbsolutePath();
	}

	/**
	 * 获取缓存文件
	 * 
	 * @param context
	 * @return
	 */
	public static File getCacheDirFile(Context context) {
		File file = context.getCacheDir().getAbsoluteFile();
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 保存图片到文件
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String saveBMPtoFile(Bitmap bitmap, Application context) {
		String fileout = getCacheDirPath(context) + System.currentTimeMillis() + "resized_image.png";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileout);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		} catch (Exception e) {
			e.printStackTrace();
			fileout = null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileout;
	}

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

	/**
	 * 创建图片文件
	 * 
	 * @param context
	 * @return
	 * @throws IOException
	 */
	public static File createImageFile(Context context) throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = null;

		if (storageDir == null) {
			try {
				storageDir = new File(Environment.getExternalStorageDirectory() + "/" + "Qfang_" + "/");
				storageDir.mkdirs();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		try {
			if (storageDir == null) {
				storageDir = context.getExternalCacheDir();
			}// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (storageDir == null) {
			try {
				storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		// mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

	/**
	 * Shrinks bitmap and save it to a file and returns you the outputstream to file
	 * 
	 * @param context
	 * @param file
	 * @param width
	 * @param height
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static File ShrinkBitmap(Context context, File file, int width, int height) throws IOException {

		Bitmap bitmap = getImageThumbnail(file.getAbsolutePath(), width, height);

		File temp = new File(context.getCacheDir(), "qfang-uploads");
		temp.mkdirs();
		String path = temp.getAbsolutePath() + File.separator + "IMG_Upload_" + new Date().getTime() + ".jpeg";
		File outFile = new File(path);

		try {
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
			bitmap.compress(CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			bitmap.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outFile;
	}

	public static Bitmap getImageThumbnail(final String pPath, final int pWidth, final int pHeight) {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = pWidth;
		int targetH = pHeight;

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(pPath, bmOptions);

		return bitmap;
	}

	/**
	 * Shrinks bitmap and save it to a file and returns you the outputstream to file
	 * 
	 * @param context
	 * @param file
	 * @param width
	 * @param height
	 * @param quality
	 * @return
	 * @throws IOException
	 */
	public static File ShrinkBitmap(Context context, InputStream inputStream, int inSampleSize, int quality) throws IOException {

		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, bmpFactoryOptions);

		bmpFactoryOptions.inSampleSize = inSampleSize;

		bmpFactoryOptions.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeStream(inputStream, null, bmpFactoryOptions);
		System.gc();

		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "WWP-Uploads" + File.separator + "compressed_" + new Random().nextInt(9999)
				+ ".jpg";
		File outFile = new File(path);
		outFile.getParentFile().mkdirs();

		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));

		bitmap.compress(CompressFormat.JPEG, quality, out);
		out.flush();
		out.close();
		System.gc();

		return outFile;
	}

	public static Bitmap getBitmapByPath(String filePath) {
		return getBitmapByPath(filePath, null);
	}

	public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis, null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
		Bitmap bitmap = null;
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	public static boolean saveBitmap2file(Bitmap bmp, String filename) {
		CompressFormat format = Bitmap.CompressFormat.JPEG;
		int quality = 100;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return bmp.compress(format, quality, stream);
	}

	/**
	 * 将图片保存到相册
	 * 
	 * @param bm
	 *            需要保存的图片
	 * @param title
	 *            图片的名字
	 * @param description
	 *            图片的描述
	 * @return 返回插入图片的路径,有可能为null
	 */
	public static String savePictureToAlbums(Context context, Bitmap bm, String title, String description) {
		String url = MediaStore.Images.Media.insertImage(context.getContentResolver(), bm, title, description);

		// 保存之后需要刷新相册
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

		// 这里要注意的是，发送上面的广播，实际上效率会比较低，也会增加耗电量.上面提到，
		// 插入图片的方法对应的返回值为图片保存的绝对路径. 因此我们只要获取该路径
		// ，发送广播扫描该路径即可.这里补充一点，使用发送广播的方式在Android4.4上会遇到异常：

		// context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory() + url)));
		return url;
	}

	// /**
	// * 获取手机中的图片
	// * @return
	// */
	// private ArrayList<CustomGallery> getGalleryPhotos() {
	// ArrayList<CustomGallery> galleryList = new ArrayList<CustomGallery>();
	//
	// try {
	// final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
	// final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
	//
	// Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
	//
	// if (imagecursor != null && imagecursor.getCount() > 0) {
	//
	// while (imagecursor.moveToNext()) {
	// CustomGallery item = new CustomGallery();
	//
	// int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
	//
	// item.sdcardPath = imagecursor.getString(dataColumnIndex);
	//
	// galleryList.add(item);
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// // show newest photo at beginning of the list
	// Collections.reverse(galleryList);
	// return galleryList;
	// }
}
