package com.splitshare.fragment;

import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.splitshare.StaticValues;
import com.splitshare.adapter.TransactionAdapter;
import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;

public class TransactionOwingFragment extends ListFragment {
	private List<FinanceModel> finances;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
		PeopleModel user = dbHelper.getUser();
		finances = dbHelper.getFinancesFromID(user.getID());
		
		setListAdapter(new TransactionAdapter(getActivity(), finances, StaticValues.TRANSACTION_OWING));
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// remove the dividers from the ListView of the ListFragment
		getListView().setDivider(null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something
		Toast.makeText(getActivity(), "Pos: " + position, Toast.LENGTH_SHORT).show();
	}
}
