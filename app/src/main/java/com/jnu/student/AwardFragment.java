package com.jnu.student;


import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.student.data.DataSaver;

import java.util.ArrayList;

public class AwardFragment extends Fragment {
    private ArrayList<Award> awards = new ArrayList<>();
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
                    if(result.getResultCode()==NewAward.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        int achieveCount=bundle.getInt("achieveCount");
                        awards.add(0,new Award(title,achieveCount,"无","0/1"));
                        new DataSaver().Save(this.getContext(),awards);
                        awardAdapter.notifyItemInserted(0);
                    }
                }

            }
    );

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
        FloatingActionButton fabbutton = rootView.findViewById(R.id.button_ADD);
        fabbutton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setItems(new CharSequence[]{"新建奖励", "排序"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 根据选择执行相应的操作，比如跳转到另一个界面
                            if (which == 0) {
                                // 选择了选项0
                                Intent intent = new Intent(requireActivity(), NewAward.class);
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
