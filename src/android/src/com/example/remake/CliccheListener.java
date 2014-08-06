package com.example.remake;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class CliccheListener extends GestureDetector.SimpleOnGestureListener {

	int nClick;

	public CliccheListener() {
		super();
	}

	public int getNClick() {
		return nClick;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		nClick = 1;
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		nClick = 2;
		return true;
	}
}
