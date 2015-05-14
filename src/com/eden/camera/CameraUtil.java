package com.eden.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.eden.util.ChineseUtil;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class CameraUtil {
    /**
     * 
     take a picture relate start
     */
    public static Bitmap loadImgThumbnail(String filePath, int w, int h) {
	Bitmap bitmap = getBitmapByPath(filePath);
	return zoomBitmap(bitmap, w, h);
    }

    public static Bitmap loadImg(String filePath) {
	return getBitmapByPath(filePath);
    }

    /**
     * 放大缩小图片
     * 
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
	Bitmap newbmp = null;
	if (bitmap != null) {
	    int width = bitmap.getWidth();
	    int height = bitmap.getHeight();
	    Matrix matrix = new Matrix();
	    float scaleWidht = ((float) w / width);
	    float scaleHeight = ((float) h / height);
	    matrix.postScale(scaleWidht, scaleHeight);
	    newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
		    true);
	}
	return newbmp;
    }

    /**
     * 获取bitmap
     * 
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
	return getBitmapByPath(filePath, null);
    }

    public static Bitmap getBitmapByPath(String filePath,
	    BitmapFactory.Options opts) {
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

    @SuppressLint("SimpleDateFormat")
    public static File getPictureSavePath(Context context) {
	String savePath = StorageUtils.getOwnCacheDirectory(
		context, "images-to-be-uploaded")
		.getAbsolutePath();
	// 没有挂载SD卡，无法保存文件
	if (ChineseUtil.isEmpty(savePath)) {
	    return null;
	}
	String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
		.format(new Date());
	String fileName = "dotshare_" + timeStamp + ".jpg";// 照片命名
	File out = new File(savePath, fileName);
	return out;
    }

    /**
     * 返回保存图片的绝对路径 requestCode : ImageUtils.REQUEST_CODE_FROM_CAMERA
     * 
     * @param context
     * @return
     */
    public static String takePicture(Fragment context) {
	File out = getPictureSavePath(context.getActivity());
	if (out == null) {
	    return null;
	}
	String originalPath = out.getAbsolutePath();// 该照片的绝对路径
	Uri uri = Uri.fromFile(out);
	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	context.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
	return originalPath;
    }

    /**
     * 返回保存图片的绝对路径 requestCode : ImageUtils.REQUEST_CODE_FROM_CAMERA
     * 
     * @param context
     * @return
     */
    public static String takePicture(Activity context) {
	File out = getPictureSavePath(context);
	if (out == null) {
	    return null;
	}
	String originalPath = out.getAbsolutePath();// 该照片的绝对路径
	Uri uri = Uri.fromFile(out);
	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	context.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
	return originalPath;
    }

    public static final int REQUEST_CODE_FROM_CAMERA = 564;
    /**
     * 
     take a picture relate end
     */

}
