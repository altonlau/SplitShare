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
import com.splitshare.adapter.OwingAdapter;
import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;

public class OwingFragment extends Fragment {
	
	OwingAdapter adapter;
	List<FinanceModel> finances;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_owing, container, false);
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		TextView emptyText = (TextView) getActivity().findViewById(R.id.no_owing_text);
		ListView owingList = (ListView) getActivity().findViewById(R.id.owing_list);
		
		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
		PeopleModel person = dbHelper.getUser();
		finances = dbHelper.getFinancesToID(person.getID());
		
		if (finances.size() <= 0) {
			emptyText.setVisibility(View.VISIBLE);
			owingList.setVisibility(View.GONE);
		} else {
			emptyText.setVisibility(View.GONE);
			owingList.setVisibility(View.VISIBLE);
			
			adapter = new OwingAdapter(getActivity(), finances);
			
			owingList.setAdapter(adapter);
		}
    }
}
