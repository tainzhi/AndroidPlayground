package com.tainzhi.sample.api;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by muqing on 2019-08-15.
 * Email: qfq61@qq.com
 */
public class Util {
	public static class Dimens {
		public static float dpToPx(Context context, float dp) {
			return dp * context.getResources().getDisplayMetrics().density;
		}
		
		public static float pxToDp(Context context, float px) {
			return px / context.getResources().getDisplayMetrics().density;
		}
		
		public static int dpToPxInt(Context context, float dp) {
			return (int) (dpToPx(context, dp) + 0.5f);
		}
		
		public static int pxToDpCeilInt(Context context, float px) {
			return (int) (pxToDp(context, px) + 0.5f);
		}
	}
	
	public static class Display {
		public static int getScreenWidth(Activity context) {
			DisplayMetrics dm = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
			return dm.widthPixels;
		}
		
		public static int getScreenHeight(Activity context) {
			DisplayMetrics dm = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
			return dm.heightPixels;
		}
	}
}
