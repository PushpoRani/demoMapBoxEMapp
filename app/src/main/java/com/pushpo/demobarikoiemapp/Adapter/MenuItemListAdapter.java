package com.pushpo.demobarikoiemapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.pushpo.demobarikoiemapp.Activity.MapsActivity;
import com.pushpo.demobarikoiemapp.PlaceDetailProvider;
import com.pushpo.demobarikoiemapp.R;

import java.util.Random;


public class MenuItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Context of the activity
    private Context mContext;
    private String[] mPlacesListTag;

    public MenuItemListAdapter(Context context, String[] placesListTag) {
        mContext = context;
        mPlacesListTag = placesListTag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuItemListHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.custom_menu_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MenuItemListHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return mPlacesListTag.length;
    }


    private class MenuItemListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mPlaceTextView;
        private ImageView mPlaceImageView;
        private int mItemPosition;

        private MenuItemListHolder(View itemView) {
            super(itemView);
            mPlaceTextView = (TextView) itemView.findViewById(R.id.place_text_view);
            mPlaceImageView = (ImageView) itemView.findViewById(R.id.place_icon);

            mPlaceImageView.setOnClickListener(this);
        }



        private void bindView(int position) {
            mPlaceTextView.setText(mPlacesListTag[position]);

            mPlaceImageView.setImageDrawable(ContextCompat.getDrawable(mContext,
                    PlaceDetailProvider.placeTagIcon[position]));

            mPlaceTextView.setTypeface(Typeface.createFromAsset(mContext.getAssets(),
                    "Roboto-Regular.ttf"));
            int[] colorArray = PlaceDetailProvider.accentColor;

            int randomColor = colorArray[new Random().nextInt(colorArray.length)];

            ((GradientDrawable) mPlaceImageView.getBackground())
                    .setColor(ContextCompat.getColor(mContext, randomColor));

            mItemPosition = position;
        }



        @Override
        public void onClick(View v) {

            if (isNetworkAvailable()) {
                /*
                 * get the tag for query parameter like Atm, Bank etc.
                 */
                String locationTag = mPlacesListTag[mItemPosition];

                if (locationTag.equals("Office"))
                    locationTag = "office";
                else if (locationTag.equals("Health Care"))
                    locationTag = "healthcare";
                else if (locationTag.equals("Food"))
                    locationTag = "food";
                else if (locationTag.equals("Education"))
                    locationTag = "education";
                else if (locationTag.equals("Government Service"))
                    locationTag = "government";
                else if (locationTag.equals("Hotel"))
                    locationTag = "hotel";
                else if (locationTag.equals("Bank"))
                    locationTag = "bank";
                else if (locationTag.equals("Shopping Mall"))
                    locationTag = "commercial";
                else
                    locationTag = locationTag.replace(' ', '_').toLowerCase();
                /**
                 * Intent to start Place list activity with locationTag as extra data.
                 */
                Intent placeTagIntent = new Intent(mContext, MapsActivity.class);
                placeTagIntent.putExtra("place", locationTag);
                mContext.startActivity(placeTagIntent);
            } else
                Snackbar.make(mPlaceImageView, R.string.no_connection_string,
                        Snackbar.LENGTH_SHORT).show();
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        }
    }
}


