package com.splitshare;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;
import com.splitshare.fragment.TransactionFragment;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences sharedPrefs = getSharedPreferences(
				StaticValues.SHARED_PREFS, Context.MODE_PRIVATE);
		if (!sharedPrefs.getBoolean(StaticValues.USER_CREATED_KEY, false)) {
			Intent newUserIntent = new Intent(this, CreateUserActivity.class);
			startActivityForResult(newUserIntent, StaticValues.INTENT_CREATE_USER_REQUEST);
		} else {
			setContentView(R.layout.activity_main);
			buildUI();
		}
	}
	
	private void buildUI() {
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		TextView userInfoText = (TextView) findViewById(R.id.main_user_info);
		
		String userInfo = createUserProfile(dbHelper);
		
		// Display results
		userInfoText.setText(userInfo);
		createTransactionContainer();
	}
	
	private String createUserProfile(DatabaseHelper dbHelper) {
		PeopleModel user = dbHelper.getUser();
		List<FinanceModel> financeFromUser = dbHelper.getFinancesFromID(user.getID());
		List<FinanceModel> financeToUser = dbHelper.getFinancesToID(user.getID());
		DecimalFormat df = new DecimalFormat("0.00"); 
		double totalBalance = 0.00d;
		
		// Do calculations
		for (int i = 0; i < financeFromUser.size(); i ++) {
			totalBalance -= financeFromUser.get(i).getAmountOwed();
		}
		
		for (int i = 0; i < financeToUser.size(); i ++) {
			totalBalance += financeToUser.get(i).getAmountOwed();
		}
		
		return user.getFirstName() + " " + user.getLastName() + ": $" + df.format(totalBalance);
	}
	
	private void createTransactionContainer() {
		TransactionFragment owedFragment = TransactionFragment
                .newInstance(StaticValues.TRANSACTION_OWED);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_owed, owedFragment).commit();
        
        TransactionFragment owingFragment = TransactionFragment
        		.newInstance(StaticValues.TRANSACTION_OWING);
        getSupportFragmentManager().beginTransaction()
        		.add(R.id.fragment_owing, owingFragment).commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == StaticValues.INTENT_CREATE_USER_REQUEST) {
	        if (resultCode == RESULT_OK) {
	        	setContentView(R.layout.activity_main);
	        	
	        	// Need to put a delay to avoid calling Fragment right after checking.
	        	// Android bug for KitKat 4.4
	        	new Thread(new Runnable() {
					@Override
					public void run() {
			        	buildUI();
					}
	        	}).start();
	        }
	    }
	}
	
	// UI Button Click Actions
	public void onAddClick(View v) {
		Intent createTransactionIntent = new Intent(this, CreateTransactionActivity.class);
		startActivity(createTransactionIntent);
	}
}