package com.example.intershalanotesassinement.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.intershalanotesassinement.model.Notes;
import java.util.ArrayList;

public class NoteDbHelper extends SQLiteOpenHelper {
    private static final String NOTESDB_NAME="NotesDatabase";
    private static final int DB_VERSION=4;
    private static final String NOTESTABLE_NAME="notesTable";
    private static final String KEY_ID="id";
    private static final String KEY_SUMMARY="summary";
    private static final String KEY_TOPIC="topic";
    private SQLiteDatabase db;
    public NoteDbHelper(@Nullable Context context) {
        super(context, NOTESDB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+NOTESTABLE_NAME+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_SUMMARY+" TEXT,"+KEY_TOPIC+" TEXT"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+NOTESTABLE_NAME);
        onCreate(db);

    }

    public void updateTable(int id, String summary, String topic) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SUMMARY, summary);
        contentValues.put(KEY_TOPIC, topic);
        db.update(NOTESTABLE_NAME, contentValues, KEY_ID + " = ?", new String[] {String.valueOf(id)});
        db.close();
    }

    public void addNotes(String summary,String topic){
        db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SUMMARY,summary);
        contentValues.put(KEY_TOPIC,topic);
        db.insert(NOTESTABLE_NAME,null,contentValues);
        db.close();
    }
    public ArrayList<Notes> fetchData(){
        ArrayList<Notes> notes= new ArrayList<>();
        try {
            db= this.getReadableDatabase();
            Cursor cursor=db.rawQuery("select *from "+NOTESTABLE_NAME,null);

            while(cursor.moveToNext()){
                Notes notes1= new Notes(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                notes.add(notes1);
            }
            cursor.close();
            return notes;
        }catch(Exception e){
            //sql query exception
            notes.add(new Notes(1,"hello","I am unable to fetch data"));
        }finally {
            db.close();
        }

        return notes;


    }
}
