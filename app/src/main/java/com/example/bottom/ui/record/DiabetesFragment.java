package com.example.bottom.ui.record;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottom.MainActivity;
import com.example.bottom.R;
import com.example.bottom.databinding.FragmentRecordBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DiabetesFragment extends Fragment implements Diabetes_level_MyRecyclerAdapter.Diabetes_level_MyRecyclerViewClickListener {

    private FragmentRecordBinding binding;
    ArrayList<Diabetes_level_ItemData> dataList = new ArrayList<>();
    Diabetes_level_MyRecyclerAdapter adapter = new Diabetes_level_MyRecyclerAdapter(dataList);

    private DrawerLayout drawerLayout;
    private View drawerView;
    TextView tv1, tv2;

    // 현재 날짜 표시
    long mNow = System.currentTimeMillis();
    Date mReDate = new Date(mNow);
    SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm:ss");
    String formatDate = dFormat.format(mReDate);
    String formatTime = tFormat.format(mReDate);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentRecordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener( this);


        tv1 = (TextView) view.findViewById(R.id.show_date);
        tv1.setText(formatDate);

        tv2 = (TextView) view.findViewById(R.id.show_time);
        tv2.setText(formatTime);

        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMenuInsert(view);
            }

        });




    }
    public void onMenuInsert(View view) {

        final View innerView = getLayoutInflater().inflate(R.layout.diabetes_level_list_insert, null);
        final Dialog mDialog = new Dialog(getContext());
        mDialog.setTitle("Title");
        mDialog.setContentView(innerView);
        mDialog.setCancelable(true);

        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        final EditText editTime = mDialog.findViewById(R.id.dli_add_time);
        final EditText editBeforeN = mDialog.findViewById(R.id.dli_add_before_num);
        final EditText editAfterN = mDialog.findViewById(R.id.dli_add_after_num);
        Button btn_go = mDialog.findViewById(R.id.btn_go);
        Button btn_cancel = mDialog.findViewById(R.id.btn_cancel);


        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myTime = editTime.getText().toString();
                String myBeforeN = editBeforeN.getText().toString();
                String myAfterN = editAfterN.getText().toString();

                dataList.add(new Diabetes_level_ItemData(myTime, myBeforeN, myAfterN));
                // Toast.makeText(getApplicationContext(), myTime, Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });



        mDialog.show();
        adapter.notifyDataSetChanged();
           // 데이터 변경 후 adapter의 notifyDataSetChanged() 호출 필수
    }


    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getActivity().getApplicationContext(), ""+(position+1), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClicked(int position) {

        adapter.remove(position);
        Toast.makeText(getActivity().getApplicationContext(),"리스트 삭제", Toast.LENGTH_SHORT).show();

    }
}