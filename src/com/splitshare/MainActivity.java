package com.splitshare;

import java.text.DecimalFormat;
import java.util.List;

import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == StaticValues.INTENT_CREATE_USER_REQUEST) {
	        if (resultCode == RESULT_OK) {
	        	setContentView(R.layout.activity_main);
	        	buildUI();
	        }
	    }
	}
}
