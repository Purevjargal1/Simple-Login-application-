package mn.edu.num.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "Database.db";
    private static final String TABLE_NAME = "userlist";
    private static final String COL1 = "id";
    public static final String COL2 = "username";
    public static final String COL3 = "password";
    public static final String COL4 = "age";
    public static final String COL5 = "gender";
    public static final String COL6 = "phone_number";
    public static final String COL7 = "birth_date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, "
                + COL5 + " TEXT, " + COL6 + " TEXT, " + COL7 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String username, String password, String age, String gender, String number, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, username);
        contentValues.put(COL3, password);
        contentValues.put(COL4, age);
        contentValues.put(COL5, gender);
        contentValues.put(COL6, number);
        contentValues.put(COL7, dob);

        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.i("Puujedb", String.valueOf(result));
        if (result == -1)
            return false;
        else
            return true;
    }

    boolean checkUser(String username, String password) {
        String columns[] = {COL2};
        SQLiteDatabase db = this.getReadableDatabase();

        String query = COL2 + " = ? AND " + COL3 + " = ?";

        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, query, selectionArgs,null,null,null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public Cursor checkPwd(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME + " WHERE " + COL2 + "='" + username + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + "='" + username + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean updateData(String username, String age, String gender, String number, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, age);
        contentValues.put(COL5, gender);
        contentValues.put(COL6, number);
        contentValues.put(COL7, dob);

        long result = db.update(TABLE_NAME, contentValues, COL2 + " = ?", new String[] {username});
        if (result == -1)
            return false;
        else
            return true;
    }

    public void updatePass(String username, String newpass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL3, newpass);

        db.update(TABLE_NAME, contentValues, COL2 + " = ?", new String[] {username});
    }

//    public ArrayList getAllUser() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ArrayList<String> array_list = new ArrayList<String>();
//        Cursor res = db.rawQuery( "select " + COL2 + " from "+TABLE_NAME, null );
//        res.moveToFirst();
//        while(res.isAfterLast() == false) {
//            array_list.add(res.getString(res.getColumnIndex(COL2)));
//            res.moveToNext();
//        }
//        return array_list;
//    }
}


