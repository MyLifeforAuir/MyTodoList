package wanna.cu.mytodolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MyTodoDatabaseManager {
    private SQLiteDatabase myDatabase = null;
    static MyTodoDatabaseManager myTodoDatabaseManager = null;
    public static MyTodoDatabaseManager getInstance(Context context){
        System.out.println("getInstance() 실행");
        if(myTodoDatabaseManager == null){
            myTodoDatabaseManager = new MyTodoDatabaseManager(context);
        }
        return myTodoDatabaseManager;
    }
    private MyTodoDatabaseManager(Context context){
        System.out.println("MyTodoDatabaseManager() 실행");
        //DB open
        myDatabase = context.openOrCreateDatabase("Todo.db", context.MODE_PRIVATE, null);

        //Table 생성
        myDatabase.execSQL("create table if not exists todo(" +
                "starttime date, "+
                "endtime date, "+
                "content text"
                +")");
    }
    public void insert(String startTime, String endTime, String content){
        myDatabase.execSQL("insert into todo(starttime, endtime, content) values('"+startTime+"', '"+endTime+"', '"+content+"')");
    }
    public ArrayList<String> getAll(){
        Cursor c = myDatabase.rawQuery("select * from todo", null);
        ArrayList<String> tmp = new ArrayList<>();
        if(c != null){
            if(c.moveToFirst()){

                do{
                    String con = c.getString(c.getColumnIndex("content"));

                    tmp.add(con);
                }
                while(c.moveToNext());
            }
        }
        System.out.println(tmp);
        return tmp;
    }
}
