package com.eden.gif.two;

import com.eden.R;


public class FaceDate {
	private static Integer[] faceIds = null;
	private static String[] faceNames = null;

	public static Integer[] getFaceIds() {
		if (faceIds == null) {
			faceIds = new Integer[] { R.drawable.f001, R.drawable.f002, R.drawable.f003, R.drawable.f004, R.drawable.f005, R.drawable.f006, R.drawable.f007 };
		}

		return faceIds;
	}

	public static String[] getFaceNames() {
		if (faceNames == null) {
			faceNames = new String[] { "1", "2", "3", "4", "5", "6", "7" };
		}
		return faceNames;
	}

}
