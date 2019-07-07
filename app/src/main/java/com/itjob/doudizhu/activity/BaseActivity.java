package com.itjob.doudizhu.activity;


import com.itjob.doudizhu.app.MainApplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * Activity基类,所有窗口公共行为集中。
 * @author Administrator
 *
 */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainApplication.getInstance().addActivity(this);
		initView();
		initListener();
		initData();
	}

	protected abstract void initListener();

	protected abstract void initView();

	protected abstract void initData();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainApplication.getInstance().removeActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		MainApplication.getInstance().play("cancel.mp3");
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("当前Activity销毁");
		finish();//当前Activity销毁
	}

	
	
}
