package com.splitshare;

import java.text.DecimalFormat;

import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateTransactionActivity extends ActionBarActivity {
	Button fromButton;
	Button toButton;
	EditText firstNameText;
	EditText lastNameText;
	EditText amountText;
	ImageButton minusButton;
	ImageButton addButton;
	EditText reasonText;
	Button createTransactionButton;
	
	int transactionType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_transaction);

		fromButton = (Button) findViewById(R.id.create_transaction_from_button);
		toButton = (Button) findViewById(R.id.create_transaction_to_button);
		firstNameText = (EditText) findViewById(R.id.create_transaction_first_name);
		lastNameText = (EditText) findViewById(R.id.create_transaction_last_name);
		amountText = (EditText) findViewById(R.id.create_transaction_amount);
		minusButton = (ImageButton) findViewById(R.id.create_transaction_minus_button);
		addButton = (ImageButton) findViewById(R.id.create_transaction_add_button);
		reasonText = (EditText) findViewById(R.id.create_transaction_reason);
		createTransactionButton = (Button) findViewById(R.id.create_transaction_button);
		
		minusButton.setColorFilter(R.color.grey);
		addButton.setColorFilter(R.color.grey);
		
		transactionType = StaticValues.TRANSACTION_OWED;
		
		createTransactionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createTransaction();
			}
		});
	}
	
	public void createTransaction() {
		String firstName = firstNameText.getText().toString();
		String lastName = lastNameText.getText().toString();
		String amountString = amountText.getText().toString();
		String reason = reasonText.getText().toString();
		
		if (firstName.isEmpty() || lastName.isEmpty() || amountString.isEmpty() || reason.isEmpty()) {
			Toast.makeText(this, "Make sure everything is filled out.", Toast.LENGTH_SHORT).show();	
		} else {
			if (transactionType == StaticValues.TRANSACTION_OWED) {
				DatabaseHelper dbHelper = new DatabaseHelper(this);
				PeopleModel user = dbHelper.getUser();
				PeopleModel person = new PeopleModel();
				FinanceModel finance = new FinanceModel();
				
				person.setFirstName(firstName);
				person.setLastName(lastName);
				person.setIsUser(StaticValues.ISNOTUSER);
				
				long id = dbHelper.insertPeople(person);
				
				Log.d("alton", "ID created: " + id);
				
				finance.setFromID((int) id);
				finance.setToID(user.getID());
				finance.setAmountOwed(Float.parseFloat(amountString));
				finance.setReason(reason);
				
				dbHelper.insertFinance(finance);
				
				setResult(RESULT_OK);
				finish();
			}
		}
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
	
	public void onTransactionClick(View v) {
		if (v == fromButton) {
			toButton.setBackground(getResources().getDrawable(R.drawable.button_niagara));
			toButton.setTextColor(getResources().getColor(R.drawable.text_button_niagara));
			fromButton.setBackground(getResources().getDrawable(R.drawable.button_white));
			fromButton.setTextColor(getResources().getColor(R.drawable.text_button_white));
			
			transactionType = StaticValues.TRANSACTION_OWED;
		} else if (v == toButton) {
			fromButton.setBackground(getResources().getDrawable(R.drawable.button_niagara));
			fromButton.setTextColor(getResources().getColor(R.drawable.text_button_niagara));
			toButton.setBackground(getResources().getDrawable(R.drawable.button_white));
			toButton.setTextColor(getResources().getColor(R.drawable.text_button_white));
			
			transactionType = StaticValues.TRANSACTION_OWING;
		}
	}
}
