package hs.myapplication;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import otto.BusProvider;
import otto.ColorEvent;
import utils.Misc;
import widget.CircleButton;

public class SetWallpaper extends Activity {

	private static final String TAG = "HSTAG";
    private WallpaperManager mWallpaperManager;
	private int mColorStart=-1230848;
	private int mColorEnd = -14114049;
    @InjectView(R.id.img_wall)
    ImageView mImg;
    @InjectView(R.id.btn_chooseColorEnd)
    CircleButton mBtnColorEnd;
    @InjectView(R.id.btn_chooseColorStart)
    CircleButton mBtnColorStart;
	private ColorPickerDialog mColorPickerDialog;
	private boolean isStart;
	private Bus mBus;
    private int mCurrentGradient;
    public static final int LINEAR = 0;
    public static final int RADIAL = 1;
    public static final int SWEEP = 2;
    private boolean mIsFirstLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);
        ButterKnife.inject(this);
        mWallpaperManager = WallpaperManager.getInstance(this);
		mColorPickerDialog = new ColorPickerDialog(this,mImg.getRootView());
    	//init otto
		mBus = BusProvider.getInstance();
		mBus.register(this);
        mBtnColorStart.setColor(mColorStart);
        mBtnColorEnd.setColor(mColorEnd);
        mIsFirstLoad = true;
	}

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mIsFirstLoad){
            mIsFirstLoad = false;
            setColorLinear();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_wallpaper, menu);
        return true;
    }

	@Override
	protected void onDestroy() {
		mBus.unregister(this);
		super.onDestroy();
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.btn_setAswallpaper)
    public void setAswallpaper(){

        try{
			mWallpaperManager.suggestDesiredDimensions(Misc.getScreenWidth(this),Misc.getScreenHeight(this));
        	mWallpaperManager.setBitmap(mImg.getDrawingCache());
		 	mImg.setDrawingCacheEnabled(false);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

	@OnClick(R.id.btn_linear)
	public void setColorLinear(){
		Bitmap.Config config =  Bitmap.Config.ARGB_8888;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(Misc.getScreenWidth(this),Misc.getScreenHeight(this)-Misc.getStatusBarHeight(this),config);
		Canvas cv = new Canvas(bitmap);
		Paint p=new Paint();
//        RadialGradient lg  = new RadialGradient(Misc.getScreenWidth(this)/2,Misc.getScreenHeight(this)/2,400,mColorStart,mColorEnd, Shader.TileMode.CLAMP);
		LinearGradient lg=new LinearGradient(0.0f,0.0f,0f,1080f,mColorStart,mColorEnd, Shader.TileMode.CLAMP);
//        SweepGradient lg = new SweepGradient(Misc.getScreenWidth(this)+1f,Misc.getScreenHeight(this)/2f,mColorStart,mColorEnd);
        p.setShader(lg);
		cv.drawPaint(p);
        mImg.setDrawingCacheEnabled(false);
		mImg.setDrawingCacheEnabled(true);
		mImg.setImageBitmap(bitmap);
        mCurrentGradient = LINEAR;

	}
	@OnClick(R.id.btn_radial)
	public void setColorRadial(){
		Bitmap.Config config =  Bitmap.Config.ARGB_8888;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(Misc.getScreenWidth(this),Misc.getScreenHeight(this)-Misc.getStatusBarHeight(this),config);
		Canvas cv = new Canvas(bitmap);
		Paint p=new Paint();
        RadialGradient lg  = new RadialGradient(Misc.getScreenWidth(this)/2,Misc.getScreenHeight(this)/2,400,mColorStart,mColorEnd, Shader.TileMode.CLAMP);
//		LinearGradient lg=new LinearGradient(0.0f,0.0f,0f,1080f,mColorStart,mColorEnd, Shader.TileMode.CLAMP);
//        SweepGradient lg = new SweepGradient(Misc.getScreenWidth(this)+1f,Misc.getScreenHeight(this)/2f,mColorStart,mColorEnd);
        p.setShader(lg);
		cv.drawPaint(p);
        mImg.setDrawingCacheEnabled(false);
		mImg.setDrawingCacheEnabled(true);
		mImg.setImageBitmap(bitmap);
        mCurrentGradient = RADIAL;
	}
	@OnClick(R.id.btn_sweep)
	public void setColorSweep(){
		Bitmap.Config config =  Bitmap.Config.ARGB_8888;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(Misc.getScreenWidth(this),Misc.getScreenHeight(this),config);
		Canvas cv = new Canvas(bitmap);
		Paint p=new Paint();
//        RadialGradient lg  = new RadialGradient(Misc.getScreenWidth(this)/2,Misc.getScreenHeight(this)/2,400,mColorStart,mColorEnd, Shader.TileMode.CLAMP);
//		LinearGradient lg=new LinearGradient(0.0f,0.0f,0f,1080f,mColorStart,mColorEnd, Shader.TileMode.CLAMP);
        SweepGradient lg = new SweepGradient(Misc.getScreenWidth(this)+1f,Misc.getScreenHeight(this)/2f,mColorStart,mColorEnd);
        p.setShader(lg);
		cv.drawPaint(p);
        mImg.setDrawingCacheEnabled(false);
		mImg.setDrawingCacheEnabled(true);
		mImg.setImageBitmap(bitmap);
        mCurrentGradient = SWEEP;
	}



//	@OnClick(R.id.btn_setColor)
//	public void setColor(){
//		Log.d(TAG,"btn_setColor");
//		ValueAnimator colorAnim = ObjectAnimator.ofInt(mImg, "backgroundColor", /*Red*/mColorStart, /*Blue*/mColorEnd);
//		colorAnim.setDuration(3000);
//		colorAnim.setEvaluator(new ArgbEvaluator());
//		colorAnim.setRepeatCount(ValueAnimator.INFINITE);
//		colorAnim.setRepeatMode(ValueAnimator.REVERSE);
//		colorAnim.start();
//	}

//    @OnClick(R.id.btn_choose_pic)
//    public void choosePic(){
//        Intent intentPick = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intentPick, 1);
//    }

	@OnClick(R.id.btn_chooseColorStart)
	public void chooseColorStart(){
		isStart = true;
		mColorPickerDialog.show();
	}
	@OnClick(R.id.btn_chooseColorEnd)
	public void btn_chooseColorEnd(){
		isStart = false;
		mColorPickerDialog.show();
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imgUri = null;
        if (resultCode == RESULT_OK){
			if (requestCode == 1) {
				imgUri = data.getData();
				mImg.setDrawingCacheEnabled(true);
				mImg.setImageURI(imgUri);
			}else if(requestCode == 2){
				mColorStart = data.getExtras().getInt("color");
				Log.d(TAG,"mColorStart:"+mColorStart);
			}else
				mColorEnd = data.getExtras().getInt("color");

        }
    }

	public static Bitmap drawableToBitmap(Drawable drawable) {
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}

	@Subscribe
	public void getColor(ColorEvent event){
		if (isStart){
			mColorStart = event.getColor();
            mBtnColorStart.setColor(event.getColor());
		}else{
			mColorEnd = event.getColor();
            mBtnColorEnd.setColor(event.getColor());
		}
        switch(mCurrentGradient){

            case RADIAL:
                setColorRadial();
                break;
            case SWEEP:
                setColorSweep();
                break;
            default:
                setColorLinear();
                break;
        }
	}


}
