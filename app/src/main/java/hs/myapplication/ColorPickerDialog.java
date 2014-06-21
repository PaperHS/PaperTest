package hs.myapplication;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SVBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import otto.BusProvider;
import otto.ColorEvent;

/**
 * Created by Peggy on 2014/6/21.
 */
public class ColorPickerDialog {

	private View mParent;		//father View
	private Context mContext;
	private PopupWindow mPopWin;
	private LinearLayout mRootLayout;
	private ViewGroup.LayoutParams mLayoutParams;
//	private ColorPicker mPicker;		//color picker main
//	private SVBar		mSvbar;			//light-dark bar
//	private Button		btnComfirm,btnCancel;
	@InjectView(R.id.colorpicker_picker) ColorPicker mPicker;
	@InjectView(R.id.colorpicker_svbar)  SVBar		 mSvbar;


	private ColorEvent mColorEvent = new ColorEvent();

	public ColorPickerDialog(Context context,View parent){
		this.mParent = parent;
		this.mContext = context;
		LayoutInflater mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		mRootLayout = (LinearLayout)mInflater.inflate(R.layout.dialog_colorpicker,null);
		mLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		ButterKnife.inject(this, mRootLayout);
		mPicker.addSVBar(mSvbar);
		mPicker.setShowOldCenterColor(false);
		mPicker.setOnColorSelectedListener(new ColorPicker.OnColorSelectedListener() {
			@Override
			public void onColorSelected(int i) {
				mColorEvent.setColor(i);
			}
		});

	}

	public void show(){
		if (mPopWin == null){
			mPopWin = new PopupWindow(mRootLayout,mLayoutParams.width,mLayoutParams.height);
			mPopWin.setFocusable(true);//get focus
		}

		mPopWin.showAtLocation(mParent, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
	}

	public void dismiss(){

		if (mPopWin != null){
			mPopWin.dismiss();
		}
	}

	public void setConfirmListen(View.OnClickListener listener){

	}

	@OnClick(R.id.colorpicker_cancel)
	public void clickCancel(){
		dismiss();
	}

	@OnClick(R.id.colorpicker_confirm)
	public void clickConfirm(){
		dismiss();
		BusProvider.getInstance().post(mColorEvent);
	}


}
