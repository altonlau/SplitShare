package com.splitshare.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.splitshare.R;
import com.splitshare.StaticValues;
import com.splitshare.adapter.TransactionAdapter;
import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;

public class TransactionOwedFragment extends Fragment {
	
	private TransactionAdapter adapter;
	private List<FinanceModel> finances;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_owed, container, false);
		
		TextView emptyText = (TextView) view.findViewById(R.id.no_transaction_text);
		ListView owedList = (ListView) view.findViewById(R.id.owe_list);
		
		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
		PeopleModel person = dbHelper.getUser();
		finances = dbHelper.getFinancesToID(person.getID());
		
		if (finances.size() <= 0) {
			emptyText.setVisibility(View.VISIBLE);
			owedList.setVisibility(View.GONE);
		} else {
			emptyText.setVisibility(View.GONE);
			owedList.setVisibility(View.VISIBLE);
			
			adapter = new TransactionAdapter(view.getContext(), finances, StaticValues.TRANSACTION_OWED);
			
			owedList.setAdapter(adapter);
		}
		
		return view;
	}
}
