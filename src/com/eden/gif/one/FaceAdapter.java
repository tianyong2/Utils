package com.eden.gif.one;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class FaceAdapter extends BaseAdapter {
	private Context context;
	private Integer[] faceIds = FaceDate.getFaceIds();// 表情的资源id
	private String[] faceNames = FaceDate.getFaceNames();// 表情的文字说明
	private PopupWindow FacePopupWindow;
	private EditText editText;

	private AnimationDrawable mFace = new AnimationDrawable();
	private byte mFrame = 0;

	public FaceAdapter(Context context, PopupWindow FacePopupWindow, EditText editText) {
		this.context = context;
		this.FacePopupWindow = FacePopupWindow;
		this.editText = editText;
	}

	public int getCount() {
		return faceIds.length;
	}

	public Object getItem(int position) {
		return faceIds[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv;
		if (convertView == null) {

			iv = new ImageView(context);
			// 设置布局 图片大小
			iv.setLayoutParams(new GridView.LayoutParams(70, 70));
			// 设置显示比例类型
			iv.setScaleType(ImageView.ScaleType.FIT_CENTER);

		} else {
			iv = (ImageView) convertView;
		}

		iv.setImageResource(faceIds[position]);

		final int pos = position;
		iv.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				FacePopupWindow.dismiss();
				// 添加表情到edittext

				SpannableString ss = new SpannableString("[" + faceNames[pos] + "]");

				final GifOpenHelper gHelper = new GifOpenHelper();
				gHelper.read(context.getResources().openRawResource(faceIds[pos]));
				BitmapDrawable bd = new BitmapDrawable(gHelper.getImage());
				mFace.addFrame(bd, gHelper.getDelay(0));
				for (int i = 1; i < gHelper.getFrameCount(); i++) {
					mFace.addFrame(new BitmapDrawable(gHelper.nextBitmap()), gHelper.getDelay(i));
				}

				mFace.setBounds(0, 0, bd.getIntrinsicWidth() * 2, bd.getIntrinsicHeight() * 2);
				mFace.setOneShot(false);

				ImageSpan span = new ImageSpan(mFace, ImageSpan.ALIGN_BASELINE);
				ss.setSpan(span, 0, ("[" + faceNames[pos] + "]").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				editText.getEditableText().insert(editText.getSelectionStart(), ss);

				// 新线程执行gif动画更新
				new Thread(new Runnable() {
					public void run() {
						while (true) {
							mFace.selectDrawable(mFrame++);
							if (mFrame == mFace.getNumberOfFrames()) {
								mFrame = 0;
							}
							editText.postInvalidate();
							try {
								// gif各帧之间的间隔，可以自己调整
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();

			}

		});

		return iv;
	}
}
