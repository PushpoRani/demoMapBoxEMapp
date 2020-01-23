package com.pushpo.demobarikoiemapp.Activity;


import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.pushpo.demobarikoiemapp.Adapter.MenuItemListAdapter;
import com.pushpo.demobarikoiemapp.PlaceDetailProvider;
import com.pushpo.demobarikoiemapp.R;


public class MenuActivity extends AppCompatActivity {

	//View Reference Variable

	private RecyclerView mRecyclerView;
	private GridLayoutManager mGridLayoutManager;
	private MenuItemListAdapter mMenuItemListAdapter;
	private String[] itemString;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mToggle;
	private NavigationView mNavigationView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);


        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);

        //actionBar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));

//        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
//        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//
//                    case R.id.location_favourite_icon:
//                        startActivity(new Intent(MenuActivity.this, FavouritePlaceListActivity.class));
//                        mDrawerLayout.closeDrawers();
//                        break;
//
//                    case R.id.share_icon:
//                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        shareIntent.setType("text/plain");
//                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, Checkout Emergency Map Application (EMAPP)");
//                        startActivity(Intent.createChooser(shareIntent, "Share App.."));
//                        mDrawerLayout.closeDrawers();
//                        break;
//
//                    case R.id.about_icon:
//                        Dialog aboutDialog = new Dialog(MenuActivity.this, R.style.AboutDialog);
//                        aboutDialog.setTitle(getString(R.string.about));
//                        aboutDialog.setContentView(R.layout.about);
//                        aboutDialog.show();
//                        mDrawerLayout.closeDrawers();
//                        break;
//                }
//                return false;
//            }
//        });

        itemString = PlaceDetailProvider.placeTagName;
        mMenuItemListAdapter = new MenuItemListAdapter(this, itemString);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mGridLayoutManager = new GridLayoutManager(MenuActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(36);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        mRecyclerView.setAdapter(mMenuItemListAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu to add items to action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.removeItem(R.id.share_icon);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setQueryHint(getString(R.string.search_hint));

//        searchView.setSearchableInfo(searchManager.getSearchableInfo(
//                new ComponentName(this, PlaceSearchResultActivity.class)));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
