package com.pushpo.demobarikoiemapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pushpo.demobarikoiemapp.R;
import com.pushpo.demobarikoiemapp.model.PlaceApiPojo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import barikoi.barikoilocation.SearchAutoComplete.PlaceSearchAdapter;

public class NearbyPlacesListAdapter extends RecyclerView.Adapter<NearbyPlacesListAdapter.ViewHolder> {

	private LayoutInflater layoutInflater;
	private Context mContext;
	private List<PlaceApiPojo.Place> mLists;
	private static final String TAG = "NearbyPlaceAdapter";
	private String placeName, subType, address, areaName, contact, distanceStr;
	private Double distance;

	public NearbyPlacesListAdapter(Context context, List<PlaceApiPojo.Place> lists){
		mContext = context;
		mLists = lists;

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		layoutInflater = LayoutInflater.from(mContext);
		View view = layoutInflater.inflate(R.layout.custom_place_detail, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {

		placeName = mLists.get(position).getName();
		holder.tvPlaceName.setText(placeName);
		subType = mLists.get(position).getSubType();
		holder.tvSubType.setText(subType);
		address = mLists.get(position).getAddress();
		holder.tvAddress.setText(address);
//		if (!address.equals(null)){
//			holder.tvAddress.setText(address);
//		}else {
//			holder.tvAddress.setVisibility(View.GONE);
//		}

		areaName = mLists.get(position).getArea();
		holder.tvArea.setText(areaName);
		contact = mLists.get(position).getContactPersonPhone();
		holder.tvContact.setText(contact);
//		if (!contact.equals(null)){
//			holder.tvContact.setText(contact);
//		}else {
//			holder.tvContact.setVisibility(View.GONE);
//		}
		distance = mLists.get(position).getDistanceInMeters();
		distanceStr = String.format("%,.2f", distance);

		holder.tvDistance.setText("Distance ~" +distanceStr+ "m");

		Log.d(TAG, "Place: " +placeName);
		Log.d(TAG, "Distance: " +distanceStr);
		Log.d(TAG, "Contact: " +contact);

	}

	@Override
	public int getItemCount() {
		System.out.println("mListSize: " + mLists.size());
		return mLists.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		TextView tvPlaceName, tvSubType, tvDistance, tvAddress, tvArea, tvContact;

		public ViewHolder(View itemView) {
			super(itemView);
			tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
			tvSubType = itemView.findViewById(R.id.tvSubtype);
			tvDistance = itemView.findViewById(R.id.tvDistance);
			tvAddress = itemView.findViewById(R.id.tvAddress);
			tvArea = itemView.findViewById(R.id.tvArea);
			tvContact = itemView.findViewById(R.id.tvContact);

			//itemLayout = itemView.findViewById(R.id.itemLayout);


		}
	}
}
