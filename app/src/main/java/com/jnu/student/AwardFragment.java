package com.jnu.student;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AwardFragment extends Fragment {
    public AwardFragment() {
        // Required empty public constructor
    }
    public static AwardFragment newInstance() {
        AwardFragment fragment = new AwardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_award_view
                , container, false);
        RecyclerView recyclerViewMain = rootView.findViewById(R.id.recyclerview_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        FloatingActionButton fabbutton = rootView.findViewById(R.id.button_ADD);
        fabbutton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setItems(new CharSequence[]{"新建奖励", "排序"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 根据选择执行相应的操作，比如跳转到另一个界面
                            if (which == 0) {
                                // 选择了选项1
                                Intent intent = new Intent(requireActivity(), NewAward.class);
                                startActivity(intent);
                            } else if (which == 1) {
                                // 选择了选项2
                                // 执行其他操作或跳转到另一个界面
                            }
                        }
                    })
                    .create()
                    .show();
        });
        return rootView;
    }
}
