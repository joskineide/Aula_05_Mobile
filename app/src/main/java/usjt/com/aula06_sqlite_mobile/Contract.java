package usjt.com.aula06_sqlite_mobile;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class Contract {

    private static List<Place> places;
    private static MainActivity mainActivity = new MainActivity();

    static{
        places = new ArrayList<>();
        places.add (new Place(
                25.006510, 54.986880
        ));
    }
    public static String createTablePlace() {
        return String.format(
                Locale.getDefault(),
                "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s DOUBLE, %s DOUBLE);",
                PlaceContract.TABLE_NAME,
                PlaceContract.COLUMN_NAME_ID,
                PlaceContract.COLUMN_NAME_LATITUDE,
                PlaceContract.COLUMN_NAME_LONGITUDE
        );
    }

    private Contract() {

    }

    public static String insertPlace() {
        String template = String.format(
                Locale.getDefault(),
                "INSERT INTO %s (%s, %s) VALUES",
                PlaceContract.TABLE_NAME,
                PlaceContract.COLUMN_NAME_LATITUDE,
                PlaceContract.COLUMN_NAME_LONGITUDE
        );

        StringBuilder sb = new StringBuilder("");
        for (Place place : places) {
            Log.e("Usjt", place.toString());
            sb.append(
                    String.format(
                            Locale.getDefault(),
                            "(%f, %f);",
                            place.getLatitude(),
                            place.getLongitude()
                    )
            );
        }

        String result = template + sb.toString();
        result = result.substring(0, result.length() - 1);
        result = result.concat(";");

        return result;
    }

    public static class PlaceContract implements BaseColumns {
        public static final String TABLE_NAME = "tb_place";
        public static final String COLUMN_NAME_ID = "id_place";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";

        public static final String DROP_TABLE = String.format(Locale.getDefault(), "DROP TABLE %s", PlaceContract.TABLE_NAME);
    }
}
