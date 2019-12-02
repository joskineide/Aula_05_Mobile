package usjt.com.aula06_sqlite_mobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlaceDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "places.db";
    private static final int DB_VERSION = 1;

    PlaceDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.createTablePlace());
        db.execSQL(Contract.insertPlace());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contract.PlaceContract.DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
    }
}
