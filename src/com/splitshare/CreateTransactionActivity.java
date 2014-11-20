package com.splitshare;

import java.text.DecimalFormat;

import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class CreateTransactionActivity extends ActionBarActivity implements OnTouchListener {
	EditText firstName;
	EditText lastName;
	EditText amountText;
	ImageButton minusButton;
	ImageButton addButton;
	
	private static final int OPERATION_MINUS = 0;
	private static final int OPERATION_ADD = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_transaction);

		firstName = (EditText) findViewById(R.id.create_transaction_first_name);
		lastName = (EditText) findViewById(R.id.create_transaction_last_name);
		amountText = (EditText) findViewById(R.id.create_transaction_amount);
		minusButton = (ImageButton) findViewById(R.id.create_transaction_minus_button);
		addButton = (ImageButton) findViewById(R.id.create_transaction_add_button);
		
		minusButton.setColorFilter(R.color.ssgallery, Mode.SRC_ATOP);
		addButton.setColorFilter(R.color.ssgallery, Mode.SRC_ATOP);
		
		minusButton.setOnTouchListener(this);
		addButton.setOnTouchListener(this);
	}
	
	public void changeAmount(int operation) {
		DecimalFormat df = new DecimalFormat("###0.00"); 
		double amount = 0.00d;
		
		if (!amountText.getText().toString().isEmpty()) {
			amount = Double.parseDouble(amountText.getText().toString());
		}
		
		if (operation == OPERATION_MINUS) {
			if (amount > 0) {
				amount --;
				
				if (amount < 0) {
					amount = 0.00d;
				}
			}
		} else if (operation == OPERATION_ADD) {
			if (amount < 10000) {
				amount ++;
				
				if (amount > 10000) {
					amount = 9999.99d;
				}
			}
		}
		
		amountText.setText(df.format(amount));
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v == minusButton) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				minusButton.setImageResource(R.drawable.ic_minus_ssniagara);
				changeAmount(OPERATION_MINUS);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				minusButton.setImageResource(R.drawable.ic_minus);
			}
		} else if (v == addButton) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				addButton.setImageResource(R.drawable.ic_add_ssniagara);
				changeAmount(OPERATION_ADD);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				addButton.setImageResource(R.drawable.ic_add);
			}
		}
		
		return false;
	}
}
