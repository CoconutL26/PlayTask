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

import com.jnu.student.data.DayTaskSaver;
import java.util.ArrayList;


public class DayTaskFragment extends Fragment {
    public static ArrayList<Task> dayTasks = new ArrayList<>();
    public static TaskAdapter taskAdapter = new TaskAdapter(dayTasks);


    public static final int MENU_ID_WARN = 1;
    public static final int MENU_ID_DELETE = 2;
    public static Bitmap uncheckImage = null;
    public DayTaskFragment() {
        // Required empty public constructor
    }


    public static DayTaskFragment newInstance() {
        DayTaskFragment fragment = new DayTaskFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_day_task
                , container, false);
        RecyclerView recyclerViewMain = rootView.findViewById(R.id.recyclerview_day_task);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        DayTaskSaver dayTaskSaver = new DayTaskSaver();
        dayTasks = dayTaskSaver.Load(this.getContext());
        uncheckImage = BitmapFactory.decodeResource(getResources(), R.drawable.pin_unchecked);

        if (0 == dayTasks.size()) {
            dayTasks.add(new Task("写10道数学题",20,"无",0,1,"每日任务",false));
            dayTasks.add(new Task("写一张英语卷子",20,"无",0,1,"每日任务",false));
            dayTasks.add(new Task("背五十个单词",20,"无",0,1,"每日任务",false));
        }
        dayTaskSaver.Save(this.getContext(),dayTasks);

        taskAdapter = new TaskAdapter(dayTasks);
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
                        dayTasks.remove(item.getOrder());
                        new DayTaskSaver().Save(DayTaskFragment.this.getContext(),dayTasks);
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