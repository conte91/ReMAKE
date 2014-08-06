package com.example.remake;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendKeysActivity extends Activity {

	EditText edit;
	Button ok, cancel;
	Spammer s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		s=TouchActivity.theSpammer();
		setContentView(R.layout.sendkeys);
		edit=(EditText) findViewById(R.id.textToSend);
		ok=(Button) findViewById(R.id.okSendKeys);
		cancel=(Button) findViewById(R.id.cancelSendKeys);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				edit.setText("");
			}
			
		});
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				s.sendText(edit.getText().toString());
				edit.setText("");
			}
		});
	}
}
