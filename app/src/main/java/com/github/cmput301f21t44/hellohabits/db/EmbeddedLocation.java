package com.github.cmput301f21t44.hellohabits.db;

import androidx.room.ColumnInfo;

import com.github.cmput301f21t44.hellohabits.model.Location;

/**
 * Location class embedded into HabitEventEntity
 */
public class EmbeddedLocation implements Location {
    @ColumnInfo(name = "longitude")
    public double mLongitude;

    @ColumnInfo(name = "latitude")
    public double mLatitude;

    @ColumnInfo(name = "accuracy")
    public double mAccuracy;

    public EmbeddedLocation(double longitude, double latitude, double accuracy) {
        this.mLongitude = longitude;
        this.mLatitude = latitude;
        this.mAccuracy = accuracy;
    }


    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getAccuracy() {
        return mAccuracy;
    }

    public static EmbeddedLocation from(Location l) {
        return new EmbeddedLocation(l.getLongitude(), l.getLatitude(), l.getAccuracy());
    }
}