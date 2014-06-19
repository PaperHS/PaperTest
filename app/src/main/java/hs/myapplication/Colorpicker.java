package hs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hs.myapplication.R;

public class Colorpicker extends Activity implements ColorPicker.OnColorChangedListener,
		ColorPicker.OnColorSelectedListener{

	private static final String TAG = "HSTAG";
	@InjectView(R.id.picker)
	ColorPicker picker;
	@InjectView(R.id.svbar)
	SVBar svBar;
	@InjectView(R.id.opacitybar)
	OpacityBar opacityBar;
	@InjectView(R.id.saturationbar)
	SaturationBar saturationBar;
	@InjectView(R.id.valuebar)
	ValueBar valueBar;
	private int mChosenColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorpicker);
		ButterKnife.inject(this);
		picker.addSVBar(svBar);
		picker.addOpacityBar(opacityBar);
		picker.addSaturationBar(saturationBar);
		picker.addValueBar(valueBar);
		picker.setOnColorChangedListener(this);
		picker.setShowOldCenterColor(false);
		picker.setOnColorSelectedListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.colorpicker, menu);
        return true;
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

	@Override
	public void onColorChanged(int i) {

	}


	@Override
	public void onColorSelected(int i) {
		Log.d(TAG,"color:"+i);
		mChosenColor = i;
	}


	@OnClick(R.id.btn_comfirm)
	public void comfirm(){
		Intent it = new Intent();
		it.putExtra("color",mChosenColor);
		this.setResult(RESULT_OK,it);
		this.finish();
	}
}
