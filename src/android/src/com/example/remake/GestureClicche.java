package com.example.remake;

import android.content.Context;
import android.view.GestureDetector;

public class GestureClicche extends GestureDetector {
	
	CliccheListener o;
	public GestureClicche(Context c, CliccheListener o) {
		super(c, o);
		this.o=o;
	}
	public int getNClick(){
		return o.getNClick();
	}
	
}