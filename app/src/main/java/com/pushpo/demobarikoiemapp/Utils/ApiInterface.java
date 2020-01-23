package com.pushpo.demobarikoiemapp.Utils;

import com.pushpo.demobarikoiemapp.model.PlaceApiPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

	@GET(Urls.GET_NEARBY_PLACES)
	Call<PlaceApiPojo> getNearbyPlaces(@Query("ptype") String ptype,
									  @Query("latitude") Double latitude,
									   @Query("longitude") Double longitude
	);
}
