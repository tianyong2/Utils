package com.eden.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;

/**
 * 缓存对象
 * 
 * @author Administrator
 * 
 */
public class CacheUtil {
	/**
	 * 读取对象
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Object readObject(String file, Activity instance) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = instance.openFileInput(file);
			ois = new ObjectInputStream(fis);
			return ois.readObject();
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			}
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 保存对象
	 * 
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public static boolean saveObject(Object ser, String file, Activity instance) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = instance.openFileOutput(file, Activity.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}
	/**
	 * 清除缓存
	 * 
	 * @param context
	 */
	public static void clearAppCache(Context context) {
		try {
			File dir = context.getCacheDir();
			if (dir != null && dir.isDirectory()) {
				deleteTree(dir);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static boolean deleteTree(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteTree(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

}
