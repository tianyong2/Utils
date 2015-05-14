package com.eden.spanablestring;

import android.app.Activity;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.BufferType;

/**
 * comment listview 和 name1,name2,name等12人 觉得赞
 * 
 * @author Administrator
 *
 */
public class SpanableStringUtil {
    public interface OnClickWordCallBack {

	void onClickNumber(View v, int number);
    }

    // public static void setTvLikeText(TextView tv, List<UserPojo> list,
    // OnClickWordCallBack onClickNameOrNumberCallBack) {
    // if (list == null || list.size() <= 0) {
    // tv.setVisibility(View.GONE);
    // return;
    // }
    // tv.setVisibility(View.VISIBLE);
    // int[] sizes = getEachWordLength(list);
    // String content = getLikeContent(list, list.size());
    // setSpannableString(tv, content);
    // setWordListeners(tv, list.size(), list, sizes,
    // onClickNameOrNumberCallBack);
    // }

    private static void setSpannableString(TextView tv, String content) {
	SpannableStringBuilder ssb = new SpannableStringBuilder(content);
	// 字体大小
	ssb.setSpan(new RelativeSizeSpan(1.0f), 0, ssb.length(),
		Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	tv.setText(ssb, BufferType.SPANNABLE);
	// LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// tv.setLayoutParams(params);
	tv.setMovementMethod(LinkMovementMethod.getInstance());
	tv.setHighlightColor(Color.TRANSPARENT);
	tv.setTextColor(Color.BLACK);
    }

    // private static void setWordListeners(TextView tv, int type,
    // List<UserPojo> words, int[] sizes,
    // final OnClickWordCallBack onClickNameOrNumberCallBack) {
    // Spannable spans = (Spannable) tv.getText();
    // if (type <= 3) {
    // setWordsClickableSpan(type, spans, words, sizes,
    // new OnClickWordCallBack() {
    // @Override
    // public void onClickWord(View v, UserPojo userPojo) {
    // if (onClickNameOrNumberCallBack != null)
    // onClickNameOrNumberCallBack.onClickWord(v,
    // userPojo);
    // }
    //
    // @Override
    // public void onClickNumber(View v, int number) {
    //
    // }
    // });
    // } else {
    // setWordsClickableSpan(3, spans, words, sizes,
    // onClickNameOrNumberCallBack);
    // setNumberClickableSpan(words.size(), spans, words, sizes,
    // onClickNameOrNumberCallBack);
    // }
    // }
    //
    // private static void setNumberClickableSpan(int number, Spannable spans,
    // List<UserPojo> words, int[] sizes,
    // OnClickWordCallBack onClickNameOrNumberCallBack) {
    // int numberLength = String.valueOf(number).length();
    // int start = sizes[0] + sizes[1] + sizes[2] + 3;
    // int end = start + numberLength;
    // ClickableSpan clickSpan = getClickableSpan(onClickNameOrNumberCallBack,
    // number);
    // spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // }

    // private static void setWordsClickableSpan(int type, Spannable spans,
    // List<UserPojo> words, int[] sizes,
    // OnClickWordCallBack onClickNameOrNumberCallBack) {
    // int start = 0;
    // int end = 0;
    // for (int i = 1; i <= type; i++) {
    // end = start + sizes[i - 1];
    // ClickableSpan clickSpan = getClickableSpan(
    // onClickNameOrNumberCallBack, words.get(i - 1));
    // spans.setSpan(clickSpan, start, end,
    // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // start = end + 1;
    // }
    // }

    // private static String getLikeContent(List<UserPojo> list, int type) {
    // StringBuilder content = new StringBuilder();
    // switch (type) {
    // case 1:
    // // eden举得赞
    // content.append(list.get(0).username).append("觉得赞");
    // break;
    // case 2:
    // // eden和eden觉得赞
    // content.append(list.get(0).username).append("和")
    // .append(list.get(1).username).append("觉得赞");
    // break;
    // case 3:
    // // eden和eden,eden觉得赞
    // content.append(list.get(0).username).append("和")
    // .append(list.get(1).username).append(",")
    // .append(list.get(2).username).append("觉得赞");
    // break;
    // default:
    // // eden和eden,eden等10人觉得赞
    // content.append(list.get(0).username).append("和")
    // .append(list.get(1).username).append(",")
    // .append(list.get(2).username).append("等").append(type)
    // .append("人觉得赞");
    // break;
    // }
    // return content.toString();
    // }

    // private static ClickableSpan getClickableSpan(
    // final OnClickWordCallBack onClickNameOrNumberCallBack,
    // final UserPojo pojo) {
    // return new ClickableSpan() {
    // @Override
    // public void onClick(View widget) {
    // if (onClickNameOrNumberCallBack != null)
    // onClickNameOrNumberCallBack.onClickWord(widget, pojo);
    // }
    //
    // @Override
    // public void updateDrawState(TextPaint tp) {
    // tp.setColor(AppContext.getContext().getResources()
    // .getColor(R.color.blue));
    // tp.setTextSize(ScreenUtils.sp2px(12));
    // }
    // };
    // }

    private static ClickableSpan getClickableSpan(
	    final OnClickWordCallBack onClickNameOrNumberCallBack,
	    final int number) {
	return new ClickableSpan() {
	    @Override
	    public void onClick(View widget) {
		if (onClickNameOrNumberCallBack != null)
		    onClickNameOrNumberCallBack.onClickNumber(widget, number);
	    }

	    @Override
	    public void updateDrawState(TextPaint tp) {
		// tp.setColor(AppContext.getContext().getResources()
		// .getColor(R.color.blue));
	    }
	};
    }

    // private static int[] getEachWordLength(List<UserPojo> list) {
    // int[] sizes = new int[list.size()];
    // for (int i = 0; i < list.size(); i++) {
    // String s = list.get(i).username;
    // if (s == null) {
    // sizes[i] = 0;
    // } else {
    // sizes[i] = s.length();
    // }
    // }
    // return sizes;
    // }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // public static void setTvNameText(CommentPojo commentPojo, TextView tv,
    // UserPojo user1, UserPojo user2,
    // OnClickWordCallBack onClickNameOrNumberCallBack, String comment,
    // final OnClickConmentCallBack onClickConmentCallBack) {
    // if (user2.userId == 0) {
    // setTvNameTextOne(tv, user1, onClickNameOrNumberCallBack, comment,
    // onClickConmentCallBack, commentPojo);
    // } else {
    // setTvNameTextTwo(tv, user1, user2, onClickNameOrNumberCallBack,
    // comment, onClickConmentCallBack, commentPojo);
    // }
    // }

    public interface OnClickConmentCallBack {
	void callBack(View v);
    }

    private static void setTvNameTextOne(TextView tv,
	    final OnClickWordCallBack onClickNameOrNumberCallBack,
	    String comment, final OnClickConmentCallBack onClickConmentCallBack) {

	String content = comment;
	setSpannableString(tv, content);
	Spannable spans = (Spannable) tv.getText();
	ClickableSpan clickSpan = new ClickableSpan() {

	    @Override
	    public void onClick(View widget) {
		if (onClickConmentCallBack != null)
		    onClickConmentCallBack.callBack(widget);
	    }

	    @Override
	    public void updateDrawState(TextPaint tp) {
		// tp.setColor(AppContext.getContext().getResources()
		// .getColor(R.color.grey_fong_2));
	    }

	};
	// spans.setSpan(clickSpan, user.username.length() + 1,
	// content.length(),
	// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	ClickableSpan clickSpanName = new ClickableSpan() {

	    @Override
	    public void onClick(View widget) {
//		if (onClickNameOrNumberCallBack != null)
//		    onClickNameOrNumberCallBack.onClickWord(widget, user);

	    }

	    @Override
	    public void updateDrawState(TextPaint tp) {
		// tp.setColor(AppContext.getContext().getResources()
		// .getColor(R.color.blue));
	    }

	};
	// spans.setSpan(clickSpanName, 0, user.username.length(),
	// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    // private static void setTvNameTextTwo(TextView tv, final UserPojo user1,
    // final UserPojo user2,
    // final OnClickWordCallBack onClickNameOrNumberCallBack,
    // String comment,
    // final OnClickConmentCallBack onClickConmentCallBack,
    // CommentPojo commentPojo) {
    // String content = getTvNameContent(user1, user2) + comment;
    //
    // setSpannableString(tv, content);
    // setWordListeners(tv, user1, user2, onClickNameOrNumberCallBack,
    // onClickConmentCallBack, commentPojo);
    // }

    // private static void setWordListeners(TextView tv, UserPojo user1,
    // UserPojo user2, OnClickWordCallBack onClickNameOrNumberCallBack,
    // final OnClickConmentCallBack onClickConmentCallBack,
    // final CommentPojo commentPojo) {
    // Spannable spans = (Spannable) tv.getText();
    // int start = 0;
    // int end = 0;
    // UserPojo[] pojos = new UserPojo[] { user1, user2 };
    // for (int i = 1; i <= 2; i++) {
    // end = start + pojos[i - 1].username.length();
    // ClickableSpan clickSpan = getClickableSpan(
    // onClickNameOrNumberCallBack, pojos[i - 1]);
    // spans.setSpan(clickSpan, start, end,
    // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // start = end + 2;
    // }
    // ClickableSpan clickSpan = new ClickableSpan() {
    //
    // @Override
    // public void onClick(View widget) {
    // if (onClickConmentCallBack != null) {
    // onClickConmentCallBack.callBack(widget, commentPojo);
    // }
    //
    // }
    //
    // @Override
    // public void updateDrawState(TextPaint tp) {
    // tp.setColor(AppContext.getContext().getResources()
    // .getColor(R.color.grey_fong_2));
    // }
    //
    // };
    // spans.setSpan(clickSpan, start - 1, spans.length(),
    // Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    // }

    // private static String getTvNameContent(UserPojo user1, UserPojo user2) {
    // StringBuilder sb = new StringBuilder();
    // sb.append(user1.username).append("回复").append(user2.username)
    // .append(":");
    // return sb.toString();
    // }

    // ---------------------------------------Photo Activity Comment
    // Spans-----------------------------------------------------------//

    public static void setCommentSpan(final Activity activity,
	    final TextView tv, final OnClickConmentCallBack callBack) {

	// if (commentPojo.replyUserId > 0) {
	// setReplyCommentSpan(activity, tv, commentPojo, callBack);
	// return;
	// }
	//
	// SpannableString text = new SpannableString(
	// commentPojo.userWhoCommented.username + ": "
	// + commentPojo.content);
	//
	// int nameSize = commentPojo.userWhoCommented.username.length();

	ClickableSpan clickableSpan = new ClickableSpan() {
	    @Override
	    public void onClick(View view) {
		// IntentUtils.openFriendCard(activity,
		// commentPojo.userWhoCommented);
	    }

	    @Override
	    public void updateDrawState(TextPaint ds) {
		// TODO Auto-generated method stub
		// ds.setColor(AppContext.getContext().getResources()
		// .getColor(R.color.blue));

	    }
	};

	// ClickableSpan fullClick = new ClickableSpan() {
	// @Override
	// public void onClick(View view) {
	//
	// callBack.callBack(tv, commentPojo);
	// }
	//
	// @Override
	// public void updateDrawState(TextPaint ds) {
	// // TODO Auto-generated method stub
	// ds.setColor(AppContext.getContext().getResources()
	// .getColor(R.color.normal_text_color));
	//
	// }
	// };

	// text.setSpan(fullClick, nameSize, text.length(), 0);
	// text.setSpan(clickableSpan, 0, nameSize, 0);
	// /*
	// * text.setSpan(new ForegroundColorSpan(AppContext.getContext()
	// * .getResources().getColor(R.color.blue)), 0, nameSize, 0);
	// */
	//
	// tv.setMovementMethod(LinkMovementMethod.getInstance());
	// tv.setHighlightColor(Color.TRANSPARENT);
	// tv.setText(text, BufferType.SPANNABLE);

    }

    // public static void setReplyCommentSpan(final Activity activity,
    // final TextView tv, final CommentPojo commentPojo,
    // final OnClickConmentCallBack callBack) {
    //
    // String middleText = AppContext.getContext().getResources()
    // .getString(R.string.replied_to);
    //
    // SpannableString text = new SpannableString(
    // commentPojo.userWhoCommented.username + " " + middleText + " "
    // + commentPojo.replyUsername + ": "
    // + commentPojo.content);
    //
    // int replyUserNameSize = commentPojo.userWhoCommented.username.length();
    // int originalUserNameSize = commentPojo.replyUsername.length();
    //
    // ClickableSpan replyUserClick = new ClickableSpan() {
    // @Override
    // public void onClick(View view) {
    //
    // IntentUtils.openFriendCard(activity,
    // commentPojo.userWhoCommented);
    // }
    //
    // @Override
    // public void updateDrawState(TextPaint ds) {
    // // TODO Auto-generated method stub
    // ds.setColor(AppContext.getContext().getResources()
    // .getColor(R.color.blue));
    //
    // }
    // };
    //
    // ClickableSpan originalUserClick = new ClickableSpan() {
    // @Override
    // public void onClick(View view) {
    // UserPojo userPojo = new UserPojo();
    // userPojo.userId = commentPojo.replyUserId;
    // userPojo.username = commentPojo.replyUsername;
    // IntentUtils.openFriendCard(activity, userPojo);
    // }
    //
    // @Override
    // public void updateDrawState(TextPaint ds) {
    // // TODO Auto-generated method stub
    // ds.setColor(AppContext.getContext().getResources()
    // .getColor(R.color.blue));
    //
    // }
    // };
    //
    // ClickableSpan fullClick = new ClickableSpan() {
    // @Override
    // public void onClick(View view) {
    //
    // callBack.callBack(tv, commentPojo);
    // }
    //
    // @Override
    // public void updateDrawState(TextPaint ds) {
    // // TODO Auto-generated method stub
    // ds.setColor(AppContext.getContext().getResources()
    // .getColor(R.color.normal_text_color));
    //
    // }
    // };
    //
    // text.setSpan(fullClick, replyUserNameSize + middleText.length()
    // + originalUserNameSize + 2, text.length(), 0);
    //
    // // Reply user
    // text.setSpan(replyUserClick, 0, replyUserNameSize, 0);
    // /*
    // * text.setSpan(new ForegroundColorSpan(AppContext.getContext()
    // * .getResources().getColor(R.color.blue)), 0, replyUserNameSize, 0);
    // */
    //
    // // Original comment user
    // text.setSpan(originalUserClick, replyUserNameSize + middleText.length()
    // + 2, replyUserNameSize + middleText.length()
    // + originalUserNameSize + 2, 0);
    // /*
    // * text.setSpan(new ForegroundColorSpan(AppContext.getContext()
    // * .getResources().getColor(R.color.blue)), 0, originalUserNameSize, 0);
    // */
    //
    // tv.setMovementMethod(LinkMovementMethod.getInstance());
    // tv.setHighlightColor(Color.TRANSPARENT);
    // tv.setText(text, TextView.BufferType.SPANNABLE);
    // }

}
