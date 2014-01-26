package com.example.ixming_android_cache;

import org.ixming.android.cache.BitmapTransition;
import org.ixming.android.cache.BitmapUtils;
import org.ixming.android.cache.DefaultBitmapDisplayer;
import org.ixming.android.cache.PowerfulBitmapResizer;
import org.ixming.android.inject.InjectorUtils;
import org.ixming.android.inject.annotation.ViewInject;
import org.ixming.utils.NumberUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@ViewInject(id = R.id.iv)
	private ImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		SimpleMapPanel map = new SimpleMapPanel(this);
//		map.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		setContentView(R.layout.activity_main);
		
		InjectorUtils.defaultInstance().inject(this);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				new DefaultBitmapDisplayer().setBitmap(iv,
						BitmapUtils.fromResource(getApplicationContext(), R.drawable.pic2, null));
			}
		}, 3000L);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	/**
	 * 打印当前内存使用情况
	 */
	public static void memoryLog() {
		long freeMem = Runtime.getRuntime().freeMemory();
		long totalMem = Runtime.getRuntime().totalMemory();
		long maxMem = Runtime.getRuntime().maxMemory();
		Log.d("memoryLog", "memoryLog: freeMem = " + NumberUtils.formatByUnit(freeMem, 1024, 2, new String[]{ "B", "KB", "M" }));
		Log.d("memoryLog", "memoryLog: totalMem = " + NumberUtils.formatByUnit(totalMem, 1024, 2, new String[]{ "B", "KB", "M" }));
		Log.d("memoryLog", "memoryLog: maxMem = " + NumberUtils.formatByUnit(maxMem, 1024, 2, new String[]{ "B", "KB", "M" }));
	}
}
