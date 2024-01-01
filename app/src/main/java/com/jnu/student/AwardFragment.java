package com.jnu.student;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.student.data.DataSaver;

import java.util.ArrayList;

public class AwardFragment extends Fragment implements AwardAdapter.OnItemClickListener {
    public static ArrayList<Award> awards = new ArrayList<>();
    public static final int MENU_ID_UPDATE = 1;
    public static final int MENU_ID_DELETE = 2;
    private AwardAdapter awardAdapter = new AwardAdapter(awards);
    public static TextView total_score_text;

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

    //新建奖励
    private ActivityResultLauncher<Intent> newAwardLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            , result -> {
                if (null != result) {
                    Intent intent = result.getData();
                    if (result.getResultCode() == UpdateAward.RESULT_CODE_SUCCESS) {
                        //获取数据
                        Bundle bundle = intent.getExtras();
                        String title = bundle.getString("title");
                        int achievement = bundle.getInt("achievement");
                        String times = bundle.getString("times");
                        awards.add(0, new Award(title, achievement, "无", times,0));
                        awardAdapter.notifyItemInserted(0);
                    }
                }

            }
    );
    //修改奖励
    private ActivityResultLauncher<Intent> editAwardLauncher = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult()
                    , result -> {
                        if (null != result) {
                            Intent intent = result.getData();
                            if (result.getResultCode() == UpdateAward.RESULT_CODE_SUCCESS) {
                                String title = intent.getStringExtra("title");
                                int achievement = intent.getIntExtra("achievement", 0);
                                String label = intent.getStringExtra("label");
                                String times = intent.getStringExtra("times");
                                int position = intent.getIntExtra("position", 0);
                                awards.get(position).setTitle(title);
                                awards.get(position).setAchievement(achievement);
                                awards.get(position).setTimes(times);
                                if (label == null) {
                                    label = "无";
                                }
                                awards.get(position).setLabel(label);
//                                new DataSaver().Save(this.getContext(),awards);
                                awardAdapter.notifyItemChanged(position);
                            }
                        }
                    });

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_award_view
                , container, false);
        RecyclerView recyclerViewMain = rootView.findViewById(R.id.recyclerview_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        total_score_text = rootView.findViewById(R.id.total_score);
        total_score_text.setText(String.valueOf(TaskFragment.total_score));

        DataSaver dataSaver = new DataSaver();
        awards = dataSaver.Load(this.getContext());

        if (0 == awards.size()) {
            awards.add(new Award("打游戏", 20, "无", "0/1",0));
            awards.add(new Award("追剧", 20, "无", "0/1",0));
            awards.add(new Award("看电影", 20, "无", "0/1",0));
        }
        FloatingActionButton fabButton = rootView.findViewById(R.id.button_ADD);
        fabButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setItems(new CharSequence[]{"新建奖励", "排序"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 根据选择执行相应的操作，比如跳转到另一个界面
                            if (which == 0) {
                                // 选择了选项0
                                Intent intent = new Intent(requireActivity(), UpdateAward.class);
                                newAwardLauncher.launch(intent);
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
        awardAdapter = new AwardAdapter(awards);

        recyclerViewMain.setAdapter(awardAdapter);
        // 设置点击监听器
        awardAdapter.setOnItemClickListener((AwardAdapter.OnItemClickListener) this);
        return rootView;
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_UPDATE:
                Intent intentUpdate = new Intent(this.getContext(), UpdateAward.class);
                Award award = awards.get(item.getOrder());
                intentUpdate.putExtra("position", item.getOrder());
                intentUpdate.putExtra("title", award.getTitle());
                intentUpdate.putExtra("achievement", award.getAchievement());
                intentUpdate.putExtra("label", award.getLabel());
                intentUpdate.putExtra("times", award.getTimes());
                editAwardLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("删除");
                builder.setMessage("你确定要删除吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        awards.remove(item.getOrder());
                        new DataSaver().Save(AwardFragment.this.getContext(), awards);
                        awardAdapter.notifyItemRemoved(item.getOrder());
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


    @Override
    public void onItemClick(View view, int position) {
        // 处理点击事件
        // position 是被点击的列表项的位置
        String achievement = String.valueOf(awards.get(position).getAchievement());
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("满足奖励");
        builder.setMessage("确定花费"+achievement+"点成就来满足你的奖励？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int spend_achievement = awards.get(position).getAchievement();
                TaskFragment.total_score -= spend_achievement;
                TaskFragment.total_score_text.setText(String.valueOf(TaskFragment.total_score));
                total_score_text.setText(String.valueOf(TaskFragment.total_score));
//                if(TaskFragment.total_score<0){
//                    TaskFragment.total_score_text.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
//                    total_score_text.setTextColor(ContextCompat.getColor(getContext(),android.R.color.holo_red_light));
//                    Context context1 = getContext();
//                    Context context2 = getContext();
//                }else{
//                    TaskFragment.total_score_text.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));
//                    total_score_text.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));
//                }
                String times=awards.get(position).getTimes();
                int done_times=awards.get(position).getDone_times();
                String donetimes=String.valueOf(done_times);
                if(times.equals("0/1")){
                    done_times++;
                    times="1/1";
                    awards.get(position).setTimes(times);
                    awards.get(position).setDone_times(done_times);
                    awards.remove(position);
                    awardAdapter.notifyItemChanged(position);
                    new DataSaver().Save(AwardFragment.this.getContext(), awards);
                    awardAdapter.notifyItemRemoved(position);
                }
                else if(times.equals(donetimes+"/∞")){
                    done_times++;
                    donetimes=String.valueOf(done_times);
                    times=donetimes+"/∞";
                    awards.get(position).setDone_times(done_times);
                    awards.get(position).setTimes(times);
                    awardAdapter.notifyItemChanged(position);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}





