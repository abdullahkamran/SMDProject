package database;

import android.arch.persistence.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

public class LatLngConverter {
    @TypeConverter
    public static String fromLatLng(LatLng latlng){
        if(latlng!=null) {
            Double l1=latlng.latitude;
            Double l2=latlng.longitude;
            String coordl1 = l1.toString();
            String coordl2 = l2.toString();
            return coordl1+","+coordl2;
        }
        else return null;
    }

    @TypeConverter
    public static LatLng toUri(String s){
        if(s!=null) {
            String [] arrOfStr = s.split("@", 2);

            Double l1 = Double.parseDouble(arrOfStr[0]);
            Double l2 = Double.parseDouble(arrOfStr[1]);

            return new LatLng(l1, l2);
        }
        else
            return null;
    }
}
