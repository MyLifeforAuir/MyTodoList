package wanna.cu.mytodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
FloatingActionButton add_FAB;
ArrayList<MyData> todoData;
RecyclerView recyclerView;
    MyTodoDatabaseManager myTodoDatabaseManager;
CustomDialog customDialog;
MyRecycleAdapter myRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTodoDatabaseManager  = MyTodoDatabaseManager.getInstance(this);
        todoData = myTodoDatabaseManager.getAll();
        initViews();
        initCustomDialog();
        add_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

    }
    public void initViews(){
        add_FAB=findViewById(R.id.add_FAB);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myRecycleAdapter = new MyRecycleAdapter(todoData);
        recyclerView.setAdapter(myRecycleAdapter);
        myRecycleAdapter.setOnRecycleAdapterChangedListener(new OnRecycleAdapterChangedListener() {
            @Override
            public void onRecycleAdapterDataChanged() {
                //콜백 실행
                todoData = myTodoDatabaseManager.getAll();
                myRecycleAdapter.mData = todoData;
                myRecycleAdapter.notifyDataSetChanged();
            }
        });

    }
    public void initCustomDialog(){
        customDialog = new CustomDialog();
        customDialog.setOnOkClickedListener(new CustomDialog.OnOkClickedListener() {
            @Override
            public void onOkClicked() {
                //콜백 실행
                System.out.println("onOkClicked 콜백함수 실행!");
                todoData = myTodoDatabaseManager.getAll();
                myRecycleAdapter.mData = todoData;
                myRecycleAdapter.notifyDataSetChanged();
            }
        });
        customDialog.setCancelable(true);

    }
    public void showCustomDialog(){
        if(!customDialog.isAdded()) {
            customDialog.show(getSupportFragmentManager(), "CustomDialog");
        }
    }


}
