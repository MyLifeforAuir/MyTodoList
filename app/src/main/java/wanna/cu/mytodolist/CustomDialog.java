package wanna.cu.mytodolist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CustomDialog extends DialogFragment {
    EditText content;
    Button add_btn_dialog, cancel_btn_dialog;
    TimePicker timePicker;
    DatePicker datePicker;

    public interface OnOkClickedListener{
        void onOkClicked();
    }
    private OnOkClickedListener mOnOkClickedListener;



    CustomDialog(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof OnOkClickedListener){
            mOnOkClickedListener = (OnOkClickedListener) getActivity();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.customdialog, container);
        add_btn_dialog = v.findViewById(R.id.add_btn_dialog);
        cancel_btn_dialog = v.findViewById(R.id.cancle_btn_dialog);
        content = v.findViewById(R.id.content_dialog);
        timePicker = v.findViewById(R.id.timePicker);
        datePicker = v.findViewById(R.id.datePicker);
        final MyTodoDatabaseManager myTodoDatabaseManager = MyTodoDatabaseManager.getInstance(getContext());
        add_btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "확인 버튼 눌림", Toast.LENGTH_SHORT).show();
                String tmp = content.getText().toString();
                String date = datePicker.getYear()+" "+datePicker.getMonth()+" "+datePicker.getDayOfMonth();
                String time = timePicker.getHour()+":"+timePicker.getMinute();
                myTodoDatabaseManager.insert(date+" "+time, "", tmp);
                if(mOnOkClickedListener!=null){
                    mOnOkClickedListener.onOkClicked();
                }
                dismiss();
            }
        });
        cancel_btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return v;
    }
}
