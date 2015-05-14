package com.eden.util;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import android.text.TextUtils;

import com.eden.util.HanziToPinyin.Token;

/**
 * 和HanziToPinying.java相关的类
 2.实现Comparator接口： [java] view plaincopy
 */
public class DataComparator implements Comparator<Object> {
	Collator collator = Collator.getInstance(java.util.Locale.CHINA); // 调入这个是解决中文排序问题

	@Override
	public int compare(Object object1, Object object2) {
		HashMap<String, String> map1 = (HashMap<String, String>) object1;
		HashMap<String, String> map2 = (HashMap<String, String>) object2;

		String name1 = getFirstChar(map1.get("appName"));
		String name2 = getFirstChar(map2.get("appName"));
		String key1 = getPinYin(name1);
		String key2 = getPinYin(name2);
		return key1.compareTo(key2);
	}

	public DataComparator() {
	}

	private String getFirstChar(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		String convert = "";
		char word = str.charAt(0);
		convert += word;
		return convert.toUpperCase();
	}

	private String getPinYin(String input) {
		ArrayList<Token> tokens = HanziToPinyin.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0) {
			for (Token token : tokens) {
				if (Token.PINYIN == token.type) {
					sb.append(token.target);
				} else {
					sb.append(token.source);
				}
			}
		}
		return sb.toString().toLowerCase();
	}
}