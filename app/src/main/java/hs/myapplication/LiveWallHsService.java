package hs.myapplication;

import android.app.Service;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class LiveWallHsService extends WallpaperService {

	private final Handler mHandler = new Handler();
	public LiveWallHsService() {
    }

	@Override
	public Engine onCreateEngine() {
		return new myEngine();
	}

	private class myEngine extends Engine{


		private final Paint mPaint = new Paint();
		private long mStartTime;	//record time
		private boolean mVisible;	//reocrd if the desk visible
		private boolean mIfDraw;	//record if touch down
		private float mTouchX = -1;
		private float mTouchY = -1;

		private final Runnable mDrawCube = new Runnable() {
			public void run() {
				drawFrame();
			}
		};


		myEngine(){
			final Paint paint = mPaint;
			paint.setColor(0xffffffff);//画笔颜色
			paint.setAntiAlias(true);//抗锯齿
			paint.setStrokeWidth(2);//线条粗细
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStyle(Paint.Style.STROKE);
			mStartTime = SystemClock.elapsedRealtime();//when system up

		}
		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {

			super.onCreate(surfaceHolder);
			setTouchEventsEnabled(true);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mDrawCube);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mDrawCube);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
			} else {
				mHandler.removeCallbacks(mDrawCube);
			}

		}

		@Override
		public void onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN){
				mTouchX = event.getX();
				mTouchY = event.getY();
				mIfDraw = true;
			}else if (event.getAction() == MotionEvent.ACTION_UP){
				mIfDraw = false;
				mTouchX = -1;
				mTouchY = -1;
			}else if (event.getAction() == MotionEvent.ACTION_MOVE){
				if(Math.abs(mTouchX - event.getX())>3)
					mTouchX = event.getX();
				if(Math.abs(mTouchY - event.getY())>3)
					mTouchY = event.getY();
			}
			super.onTouchEvent(event);
		}

		public void drawFrame(){
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					if (mIfDraw){
						Paint paint = new Paint();
          				paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
          				c.drawPaint(paint);
          				paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
						drawLine(c, 0, mTouchY, c.getWidth(), mTouchY);
						drawLine(c,mTouchX,0,mTouchX,c.getHeight());
					}else{
						//must clear canvas !! if not ,will be blink
						Paint paint = new Paint();
						paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
						c.drawPaint(paint);
						paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
					}

				}
			}finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}
			mHandler.removeCallbacks(mDrawCube);
			if (mVisible) {
				mHandler.postDelayed(mDrawCube, 1000 / 25);
			}
		}

		public void drawLine(Canvas canvas,float x1, float y1, float x2, float y2){
			canvas.drawLine(x1,y1,x2,y2,mPaint);
		}
	}


}
