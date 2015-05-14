package com.eden.annotation;

import java.lang.reflect.Field;

import com.eden.log.L;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	public static final String TAG = "My";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// 注解方式
	// @InjectView(id = R.id.textView1)
	// private TextView tv;
	/**
	 * 自动初始化加了InjectView注解的控件,在setContentView(id);方法后面调用
	 * 
	 */
	protected void initView() {
		// 得到Activity对应的Class
		Class<? extends BaseActivity> clazz = this.getClass();
		// 得到该Activity的所有字段
		Field[] fields = clazz.getDeclaredFields();
		// Log.v(TAG, "fields size-->" + fields.length);
		for (Field field : fields) {
			// 判断字段是否标注InjectView
			if (field.isAnnotationPresent(InjectView.class)) {
				// 如果标注了，就获得它的id
				InjectView inject = field.getAnnotation(InjectView.class);
				int id = inject.id();
				if (id > 0) {
					// 反射访问私有成员，必须加上这句
					field.setAccessible(true);
					// 然后对这个属性赋值
					try {
						field.set(this, this.findViewById(id));
					} catch (IllegalAccessException e) {
						L.e(TAG, "异常id--->" + id);
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						L.e(TAG, "异常id--->" + id);
						e.printStackTrace();
					}
				}
			}
		}
	}
}
