package com.keernuo.preprocessor.view;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.keernuo.preprocessor.R;

/**
 * Created by Fioman on 2016/12/21
 * Description：可以用于加载页面，但是我这里只有一个线程，要扩展的话可以在setDoInBackground（）方法中多写几个参数多new几个DoInBTask对象
 * 暂时是只想到这种方法，否则的话就要全部重写（线程较多的话还是不建议使用我这种方式，不过原理上也可以借鉴），应该能使用在大部分加载的地方，
 * 加载的图片什么的可以自己再自定义，主要修改showDialog方法中的代码
 */
public class WaitDialog {

	private Context context;
	AlertDialog dialog;
	private boolean touchcancelable = false;
	private backTask bt;
	private DoInBTask dbt;
	private AnimationDrawable animationDrawable;
	
	public WaitDialog(Context con){
		context = con;
	}
	
	public WaitDialog showDialog(String mess){
		dialog = new AlertDialog.Builder(context).create();
		Window mWindow = dialog.getWindow();
		mWindow.setGravity(Gravity.CENTER);
		dialog.show();
		dialog.setCancelable(touchcancelable);
		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface arg0) {
				if(dbt!=null&&dbt.getStatus()!=AsyncTask.Status.FINISHED)
					dbt.cancel(true);
			}
		});
		
		//Window window = dialog.getWindow();
		mWindow.setContentView(R.layout.doinb_dialog);
		TextView v = (TextView) mWindow.findViewById(R.id.d_tip);
		ImageView imageView = (ImageView) mWindow.findViewById(R.id.iv_wait_anim);
		imageView.setImageResource(R.drawable.prepross);
		animationDrawable = (AnimationDrawable) imageView.getDrawable();
		animationDrawable.start();
		v.setText(mess);
		return this;
	}
	
	public void setTouchcancelable(boolean touchcancelable) {
		this.touchcancelable = touchcancelable;
		dialog.setCancelable(touchcancelable);
	}
	
	public boolean isTouchcancelable() {
		return touchcancelable;
	}
	
	public WaitDialog setDoInBackground(backTask task){
		bt = task;
		dbt = new DoInBTask();
		dbt.execute();
		return this;
	}
	
	public interface backTask{
		public boolean doInBackground();
		public void taskEnd(boolean ret);
	}
	
	public class DoInBTask extends AsyncTask<Void,Void,Boolean>{

		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			Log.d("sqq", "还在运行");
			if(bt.doInBackground()){
				return true;
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			Log.d("sqq", "要结束了");
			if(dialog!=null){
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
			bt.taskEnd(result);
			super.onPostExecute(result);
		}
		
		@Override
		protected void onCancelled() {
			Log.d("sqq", "取消了");
			super.onCancelled();
		}
	}
}
