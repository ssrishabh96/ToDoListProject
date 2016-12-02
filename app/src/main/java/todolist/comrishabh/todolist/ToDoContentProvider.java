package todolist.comrishabh.todolist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by RISHABH on 12/2/2016.
 */

public class ToDoContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI=Uri.parse("content://todolist.comrishabh.todoprovider/todoitems");
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    public static final String KEY_ID="_id";
    public static final String KEY_TASK="task";
    public static final String KEY_CREATION_DATE="creation_date";

    private static final String TAG=ToDoContentProvider.class.getSimpleName();

    private static final int ALL_ROWS=1;
    private static final int SINGLE_ROW=2;
    private static UriMatcher uriMatcher;

    {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("todolist.comrishabh.todoprovider","todoitems", ALL_ROWS);
        uriMatcher.addURI("todolist.comrishabh.todoprovider","todoitems/#", SINGLE_ROW);
    }


    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate: fired");
        mySQLiteOpenHelper= new MySQLiteOpenHelper(getContext(),
                MySQLiteOpenHelper.DATABASE_NAME,null,MySQLiteOpenHelper.DATABASE_VERSION);

        return Boolean.TRUE;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "query: fired ");

        SQLiteDatabase sqliteDatabse= mySQLiteOpenHelper.getWritableDatabase();

        String groupBy=null;
        String having="";

        SQLiteQueryBuilder sqLiteQueryBuilder= new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(MySQLiteOpenHelper.DATABASE_TABLE);

        // If this is a row query,limit the resultSet to be passed ina row

        switch (uriMatcher.match(uri)){

            case SINGLE_ROW:
                String rowID=uri.getPathSegments().get(1);
                sqLiteQueryBuilder.appendWhere(KEY_ID+"="+ rowID);

            default:break;
        }

        Cursor cursor= sqLiteQueryBuilder.query(sqliteDatabse, projection,selection,selectionArgs, groupBy, having,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.i(TAG, "getType: fired");

        switch (uriMatcher.match(uri)){

            case ALL_ROWS: return "vnd.android.cursor.dir/vnd.comrishabh.todos";

            case SINGLE_ROW: return "vnd.android.cursor.item/vnd.comrishabh.todos";

            default: throw new IllegalArgumentException("Unsupported URI "+uri.toString());
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.i(TAG, "insert: fired");

        SQLiteDatabase db=mySQLiteOpenHelper.getWritableDatabase();

        String nullColumnhack=null;

        long id=db.insert(MySQLiteOpenHelper.DATABASE_TABLE, nullColumnhack,contentValues);

        if(id> -1)
        {
            Uri myuri= ContentUris.withAppendedId(CONTENT_URI,id);

            getContext().getContentResolver().notifyChange(myuri,null);
            return myuri;
        }
        else
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "delete: fired");

        // Open read/Write database to support transaction
        SQLiteDatabase dbase= mySQLiteOpenHelper.getWritableDatabase();

        // check for the row URI/ or entire databse URI

        switch (uriMatcher.match(uri))
        {
            case SINGLE_ROW:
                String row_id=uri.getPathSegments().get(1);
                selection=KEY_ID+"="+row_id+
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection+")": "" );


            default:break;
        }

        if(selection==null)
            selection="1";

        // Execeute the deletion
        int deleteCount= dbase.delete(MySQLiteOpenHelper.DATABASE_TABLE, selection, selectionArgs);

        // Notify any observer of changes in  the dataset of the table
        getContext().getContentResolver().notifyChange(uri,null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        Log.i(TAG, "update: fired");
        SQLiteDatabase database= mySQLiteOpenHelper.getWritableDatabase();

        switch (uriMatcher.match(uri))
        {
            case SINGLE_ROW:

                String rowid=uri.getPathSegments().get(1);
                selection=KEY_ID+"="+rowid
                        + (!TextUtils.isEmpty(selection)? "AND ("+selection+")":"" );

            default:break;
        }

        int updatecount=database.update(MySQLiteOpenHelper.DATABASE_TABLE,contentValues,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return updatecount;

    }

    private static class MySQLiteOpenHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME="todolistdatabase.db";
        private static final String DATABASE_TABLE="toDoListTable";
        private static final int DATABASE_VERSION=1;
        private static final String DATABASE_CREATE="create table "+ DATABASE_TABLE
                + " ("+ KEY_ID+" integer primary key autoincrement, "+
                KEY_TASK+" text not null, " +
                KEY_CREATION_DATE +" long);";


        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            Log.i(TAG, "MySQLiteOpenHelper: MySQLiteOpenHelper fired");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.i(TAG, "MySQLiteOpenHelper: onCreate fired");
            sqLiteDatabase.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
            Log.i(TAG, "MySQLiteOpenHelper: onUpgrade fired");
            Log.i(TAG,String.format("TaskDbAdapter onUpgrading database from old( %d ), to new verion( %d )", oldVer, newVer) );

            sqLiteDatabase.execSQL("DROP TABLE IF IT EXISTS "+DATABASE_TABLE);
            onCreate(sqLiteDatabase);

        }
    }



}
