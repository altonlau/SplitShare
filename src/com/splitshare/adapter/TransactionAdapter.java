package com.splitshare.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.splitshare.R;
import com.splitshare.StaticValues;
import com.splitshare.db.DatabaseHelper;
import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;

public class TransactionAdapter extends ArrayAdapter<FinanceModel> {
	private int transactionType;

	public TransactionAdapter(Context context, List<FinanceModel> finances, int transactionType) {
		super(context, transactionType == StaticValues.TRANSACTION_OWED ? R.layout.list_owed : R.layout.list_owing, finances);
		this.transactionType = transactionType;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(transactionType == StaticValues.TRANSACTION_OWED ? R.layout.list_owed : R.layout.list_owing, parent, false);
			
			// Initialize the view holder
			viewHolder = new ViewHolder();
			viewHolder.transactionInfoView = (TextView) convertView.findViewById(R.id.transaction_info);
			
			convertView.setTag(viewHolder);
		} else {
			// Recycle the already inflated view
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		// Update the item view
		DatabaseHelper dbHelper = new DatabaseHelper(getContext());
		FinanceModel finance = getItem(position);
		PeopleModel person = dbHelper.getPeople(transactionType == StaticValues.TRANSACTION_OWED ? finance.getFromID() : finance.getToID());
		DecimalFormat df = new DecimalFormat("0.00");		
		
		viewHolder.transactionInfoView.setText(person.getFirstName() + " " + person.getLastName() + ": $" + df.format(finance.getAmountOwed()));
		
		return convertView;
	}
	
	/**
	 * The view holder design pattern prevents using findViewById() repeatedly
	 * in the getView() method of the adapter.
	 */
	private static class ViewHolder {
		TextView transactionInfoView;
	}
}
