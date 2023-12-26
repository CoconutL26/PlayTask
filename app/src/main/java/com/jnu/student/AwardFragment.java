package com.jnu.student;
import android.app.AlertDialog;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.student.data.DataSaver;

import java.util.ArrayList;

public class AwardFragment extends Fragment {
    public static ArrayList<Award> awards = new ArrayList<>();
    public static final int MENU_ID_UPDATE = 1;
    public static final int MENU_ID_DELETE = 2;
    private AwardAdapter awardAdapter = new AwardAdapter(awards);
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
            ,result -> {
                if(null != result){
                    Intent intent=result.getData();
                    if(result.getResultCode()== UpdateAward.RESULT_CODE_SUCCESS)
                    {
                        //获取数据
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        int achieveCount=bundle.getInt("achieveCount");
                        String times=bundle.getString("times");
                        awards.add(0,new Award(title,achieveCount,"无",times));
                        new DataSaver().Save(this.getContext(),awards);
                        awardAdapter.notifyItemInserted(0);
                    }
                }

            }
    );
    //修改奖励
    private ActivityResultLauncher<Intent> editAwardLauncher = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult()
                    ,result -> {
                        if(null!=result){
                            Intent intent=result.getData();
                            if(result.getResultCode()== UpdateAward.RESULT_CODE_SUCCESS)
                            {
                                String title = intent.getStringExtra("title");
                                int achievement = intent.getIntExtra("achievement",0);
                                String label = intent.getStringExtra("label");
                                String times = intent.getStringExtra("times");
                                int position = intent.getIntExtra("position",0);
                                awards.get(position).setTitle(title);
                                awards.get(position).setAchievement(achievement);
                                awards.get(position).setTimes(times);
                                if(label==null){    label="无";}
                                awards.get(position).setLabel(label);
                                new DataSaver().Save(this.getContext(),awards);
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

        DataSaver dataSaver = new DataSaver();
        awards = dataSaver.Load(this.getContext());

        if (0 == awards.size()) {
            awards.add(new Award("打游戏",20,"无","0/1"));
            awards.add(new Award("追剧",20,"无","0/1"));
            awards.add(new Award("看电影",20,"无","0/1"));
        }
        FloatingActionButton fabButton = rootView.findViewById(R.id.button_ADD);
        fabButton.setOnClickListener(v->{
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
        return rootView;
    }
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID_UPDATE:
                Intent intentUpdate = new Intent(this.getContext(), UpdateAward.class);
                Award award= awards.get(item.getOrder());
                intentUpdate.putExtra("position",item.getOrder());
                intentUpdate.putExtra("title",award.getTitle());
                intentUpdate.putExtra("achievement",award.getAchievement());
                intentUpdate.putExtra("label",award.getLabel());
                intentUpdate.putExtra("times",award.getTimes());
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
                        new DataSaver().Save(AwardFragment.this.getContext(),awards);
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

    public static class AwardAdapter extends RecyclerView.Adapter<AwardAdapter.ViewHolder> {
        @NonNull
        private ArrayList<Award> awardArrayList;

        public class ViewHolder extends RecyclerView.ViewHolder
                implements View.OnCreateContextMenuListener {

            private final TextView awardTitleView;
            private final TextView awardAchieveView;
            private final TextView awardTimes;


            public ViewHolder(View itemView) {
                super(itemView);
                awardAchieveView = itemView.findViewById(R.id.text_view_award_achieve);
                awardTitleView = itemView.findViewById(R.id.text_view_award_title);
                awardTimes = itemView.findViewById(R.id.text_view_award_times);

                itemView.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle() {
                return awardAchieveView;
            }

            public TextView getImageViewImage() {
                return awardTitleView;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view
                    , ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"编辑");
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"删除");
            }
        }

        public AwardAdapter(ArrayList<Award> awardArrayList) {

            this.awardArrayList = awardArrayList;
        }
        // Create new views (invoked by the layout manager)
        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.awardlayout, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Award award = awardArrayList.get(position);
            viewHolder.awardTitleView.setText(award.getTitle());
            String achievementText = String.valueOf((-1)*award.getAchievement());
            viewHolder.awardAchieveView.setText(achievementText);
            viewHolder.awardTimes.setText(award.getTimes());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return awardArrayList.size();
        }

    }
}
