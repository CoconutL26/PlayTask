package com.jnu.student;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskFragment extends Fragment{
    private String []tabHeaderStrings = {"每日任务","每周任务","普通任务"};
    public static int total_score=0;
    public static TextView total_score_text;

//    private TaskAdapter taskAdapter = new TaskAdapter(this.getContext(),tasks);

    public TaskFragment() {
        // Required empty public constructor
    }
    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_task_view
                , container, false);
        // 获取ViewPager2和TabLayout的实例
        ViewPager2 viewPager = rootView.findViewById(R.id.task_pager);
        viewPager.setUserInputEnabled(true);
        TabLayout tabLayout = rootView.findViewById(R.id.task_tab_layout);

        total_score_text = rootView.findViewById(R.id.total_score);
        total_score_text.setText(String.valueOf(total_score));
        // 创建适配器
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        // 将TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
        ).attach();
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
        return rootView;
    }


    public class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 3;
        public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 根据位置返回对应的Fragment实例
            switch (position) {
                case 0:
                    return DayTaskFragment.newInstance();
                case 1:
                    return WeekTaskFragment.newInstance();
                case 2:
                    return NormalTaskFragment.newInstance();

            }
            return TaskFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return NUM_TABS;
        }

    }
    //新建奖励
    private ActivityResultLauncher<Intent> newTaskLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,result -> {

                if(null != result) {
                    Intent intent = result.getData();
                    if (result.getResultCode() == NewTask.RESULT_CODE_SUCCESS) {
                        Bundle bundle = intent.getExtras();
                        String frequency = bundle.getString("frequency");
                        String title = bundle.getString("title");
                        int achieveCount = bundle.getInt("achieveCount");
                        int times = bundle.getInt("times");
                        if(frequency.equals("每日任务")){
                                // 添加任务到DayTaskFragment
                                DayTaskFragment.dayTasks.add(0, new Task(title, achieveCount, "无",0, times, frequency, false));
                                DayTaskFragment.taskAdapter.notifyItemInserted(0);
                        }else if(frequency.equals("每周任务")){
                                // 添加任务到WeekTaskFragment
                                WeekTaskFragment.weekTasks.add(0, new Task(title, achieveCount, "无",0, times, frequency, false));
                                WeekTaskFragment.taskAdapter.notifyItemInserted(0);
                        }else if(frequency.equals("普通任务")){
                                // 添加任务到NormalTaskFragment
                                NormalTaskFragment.normalTasks.add(0, new Task(title, achieveCount, "无",0, times, frequency, false));
                                NormalTaskFragment.taskAdapter.notifyItemInserted(0);
                        }

                    }
                }
            }
    );
//    public void changeColor(){
//        if(TaskFragment.total_score<0){
//            TaskFragment.total_score_text.setTextColor(ContextCompat.getColor(TaskFragment.total_score_text.getContext(),android.R.color.holo_red_light));
////            AwardFragment.total_score_text.setTextColor(ContextCompat.getColor(TaskFragment.total_score_text.getContext(),android.R.color.holo_red_light));
//        }else{
//            TaskFragment.total_score_text.setTextColor(ContextCompat.getColor(TaskFragment.total_score_text.getContext(),android.R.color.black));
////            AwardFragment.total_score_text.setTextColor(ContextCompat.getColor(TaskFragment.total_score_text.getContext(),android.R.color.black));
//        }
//    }


}
