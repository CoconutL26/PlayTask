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

import com.jnu.student.data.WeekTaskSaver;

import java.util.ArrayList;


public class WeekTaskFragment extends Fragment {
    public static ArrayList<Task> weekTasks = new ArrayList<>();
    public static TaskAdapter taskAdapter = new TaskAdapter(weekTasks);


    public static final int MENU_ID_WARN = 1;
    public static final int MENU_ID_DELETE = 2;
    public static Bitmap uncheckImage = null;
    public WeekTaskFragment() {
        // Required empty public constructor
    }

    public static WeekTaskFragment newInstance() {
        WeekTaskFragment fragment = new WeekTaskFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_week_task
                , container, false);
        RecyclerView recyclerViewMain = rootView.findViewById(R.id.recyclerview_week_task);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        WeekTaskSaver weekTaskSaver = new WeekTaskSaver();
        weekTasks = weekTaskSaver.Load(this.getContext());
        uncheckImage = BitmapFactory.decodeResource(getResources(), R.drawable.pin_unchecked);

        if (0 == weekTasks.size()) {
            weekTasks.add(new Task("跑步十圈",20,"无",0,1,"每日任务",false));
            weekTasks.add(new Task("跳绳两百个",20,"无",0,1,"每日任务",false));
            weekTasks.add(new Task("打羽毛球两个小时",20,"无",0,1,"每日任务",false));
        }
        weekTaskSaver.Save(this.getContext(),weekTasks);

        taskAdapter = new TaskAdapter(weekTasks);
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
                        weekTasks.remove(item.getOrder());
                        new WeekTaskSaver().Save(WeekTaskFragment.this.getContext(),weekTasks);
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