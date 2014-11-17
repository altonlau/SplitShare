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

public class TransactionFragment extends Fragment {
	
	private TransactionAdapter adapter;
	private List<FinanceModel> finances;
	private int transactionType;
	
	public static TransactionFragment newInstance(int transactionType) {
		TransactionFragment owingFragment = new TransactionFragment();
		owingFragment.setTransactionType(transactionType);
		
		return owingFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(transactionType == StaticValues.TRANSACTION_OWED ? R.layout.fragment_owed : R.layout.fragment_owing, container, false);
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		TextView emptyText = (TextView) getActivity().findViewById(R.id.no_transaction_text);
		ListView owingList = (ListView) getActivity().findViewById(R.id.owing_list);
		
		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
		PeopleModel person = dbHelper.getUser();
		finances = transactionType == StaticValues.TRANSACTION_OWED ? dbHelper.getFinancesToID(person.getID()) : dbHelper.getFinancesFromID(person.getID());
		
		if (finances.size() <= 0) {
			emptyText.setVisibility(View.VISIBLE);
			owingList.setVisibility(View.GONE);
		} else {
			emptyText.setVisibility(View.GONE);
			owingList.setVisibility(View.VISIBLE);
			
			adapter = new TransactionAdapter(getActivity(), finances, transactionType);
			
			owingList.setAdapter(adapter);
		}
    }

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}
}
