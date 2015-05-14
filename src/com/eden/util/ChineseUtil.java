package com.eden.util;

import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

import com.eden.util.HanziToPinyin.Token;

public class ChineseUtil {
    private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274,
	    2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
	    4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
    private final static String[] lc_FirstLetter = { "a", "b", "c", "d", "e",
	    "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
	    "t", "w", "x", "y", "z" };

    /**
     * 取得给定汉字串的首字母串,即声母串
     * 
     * @param str
     *            给定汉字串
     * @return 声母串
     */
    public String getAllFirstLetter(String str) {
	if (str == null || str.trim().length() == 0) {
	    return "";
	}

	String _str = "";
	for (int i = 0; i < str.length(); i++) {
	    _str = _str + this.getFirstLetter(str.substring(i, i + 1));
	}

	return _str;
    }

    /**
     * 取得给定汉字的首字母,即声母
     * 
     * @param chinese
     *            给定的汉字
     * @return 给定汉字的声母
     */
    public String getFirstLetter(String chinese) {
	if (chinese == null || chinese.trim().length() == 0) {
	    return "";
	}
	chinese = this.conversionStr(chinese, "GB2312", "ISO8859-1");

	if (chinese.length() > 1) // 判断是不是汉字
	{
	    int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
	    int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
	    li_SectorCode = li_SectorCode - 160;
	    li_PositionCode = li_PositionCode - 160;
	    int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
	    if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
		for (int i = 0; i < 23; i++) {
		    if (li_SecPosCode >= li_SecPosValue[i]
			    && li_SecPosCode < li_SecPosValue[i + 1]) {
			chinese = lc_FirstLetter[i];
			break;
		    }
		}
	    } else // 非汉字字符,如图形符号或ASCII码
	    {
		chinese = this.conversionStr(chinese, "ISO8859-1", "GB2312");
		chinese = chinese.substring(0, 1);
	    }
	}

	return chinese;
    }

    /**
     * 字符串编码转换
     * 
     * @param str
     *            要转换编码的字符串
     * @param charsetName
     *            原来的编码
     * @param toCharsetName
     *            转换后的编码
     * @return 经过编码转换后的字符串
     */
    private String conversionStr(String str, String charsetName,
	    String toCharsetName) {
	try {
	    str = new String(str.getBytes(charsetName), toCharsetName);
	} catch (UnsupportedEncodingException ex) {
	    System.out.println("字符串编码转换异常：" + ex.getMessage());
	}
	return str;
    }

    /**
     * 随机生成汉字 汉字是由2个字节组成的
     * 
     * @return
     */
    public static String randomWord() {
	String str = "";
	int hightPos;// 高位
	int lowPos;// 地位
	Random r = new Random();
	hightPos = (176 + Math.abs(r.nextInt(39)));
	lowPos = (161 + Math.abs(r.nextInt(93)));
	byte[] b = new byte[2];
	b[0] = Integer.valueOf(hightPos).byteValue();
	b[1] = Integer.valueOf(lowPos).byteValue();
	try {
	    str = new String(b, "GBK");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return str;
    }

    /**
     * 获取一段汉字全拼方法
     * 
     * @param source
     * @return
     */
    public static String getFullPinYin(String source) {

	if (!Arrays.asList(Collator.getAvailableLocales()).contains(
		Locale.CHINA)) {

	    return source;

	}

	ArrayList<Token> tokens = HanziToPinyin.getInstance().get(source);

	if (tokens == null || tokens.size() == 0) {

	    return source;

	}

	StringBuffer result = new StringBuffer();

	for (Token token : tokens) {

	    if (token.type == Token.PINYIN) {

		result.append(token.target);

	    } else {

		result.append(token.source);

	    }

	    return result.toString();

	}

	return source;
    }

    /**
     * 获取一段汉字的简拼
     * 
     * @param source
     * @return
     */
    public String getFirstPinYin(String source) {

	if (!Arrays.asList(Collator.getAvailableLocales()).contains(
		Locale.CHINA)) {

	    return source;

	}

	ArrayList<Token> tokens = HanziToPinyin.getInstance().get(source);

	if (tokens == null || tokens.size() == 0) {

	    return source;

	}

	StringBuffer result = new StringBuffer();

	for (Token token : tokens) {

	    if (token.type == Token.PINYIN) {

		result.append(token.target.charAt(0));

	    } else {
		result.append("#");

	    }

	    return result.toString();

	}
	return source;
    }
    
    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     * 
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
	if (input == null || "".equals(input))
	    return true;

	for (int i = 0; i < input.length(); i++) {
	    char c = input.charAt(i);
	    if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
		return false;
	    }
	}
	return true;
    }


}
