package com.jnu.student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.student.data.NormalTaskSaver;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NormalTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NormalTaskFragment extends Fragment {

    public static ArrayList<Task> normalTasks = new ArrayList<>();
    public static TaskAdapter taskAdapter = new TaskAdapter(normalTasks);


    public static final int MENU_ID_WARN = 1;
    public static final int MENU_ID_DELETE = 2;
    public static Bitmap uncheckImage = null;
    public NormalTaskFragment() {
        // Required empty public constructor
    }

    public static NormalTaskFragment newInstance() {
        NormalTaskFragment fragment = new NormalTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_normal_task
                , container, false);
        RecyclerView recyclerViewMain = rootView.findViewById(R.id.recyclerview_normal_task);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        NormalTaskSaver normalTaskSaver = new NormalTaskSaver();
        normalTasks = normalTaskSaver.Load(this.getContext());
        uncheckImage = BitmapFactory.decodeResource(getResources(), R.drawable.pin_unchecked);

        if (0 == normalTasks.size()) {
            normalTasks.add(new Task("写10道物理题",20,"无",0,1,"每日任务",false));
            normalTasks.add(new Task("写一张语文卷子",20,"无",0,1,"每日任务",false));
            normalTasks.add(new Task("背一百个单词",20,"无",0,1,"每日任务",false));
        }
        normalTaskSaver.Save(this.getContext(),normalTasks);

        taskAdapter = new TaskAdapter(normalTasks);
        recyclerViewMain.setAdapter(taskAdapter);
        return rootView;
    }
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_WARN:

                break;
            case MENU_ID_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("删除");
                builder.setMessage("你确定要删除吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        normalTasks.remove(item.getOrder());
                        new NormalTaskSaver().Save(NormalTaskFragment.this.getContext(),normalTasks);
                        taskAdapter.notifyItemRemoved(item.getOrder());
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                break;

        }
        return super.onContextItemSelected(item);
    }
}