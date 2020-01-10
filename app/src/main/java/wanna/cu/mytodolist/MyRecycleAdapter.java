package wanna.cu.mytodolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
interface OnRecycleAdapterChangedListener{
    public void onRecycleAdapterDataChanged();
}

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder> {
    OnRecycleAdapterChangedListener mOnRecycleAdapterChangedListener;
    ArrayList<MyData> mData;
    MyTodoDatabaseManager myTodoDatabaseManager;
    MyRecycleAdapter(ArrayList<MyData> data){
        mData = data;
    }
    public void setOnRecycleAdapterChangedListener(OnRecycleAdapterChangedListener onRecycleAdapterChangedListener){
        mOnRecycleAdapterChangedListener = onRecycleAdapterChangedListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.myrecycle, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        myTodoDatabaseManager = MyTodoDatabaseManager.getInstance(holder.cardView.getContext());
        myTodoDatabaseManager.setOnDatabaseDeletedListener(new OnDatabaseDeletedListener() {
            @Override
            public void onDatabaseDeleted() {
                Log.d("onDatabaseDeleted", "onDatabaseDeleted()콜백 함수 실행");
                if(mOnRecycleAdapterChangedListener!=null){
                    Log.d("onRecycleAdapterDataChanged", "onRecycleAdapterDataChanged()함수 실행");
                    mOnRecycleAdapterChangedListener.onRecycleAdapterDataChanged();
                }
            }
        });
        holder.content.setText(mData.get(position).getTodo());
        holder.timeTextView.setText(mData.get(position).getStartTime());
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("onLongClick", "onLongClick 실행됨"+mData.get(position));

                //myTodoDatabaseManager.delete(mData.get(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.cardView.getContext());
                builder.setTitle("해당 목록을 삭제하시겠습니까?\n내용 : "+mData.get(position).getTodo());
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myTodoDatabaseManager.delete(mData.get(position).getTodo(), mData.get(position).getStartTime());

                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
    TextView content, timeTextView;
    CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            cardView = itemView.findViewById(R.id.cardView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
