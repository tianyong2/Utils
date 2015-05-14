package com.eden.toast;


import com.eden.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.widget.Toast;

/**
 * Toast统一管理类
 *  @author eden
 */
public class T
{

	private T()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isShow = true;

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 获取等待框
	 * 
	 * @param activity
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog getProgressDialogFor(Activity activity, boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(activity);
		dialog.setMessage(activity.getString(R.string.wait));
		dialog.setIndeterminate(true);
		dialog.setCancelable(cancelable);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		return dialog;
	}
	
	/**
	 * 获取等待框
	 * 
	 * @param activity
	 * @param cancelable
	 * @param stringRsc
	 * @return
	 */
	public static ProgressDialog getProgressDialogFor(Activity activity, boolean cancelable, int stringRsc) {
		ProgressDialog dialog = new ProgressDialog(activity);
		dialog.setMessage(activity.getString(stringRsc));
		dialog.setIndeterminate(true);
		dialog.setCancelable(cancelable);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		return dialog;
	}
	public static ProgressDialog getProgressDialogHorizontalFor(Activity activity, boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(activity);
		// dialog.setMessage(activity.getString(R.string.wait));
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setMax(100);
		dialog.setCancelable(cancelable);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		return dialog;
	}
	/**
	 * 取消等待框
	 * 
	 * @param dialog
	 */
	public static void dismissSilently(final ProgressDialog dialog) {
		if (dialog != null) {
			try {
				dialog.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

}