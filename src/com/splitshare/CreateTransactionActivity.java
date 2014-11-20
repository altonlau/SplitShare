package com.splitshare;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class CreateTransactionActivity extends ActionBarActivity {
	EditText firstName;
	EditText lastName;
	EditText amountText;
	ImageButton minusButton;
	ImageButton addButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_transaction);

		firstName = (EditText) findViewById(R.id.create_transaction_first_name);
		lastName = (EditText) findViewById(R.id.create_transaction_last_name);
		amountText = (EditText) findViewById(R.id.create_transaction_amount);
		minusButton = (ImageButton) findViewById(R.id.create_transaction_minus_button);
		addButton = (ImageButton) findViewById(R.id.create_transaction_add_button);
		
		minusButton.setColorFilter(R.color.grey);
		addButton.setColorFilter(R.color.grey);
	}
	
	public void onMinusClick(View v) {
		DecimalFormat df = new DecimalFormat("###0.00"); 
		double amount = 0.00d;
		
		if (!amountText.getText().toString().isEmpty()) {
			amount = Double.parseDouble(amountText.getText().toString());
		}
		
		if (amount > 0) {
			amount --;
			
			if (amount < 0) {
				amount = 0.00d;
			}
		}
		
		amountText.setText(df.format(amount));
	}
	
	public void onAddClick(View v) {
		DecimalFormat df = new DecimalFormat("###0.00"); 
		double amount = 0.00d;
		
		if (!amountText.getText().toString().isEmpty()) {
			amount = Double.parseDouble(amountText.getText().toString());
		}
		
		if (amount < 10000) {
			amount ++;
			
			if (amount > 10000) {
				amount = 9999.99d;
			}
		}
		
		amountText.setText(df.format(amount));
	}
}
