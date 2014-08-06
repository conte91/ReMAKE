package com.example.remake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.RelativeLayout;

public class TouchActivity extends Activity {

	/** Called when the activity is first created. */
	private ImageView img = null;
	private RelativeLayout aLayout;
	private static Spammer s;
	private int status = 0;
	private int myWidth, myHeight;
	private int nClick[];
	private ImageView leftC, centerC, rightC;
	private GestureClicche mGestureDetector[];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_layout);

		aLayout = (RelativeLayout) findViewById(R.id.a_layout);

		DisplayMetrics metrics = aLayout.getResources().getDisplayMetrics();
		myWidth = metrics.widthPixels;
		myHeight = metrics.heightPixels;

		s = new Spammer();

		img = (ImageView) findViewById(R.id.imageView);
		leftC = (ImageView) findViewById(R.id.leftMouse);
		centerC = (ImageView) findViewById(R.id.centerMouse);
		rightC = (ImageView) findViewById(R.id.rightMouse);

		// sa.setFillAfter(true);

		img.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				status = 1;
				s.enable(true);
				return false;
			}
		});

		mGestureDetector = new GestureClicche[3];
		for (int i = 0; i < 3; ++i) {
			mGestureDetector[i] = new GestureClicche(this,
					new CliccheListener());
		}

		OnTouchListener theClickListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				/** We had a tap */
				if (v == leftC) {
					if (mGestureDetector[0].onTouchEvent(event)) {
						s.click(1, mGestureDetector[0].getNClick());
					}
				} else if (v == centerC) {
					if (mGestureDetector[1].onTouchEvent(event)) {
						s.click(2, mGestureDetector[1].getNClick());
					}
				} else if (v == rightC) {
					if (mGestureDetector[2].onTouchEvent(event)) {
						s.click(3, mGestureDetector[2].getNClick());
					}
				}
				return true;
			}
		};
		leftC.setOnTouchListener(theClickListener);
		centerC.setOnTouchListener(theClickListener);
		rightC.setOnTouchListener(theClickListener);

		aLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int realX, realY;
				if (status == 1) // any event from down and move
				{
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						realX = (int) event.getX() - myWidth / 2;
						realY = (int) event.getY() - myHeight / 2;
						s.setSpeed((realX > 0 ? 1 : -1) * realX * realX / 100,
								(realY > 0 ? 1 : -1) * realY * realY / 100);
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
								img.getLayoutParams());
						params.leftMargin = (int) event.getX()
								- (params.width / 2);
						params.topMargin = (int) event.getY()
								- (params.height / 2);
						img.setLayoutParams(params);
						aLayout.invalidate();
					}
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					status = 0;
					s.enable(false);
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
							img.getLayoutParams());
					params.addRule(RelativeLayout.CENTER_IN_PARENT);
					img.setLayoutParams(params);
					aLayout.invalidate();
				}
				return true;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.a_layout_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.switch_to_keyboard:
	        	Intent intent = new Intent(this, SendKeysActivity.class);
	        	startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public static Spammer theSpammer(){
		return s;
	}

}
