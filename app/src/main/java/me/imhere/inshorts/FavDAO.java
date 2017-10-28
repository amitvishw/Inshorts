package me.imhere.inshorts;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class FavDAO extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME    = "favsID.db";
    private static final String TABLE_NAME       = "favs";
    private static final String COLUMN_ID        = "id";

    FavDAO(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS  favs (id integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE favs");
        onCreate(sqLiteDatabase);
    }

    ArrayList<Integer> getAllFavs()
    {
        ArrayList<Integer> arrayList            = new ArrayList<>();
        SQLiteDatabase db                       = this.getReadableDatabase();
        Cursor res                              =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast())
        {
            arrayList.add((res.getInt(res.getColumnIndex(COLUMN_ID))));
            res.moveToNext();
        }
        res.close();
        return arrayList;
    }

    boolean insertFav(int id)
    {
        SQLiteDatabase db                       = this.getWritableDatabase();
        ContentValues contentValues             = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }
}
