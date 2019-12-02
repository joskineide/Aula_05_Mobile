package usjt.com.aula06_sqlite_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceDAO {
    private Context context;

    public PlaceDAO(Context context) {
        this.context = context;
    }

    public List<Place> search() {
        PlaceDBHelper dbHelper = new PlaceDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Place> places = new ArrayList<>();
        String command = String.format(
                Locale.getDefault(),
                "SELECT * FROM %s ORDER BY id_place DESC LIMIT 50",
                Contract.PlaceContract.TABLE_NAME
        );
        Cursor cursor = db.rawQuery(command, null);
        while (cursor.moveToNext()) {
            int idPlace = cursor.getInt(
                    cursor.getColumnIndex(
                            Contract.PlaceContract.COLUMN_NAME_ID
                    )
            );

            double latitude = cursor.getDouble(
                    cursor.getColumnIndex(
                            Contract.PlaceContract.COLUMN_NAME_LATITUDE
                    )
            );

            double longitude = cursor.getDouble(
                    cursor.getColumnIndex(
                            Contract.PlaceContract.COLUMN_NAME_LONGITUDE
                    )
            );

            Place place = new Place(idPlace, latitude, longitude);
            places.add(place);
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return places;
    }

    public Place insertLocation(Place place) {
        PlaceDBHelper dbHelper = new PlaceDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String template = "INSERT INTO %s (%s, %s) VALUES (%f, %f);";
        String command = String.format(
                Locale.getDefault(),
                template,
                Contract.PlaceContract.TABLE_NAME,
                Contract.PlaceContract.COLUMN_NAME_LATITUDE,
                Contract.PlaceContract.COLUMN_NAME_LONGITUDE,
                place.getLatitude(),
                place.getLongitude()
        );

        // Gets the data repository in write mode
        db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Contract.PlaceContract.COLUMN_NAME_LATITUDE, place.getLatitude());
        values.put(Contract.PlaceContract.COLUMN_NAME_LONGITUDE, place.getLongitude());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Contract.PlaceContract.TABLE_NAME, null, values);

        db.close();
        dbHelper.close();
        return place;
    }

}
