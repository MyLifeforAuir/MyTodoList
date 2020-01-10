package wanna.cu.mytodolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
interface OnDatabaseDeletedListener{
    public void onDatabaseDeleted();
}
public class MyTodoDatabaseManager {
    private SQLiteDatabase myDatabase = null;
    static MyTodoDatabaseManager myTodoDatabaseManager = null;
    OnDatabaseDeletedListener mOnDatabaseDeletedListener;

    public void setOnDatabaseDeletedListener(OnDatabaseDeletedListener onDatabaseDeletedListener){
        mOnDatabaseDeletedListener = onDatabaseDeletedListener;
    }

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
    public void delete(String content, String startTime){
        myDatabase.execSQL("delete from todo where  content = '"+content+"' and starttime = '"+startTime+"'");
        if(mOnDatabaseDeletedListener!=null) {
            mOnDatabaseDeletedListener.onDatabaseDeleted();
        }
    }
    public void insert(String startTime, String endTime, String content){
        myDatabase.execSQL("insert into todo(starttime, endtime, content) values('"+startTime+"', '"+endTime+"', '"+content+"')");
    }
    public ArrayList<MyData> getAll(){
        Cursor c = myDatabase.rawQuery("select * from todo", null);
        ArrayList<MyData> tmp = new ArrayList<>();
        MyData myData;
        if(c != null){
            if(c.moveToFirst()){

                do{
                    myData = new MyData();
                    myData.setStartTime(c.getString(c.getColumnIndex("starttime")));
                    myData.setTodo(c.getString(c.getColumnIndex("content")));

                    tmp.add(myData);
                }
                while(c.moveToNext());
            }
        }
        System.out.println(tmp);
        return tmp;
    }
}
