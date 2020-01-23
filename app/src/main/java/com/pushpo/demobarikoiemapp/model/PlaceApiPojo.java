
package com.pushpo.demobarikoiemapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceApiPojo {

    @SerializedName("Place")
    @Expose
    private List<Place> place = null;

    public List<Place> getPlace() {
        return place;
    }

    public void setPlace(List<Place> place) {
        this.place = place;
    }


    public class Place {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("distance_in_meters")
        @Expose
        private Double distanceInMeters;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("area")
        @Expose
        private String area;
        @SerializedName("pType")
        @Expose
        private String pType;
        @SerializedName("subType")
        @Expose
        private String subType;
        @SerializedName("uCode")
        @Expose
        private String uCode;
        @SerializedName("contact_person_phone")
        @Expose
        private String contactPersonPhone;
        @SerializedName("ST_AsText(location)")
        @Expose
        private String sTAsTextLocation;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getDistanceInMeters() {
            return distanceInMeters;
        }

        public void setDistanceInMeters(Double distanceInMeters) {
            this.distanceInMeters = distanceInMeters;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPType() {
            return pType;
        }

        public void setPType(String pType) {
            this.pType = pType;
        }

        public String getSubType() {
            return subType;
        }

        public void setSubType(String subType) {
            this.subType = subType;
        }

        public String getUCode() {
            return uCode;
        }

        public void setUCode(String uCode) {
            this.uCode = uCode;
        }

        public String getContactPersonPhone() {
            return contactPersonPhone;
        }

        public void setContactPersonPhone(String contactPersonPhone) {
            this.contactPersonPhone = contactPersonPhone;
        }

        public String getSTAsTextLocation() {
            return sTAsTextLocation;
        }

        public void setSTAsTextLocation(String sTAsTextLocation) {
            this.sTAsTextLocation = sTAsTextLocation;
        }

    }


}
