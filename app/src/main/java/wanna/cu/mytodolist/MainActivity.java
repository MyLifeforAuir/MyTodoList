package wanna.cu.mytodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomDialog.OnOkClickedListener {
Button add_btn, del_btn;
ArrayList<String> todoData;
RecyclerView recyclerView;
    MyTodoDatabaseManager myTodoDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTodoDatabaseManager  = MyTodoDatabaseManager.getInstance(this);
        todoData = myTodoDatabaseManager.getAll();
        initViews();
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });



    }
    public void initViews(){
        add_btn=findViewById(R.id.add_btn);
        del_btn = findViewById(R.id.del_btn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new MyRecycleAdapter(todoData));
    }
    public void showCustomDialog(){
        CustomDialog customDialog = new CustomDialog();
        customDialog.setCancelable(false);
        customDialog.show(getSupportFragmentManager(), "CustomDialog");
    }

    public ArrayList<String> getTodoData() {
        return todoData;
    }

    public void addTodoData(String text){
            todoData.add(text);
    }

    @Override
    public void onOkClicked() {
        //콜백 실행
        System.out.println("onOkClicked 콜백함수 실행!");
        todoData = myTodoDatabaseManager.getAll();
        recyclerView.setAdapter(new MyRecycleAdapter(todoData));
    }
}
