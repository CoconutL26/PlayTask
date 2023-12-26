package com.jnu.student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.student.data.TaskSaver;
import java.util.ArrayList;


public class DayTaskFragment extends Fragment {
    public static ArrayList<Task> tasks = new ArrayList<>();
    private TaskAdapter taskAdapter = new TaskAdapter(this.getContext(),tasks);


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

        TaskSaver taskSaver = new TaskSaver();
        tasks = taskSaver.Load(this.getContext());
        uncheckImage = BitmapFactory.decodeResource(getResources(), R.drawable.pin_unchecked);

        if (0 == tasks.size()) {
            tasks.add(new Task("写10道数学题",20,"无","0/1","每日任务",false));
            tasks.add(new Task("写一张英语卷子",20,"无","0/1","每日任务",false));
            tasks.add(new Task("背五十个单词",20,"无","0/1","每日任务",false));
        }
        taskSaver.Save(this.getContext(),tasks);
        FloatingActionButton fabButton = rootView.findViewById(R.id.button_ADD_day_task);
        fabButton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setItems(new CharSequence[]{"新建任务", "排序"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 根据选择执行相应的操作，比如跳转到另一个界面
                            if (which == 0) {
                                // 选择了选项0
                                Intent intent = new Intent(requireActivity(), NewTask.class);
                                newTaskLauncher.launch(intent);
                                int a = 0;
                            } else if (which == 1) {
                                // 选择了选项1
                                // 执行其他操作或跳转到另一个界面
                            }
                        }
                    })
                    .create()
                    .show();
        });
        taskAdapter = new TaskAdapter(this.getContext(),tasks);
        recyclerViewMain.setAdapter(taskAdapter);
        return rootView;
    }
    //新建奖励
    private ActivityResultLauncher<Intent> newTaskLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null != result){
                    Intent intent=result.getData();
                    Bundle bundle=intent.getExtras();
                    String frequency=bundle.getString("frequency");
                    if(result.getResultCode()==NewTask.RESULT_CODE_SUCCESS && frequency.equals("每日任务"))
                    {

                        String title=bundle.getString("title");
                        int achieveCount=bundle.getInt("achieveCount");
                        String times=bundle.getString("times");
                        tasks.add(0,new Task(title,achieveCount,"无","0/"+times,frequency,false));
                        new TaskSaver().Save(this.getContext(),tasks);
                        taskAdapter.notifyItemInserted(0);
                    }
                }

            }
    );
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
                        tasks.remove(item.getOrder());
                        new TaskSaver().Save(DayTaskFragment.this.getContext(),tasks);
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


    public Drawable getDrawable(){
        Drawable uncheckDrawable = new BitmapDrawable(getResources(),uncheckImage);
        return uncheckDrawable;
    }


}