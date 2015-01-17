package com.splitshare;

import java.text.DecimalFormat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;

public class CreateTransactionActivity extends Activity implements OnTouchListener {
	Button fromButton;
	Button toButton;
	Button createButton;
	EditText firstNameText;
	EditText lastNameText;
	EditText amountText;
	EditText reasonText;
	ImageButton minusButton;
	ImageButton addButton;
	
	private static final int OPERATION_MINUS = 0;
	private static final int OPERATION_ADD = 1;
	int transactionType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_transaction);

		fromButton = (Button) findViewById(R.id.create_transaction_from_button);
		toButton = (Button) findViewById(R.id.create_transaction_to_button);
		createButton = (Button) findViewById(R.id.create_transaction_button);
		firstNameText = (EditText) findViewById(R.id.create_transaction_first_name);
		lastNameText = (EditText) findViewById(R.id.create_transaction_last_name);
		amountText = (EditText) findViewById(R.id.create_transaction_amount);
		reasonText = (EditText) findViewById(R.id.create_transaction_reason);
		minusButton = (ImageButton) findViewById(R.id.create_transaction_minus_button);
		addButton = (ImageButton) findViewById(R.id.create_transaction_add_button);
		
		transactionType = StaticValues.TRANSACTION_OWED;
		
		fromButton.setSelected(true);
		toButton.setSelected(false);
		
		fromButton.setOnTouchListener(this);
		toButton.setOnTouchListener(this);
		createButton.setOnTouchListener(this);
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

	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v == fromButton) {
			fromButton.setSelected(true);
			toButton.setSelected(false);
			transactionType = StaticValues.TRANSACTION_OWED;
		} else if (v == toButton) {
			fromButton.setSelected(false);
			toButton.setSelected(true);
			transactionType = StaticValues.TRANSACTION_OWING;
		} else if (v == minusButton) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				minusButton.setImageResource(R.drawable.ic_minus_ssniagara);
				changeAmount(OPERATION_MINUS);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				minusButton.setImageResource(R.drawable.ic_minus_ssgallery);
			}
		} else if (v == addButton) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				addButton.setImageResource(R.drawable.ic_add_ssniagara);
				changeAmount(OPERATION_ADD);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				addButton.setImageResource(R.drawable.ic_add_ssgallery);
			}
		} else if (v == createButton) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				DatabaseHelper dbHelper = new DatabaseHelper(this);
				PeopleModel user = dbHelper.getUser();
				PeopleModel person;
				FinanceModel finance;
				
				String firstName = firstNameText.getText().toString();
				String lastName = lastNameText.getText().toString();
				String amountString = amountText.getText().toString();
				String reason = reasonText.getText().toString();
				
				if (!firstName.isEmpty() && !lastName.isEmpty() && !amountString.isEmpty() && !reason.isEmpty()) {
					person = new PeopleModel(firstName, lastName, StaticValues.ISNOTUSER);
					int personID = person.getID();
					
					if (personID != -1) {
						int id = (int) dbHelper.insertPeople(person);
						person = dbHelper.getPeople(id);
					} else {
						person = dbHelper.getPeople(personID);
					}
					
					if (transactionType == StaticValues.TRANSACTION_OWED) {
						finance = new FinanceModel(Float.parseFloat(amountString), reason, person.getID(), user.getID());
						dbHelper.insertFinance(finance);
					} else if (transactionType == StaticValues.TRANSACTION_OWING) {
						finance = new FinanceModel(Float.parseFloat(amountString), reason, user.getID(), person.getID());
						dbHelper.insertFinance(finance);
					}
				}
				
				setResult(RESULT_OK);
				finish();
			}
		}
		
		return false;
	}
}
