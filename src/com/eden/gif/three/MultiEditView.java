package com.eden.gif.three;

import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * 这个不好 
 * @author Administrator
 *
 */
public class MultiEditView extends EditText implements Runnable{
	 private boolean mRunning = true;  
	    private Vector<AnimationDrawable> drawables;  
	    private static final String zhengze = "\\[[^\\]]+\\]";  
	    private Pattern patten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);  
	    private Hashtable<Integer, AnimationDrawable> cache;  
	    private static final int SPEED = 100;  
	  
	    public MultiEditView(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	        // TODO Auto-generated constructor stub  
	        drawables = new Vector<AnimationDrawable>();  
	        cache = new Hashtable<Integer, AnimationDrawable>();  
	        new Thread(this).start();  
	    }  
	  
	    public void insertGif(int resID) {  
	        if (drawables.size() > 0)  
	            drawables.clear();  
	        StringBuffer buffer = new StringBuffer(getText());  
	        buffer.append("[");  
	        buffer.append(resID);  
	        buffer.append("]");  
	        SpannableString spannableString = new SpannableString(buffer);  
	        dealExpression(spannableString, patten, 0);  
	        setText(spannableString);  
	        setSelection(spannableString.length());  
	    }  
	    
	    private void dealExpression(SpannableString spannableString,  
	            Pattern patten, int start) {  
	        Matcher matcher = patten.matcher(spannableString);  
	        while (matcher.find()) {  
	            String key = matcher.group();  
	            if (matcher.start() < start) {  
	                continue;  
	            }  
	            int id = Integer.parseInt(key.substring(1, key.indexOf("]")));  
	            if (id != 0) {  
	                // long begin = System.currentTimeMillis();  
	                AnimationDrawable mSmile = null;  
	                if (cache.containsKey(id)) {  
	                    mSmile = cache.get(id);  
	                } else {  
	                    mSmile = new AnimationDrawable();  
	                    GifHelper gHelper = new GifHelper();  
	                    gHelper.read(getResources().openRawResource(id));  
	                    BitmapDrawable bd = new BitmapDrawable(gHelper.getImage());  
	                    mSmile.addFrame(bd, gHelper.getDelay(0));  
	                    for (int i = 1; i < gHelper.getFrameCount(); i++) {  
	                        mSmile.addFrame(  
	                                new BitmapDrawable(gHelper.nextBitmap()),  
	                                gHelper.getDelay(i));  
	                    }  
	                    mSmile.setBounds(0, 0, gHelper.getImage().getWidth(),  
	                            gHelper.getImage().getHeight());  
	                    bd.setBounds(0, 0, gHelper.getImage().getWidth(), gHelper  
	                            .getImage().getHeight());  
	                    cache.put(id, mSmile);  
	                }  
	                // long use = System.currentTimeMillis() - begin;  
	                // System.out.println("use:" + use + "/ms");  
	                ImageSpan span = new ImageSpan(mSmile, ImageSpan.ALIGN_BASELINE);  
	                int mstart = matcher.start();  
	                int end = mstart + key.length();  
	                spannableString.setSpan(span, mstart, end,  
	                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);  
	                if (!drawables.contains(mSmile))  
	                    drawables.add(mSmile);  
	            }  
	        }  
	    }  
	    
	    @Override  
	    public void run() {  
	        // TODO Auto-generated method stub  
	        while (mRunning) {  
	            if (super.hasWindowFocus()) {  
	                // System.out.println(drawables.size());  
	                for (int i = 0; i < drawables.size(); i++) {  
	                    AnimationDrawable drawable = drawables.get(i);  
	                    drawable.run();  
	                }  
	                postInvalidate();  
	            }  
	            sleep();  
	        }  
	    }  
	  
	    private void sleep() {  
	        try {  
	            Thread.sleep(SPEED);  
	        } catch (InterruptedException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public void destroy() {  
	        mRunning = false;  
	        drawables.clear();  
	        drawables = null;  
	    }  
	    
	    @Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        // TODO Auto-generated method stub  
	        if (event.getAction() == KeyEvent.ACTION_DOWN  
	                && keyCode == KeyEvent.KEYCODE_DEL) {  
	            // 删除  
	            String text = getText().toString();  
	            int offset = text.lastIndexOf("]");  
	            if (offset != -1 && offset == text.length() - 1) {  
	                if (drawables.size() > 0)  
	                    drawables.clear();  
	                StringBuffer buffer = new StringBuffer(text.substring(0,  
	                        text.lastIndexOf("[")));  
	                buffer.append(" ");// 加个空格防止删除最后一个]  
	                SpannableString spannableString = new SpannableString(buffer);  
	                dealExpression(spannableString, patten, 0);  
	                setText(spannableString);  
	                setSelection(spannableString.length());  
	            }  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    }  
}
