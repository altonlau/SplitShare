package com.splitshare.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.splitshare.R;
import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OwingAdapter extends BaseAdapter {

	Context context;
	List<FinanceModel> finances;
	
	public OwingAdapter(Context context, List<FinanceModel> finances) {
		this.context = context;
		this.finances = finances;
	}
	
	@Override
	public int getCount() {
		return this.finances.size();
	}

	@Override
	public Object getItem(int position) {
		return this.finances.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.finances.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_owing, null);
		}
		
		TextView personTransaction = (TextView) convertView.findViewById(R.id.transaction_info);
		
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		FinanceModel finance = finances.get(position);
		PeopleModel owingPerson = dbHelper.getPeople(finance.getToID());
		DecimalFormat df = new DecimalFormat("0.00"); 
		
		personTransaction.setText(owingPerson.getFirstName() + " " + owingPerson.getLastName() + ": $" + df.format(finance.getAmountOwed()));
		
		return convertView;
	}

}
