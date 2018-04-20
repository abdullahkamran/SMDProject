package database;

import android.arch.persistence.room.TypeConverter;
import android.net.Uri;

public class UriConverter {

    @TypeConverter
    public static String fromUri(Uri u){
        if(u!=null)
            return u.toString();
        else return null;
    }

    @TypeConverter
    public static Uri toUri(String s){
        if(s!=null)
            return Uri.parse(s);
        else
            return null;
    }
}