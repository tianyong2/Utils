package com.eden.gif.one;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.EditText;
import android.widget.TextView;

public class GifUtil {
	// 表情
	private static Integer[] faceIds = FaceDate.getFaceIds();
	private static String[] faceNames = FaceDate.getFaceNames();

	public static void setSpannableText(final Context context, final EditText et, final String content) {

		// 输出的gif图像太小，需放大的倍数
		final int multiple = 1;
		// 存储正文中表情符号的位置
		class facePos {
			int s;// 表情文字起始
			int e;// 表情文字结束点
			int i;// 该表情文字表示的表情的序号

			public facePos(int s, int e, int i) {
				this.s = s;
				this.e = e;
				this.i = i;
			}

		}
		// 先显示文字
		et.setText(content);
		// 新线程计算是否需要替换表情，需要替换就替换
		new Thread(new Runnable() {

			public void run() {

				// 用以存储需替换的表情的位置
				List<facePos> faceList = new ArrayList<facePos>();
				// 查找表情的位置
				for (int i = 0; i < content.length(); i++) {
					if (content.charAt(i) == '[') {

						for (int k = i; k < i + 7; k++) {
							if (content.charAt(k) == ']') {
								for (int j = 0; j < faceNames.length; j++) {
									if (faceNames[j].equals(content.substring(i + 1, k))) {
										// 保存需替换的表情文字的位置
										facePos fp = new facePos(i, k, j);
										faceList.add(fp);
									}
								}

								break;
							}
						}

					}
				}

				// 如果无表情
				if (faceList.size() == 0) {

				} else {
					// 如果有表情
					SpannableString ss = new SpannableString(content);
					for (int j = 0; j < faceList.size(); j++) {

						AnimationDrawable mFace = new AnimationDrawable();
						byte mFrame = 0;

						final GifOpenHelper gHelper = new GifOpenHelper();
						gHelper.read(context.getResources().openRawResource(faceIds[faceList.get(j).i]));
						BitmapDrawable bd = new BitmapDrawable(gHelper.getImage());
						mFace.addFrame(bd, gHelper.getDelay(0));
						for (int i = 1; i < gHelper.getFrameCount(); i++) {
							mFace.addFrame(new BitmapDrawable(gHelper.nextBitmap()), gHelper.getDelay(i));
						}

						mFace.setBounds(0, 0, bd.getIntrinsicWidth() * multiple, bd.getIntrinsicHeight() * multiple);
						mFace.setOneShot(false);

						ImageSpan span = new ImageSpan(mFace, ImageSpan.ALIGN_BASELINE);
						// 替换一个表情
						ss.setSpan(span, faceList.get(j).s, faceList.get(j).e + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

						final SpannableString ssForPost = ss;
						// 显示新的已经替换一个表情的text
						et.post(new Runnable() {

							public void run() {
								et.setText(ssForPost);

							}

						});
						// 开新线程播放gif表情动画
						final AnimationDrawable tmFace = mFace;
						final byte tmFrame = mFrame;
						new Thread(new Runnable() {
							AnimationDrawable mFace = tmFace;
							byte mFrame = tmFrame;

							public void run() {
								while (true) {
									mFace.selectDrawable(mFrame++);
									if (mFrame == mFace.getNumberOfFrames()) {
										mFrame = 0;
									}
									et.post(new Runnable() {
										
										@Override
										public void run() {
											insertText(et, "@");
											deleteText(et);
											
										}
									});
//									et.postInvalidate();
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						}).start();

					}
				}
			}

		}).start();

	}

	/** 获取EditText光标所在的位置 */
	private static int getEditTextCursorIndex(EditText mEditText) {
		return mEditText.getSelectionStart();
	}

	/** 向EditText指定光标位置插入字符串 */
	private static void insertText(EditText mEditText, String mText) {
		mEditText.getText().insert(getEditTextCursorIndex(mEditText), mText);
	}

	/** 向EditText指定光标位置删除字符串 */
	private static void deleteText(EditText mEditText) {
		mEditText.getText().delete(getEditTextCursorIndex(mEditText) - 1, getEditTextCursorIndex(mEditText));
	}

	public static void setSpannableText(final Context context, final TextView tv, final String content) {
		// 输出的gif图像太小，需放大的倍数
		final int multiple = 1;
		// 存储正文中表情符号的位置
		class facePos {
			int s;// 表情文字起始
			int e;// 表情文字结束点
			int i;// 该表情文字表示的表情的序号

			public facePos(int s, int e, int i) {
				this.s = s;
				this.e = e;
				this.i = i;
			}

		}
		// 先显示文字
		tv.setText(content);
		// 新线程计算是否需要替换表情，需要替换就替换
		new Thread(new Runnable() {

			public void run() {

				// 用以存储需替换的表情的位置
				List<facePos> faceList = new ArrayList<facePos>();
				// 查找表情的位置
				for (int i = 0; i < content.length(); i++) {
					if (content.charAt(i) == '[') {

						for (int k = i; k < i + 7; k++) {
							if (content.charAt(k) == ']') {
								for (int j = 0; j < faceNames.length; j++) {
									if (faceNames[j].equals(content.substring(i + 1, k))) {
										// 保存需替换的表情文字的位置
										facePos fp = new facePos(i, k, j);
										faceList.add(fp);
									}
								}

								break;
							}
						}

					}
				}

				// 如果无表情
				if (faceList.size() == 0) {

				} else {
					// 如果有表情
					SpannableString ss = new SpannableString(content);
					for (int j = 0; j < faceList.size(); j++) {

						AnimationDrawable mFace = new AnimationDrawable();
						byte mFrame = 0;

						final GifOpenHelper gHelper = new GifOpenHelper();
						gHelper.read(context.getResources().openRawResource(faceIds[faceList.get(j).i]));
						BitmapDrawable bd = new BitmapDrawable(gHelper.getImage());
						mFace.addFrame(bd, gHelper.getDelay(0));
						for (int i = 1; i < gHelper.getFrameCount(); i++) {
							mFace.addFrame(new BitmapDrawable(gHelper.nextBitmap()), gHelper.getDelay(i));
						}

						mFace.setBounds(0, 0, bd.getIntrinsicWidth() * multiple, bd.getIntrinsicHeight() * multiple);
						mFace.setOneShot(false);

						ImageSpan span = new ImageSpan(mFace, ImageSpan.ALIGN_BASELINE);
						// 替换一个表情
						ss.setSpan(span, faceList.get(j).s, faceList.get(j).e + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

						final SpannableString ssForPost = ss;
						// 显示新的已经替换一个表情的text
						tv.post(new Runnable() {

							public void run() {
								tv.setText(ssForPost);

							}

						});
						// 开新线程播放gif表情动画
						final AnimationDrawable tmFace = mFace;
						final byte tmFrame = mFrame;
						new Thread(new Runnable() {
							AnimationDrawable mFace = tmFace;
							byte mFrame = tmFrame;

							public void run() {
								while (true) {
									mFace.selectDrawable(mFrame++);
									if (mFrame == mFace.getNumberOfFrames()) {
										mFrame = 0;
									}
									tv.postInvalidate();
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						}).start();

					}
				}
			}

		}).start();
	}
}
