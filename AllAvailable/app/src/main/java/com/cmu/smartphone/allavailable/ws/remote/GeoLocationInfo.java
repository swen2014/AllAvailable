package com.cmu.smartphone.allavailable.ws.remote;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class GeoLocationInfo {

    private Context context;
    private LocationManager locationMangaer;
    private LocationListener locationListener;
    private Location location;
    private double latitude = 0.0;
    private double longitude = 0.0;


    public GeoLocationInfo(Context context) {
        this.context = context;
        this.locationMangaer = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        this.locationListener = new GetLocationListener();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void registerManager() {
        try {
            if (locationMangaer.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
                locationMangaer.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }


            if (locationMangaer.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
                locationMangaer.requestLocationUpdates(LocationManager
                        .GPS_PROVIDER, 0, 0, locationListener);
            }
        } catch (SecurityException se) {

        }
    }

    class GetLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location newLocation) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            location = newLocation;
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }


        @Override
        public void onProviderEnabled(String provider) {
        }


        @Override
        public void onProviderDisabled(String provider) {
        }
    }
} 