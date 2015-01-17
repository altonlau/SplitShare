package com.splitshare.fragment;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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

public class TransactionFragment extends Fragment {
	
	private TransactionAdapter adapter;
	private List<FinanceModel> finances;
	private int transactionType;
	
	public static TransactionFragment newInstance(int transactionType) {
		TransactionFragment transactionFragment = new TransactionFragment();
		transactionFragment.setTransactionType(transactionType);
		
		return transactionFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(transactionType == StaticValues.TRANSACTION_OWED ? R.layout.fragment_owed : R.layout.fragment_owing, container, false);
		
		TextView emptyText = (TextView) view.findViewById(R.id.no_transaction_text);
		ListView owingList = (ListView) view.findViewById(R.id.owing_list);
		
		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
		PeopleModel user = dbHelper.getUser();
		finances = transactionType == StaticValues.TRANSACTION_OWED ? dbHelper.getFinancesToID(user.getID()) : dbHelper.getFinancesFromID(user.getID());
		
		if (finances.size() <= 0) {
			emptyText.setVisibility(View.VISIBLE);
			owingList.setVisibility(View.GONE);
		} else {
			emptyText.setVisibility(View.GONE);
			owingList.setVisibility(View.VISIBLE);
			
			adapter = new TransactionAdapter(view.getContext(), finances, transactionType);
			
			owingList.setAdapter(adapter);
			
			Log.d("alton", "Size: " + adapter.getCount());
		}
		
		return view;
	}

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}
}
