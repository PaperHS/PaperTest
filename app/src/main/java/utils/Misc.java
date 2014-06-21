package utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * This class contains some miscellaneous utils.
 * 
 * @author Vincent
 * @since  Ver. 0.1.0, May. 30, 2014
 */
public class Misc
{
	/**
	 * Get the width of current screen.
	 * 
	 * @param  contex the contex
	 * @return the width
	 */
	public static int getScreenWidth(Context contex) {
		DisplayMetrics dm = contex.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}
	
	/**
	 * Get the height of current screen.
	 * 
	 * @param  contex the context
	 * @return the height
	 */
	public static int getScreenHeight(Context contex) {
		DisplayMetrics dm = contex.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}
	
	/**
	 * Get the height of status bar.
	 * 
	 * @param activity
	 * @return
	 */
	public static int getStatusBarHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		
		return statusBarHeight;
	}
}
