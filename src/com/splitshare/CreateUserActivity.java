package com.splitshare;

import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.PeopleModel;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class CreateUserActivity extends Activity {
	private static final String TAG = "alton:CreateUser";
	
	EditText firstNameText, lastNameText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);
		
		firstNameText = (EditText) findViewById(R.id.create_first_name);
		lastNameText = (EditText) findViewById(R.id.create_last_name);
		Button createButton = (Button) findViewById(R.id.create_user_button);
		
		lastNameText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					createUser();
				}
				return false;
			}
		});
		createButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createUser();
			}
		});
	}

	protected void createUser() {
		String firstName = firstNameText.getText().toString();
		String lastName = lastNameText.getText().toString();
		
		if (firstName.isEmpty() || lastName.isEmpty()) {
			Toast.makeText(this, R.string.error_missing_name, Toast.LENGTH_SHORT).show();
		} else {
			DatabaseHelper dbHelper = new DatabaseHelper(this);
			PeopleModel person = new PeopleModel(firstName, lastName, StaticValues.ISUSER);
			SharedPreferences sharedPrefs = getSharedPreferences(
					StaticValues.SHARED_PREFS, Context.MODE_PRIVATE);
			
			Log.i(TAG, "First Name: " + person.getFirstName());
			Log.i(TAG, "Last Name: " + person.getLastName());
			Log.i(TAG, "Is User: " + person.getIsUser());
			
			dbHelper.insertPeople(person);
			sharedPrefs.edit().putBoolean(StaticValues.USER_CREATED_KEY, true).apply();
			
			setResult(RESULT_OK);
			finish();
		}
	}
}
