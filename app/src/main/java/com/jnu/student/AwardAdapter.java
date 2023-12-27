package com.jnu.student;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AwardAdapter extends RecyclerView.Adapter<AwardAdapter.ViewHolder> {
    @NonNull
    public static ArrayList<Award> awardArrayList;
    // 事件回调监听
    private OnItemClickListener onItemClickListener;

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
            contextMenu.add(0,AwardFragment.MENU_ID_UPDATE,getAdapterPosition(),"编辑");
            contextMenu.add(0,AwardFragment.MENU_ID_DELETE,getAdapterPosition(),"删除");
        }
    }
    // ① 定义点击回调接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = viewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(viewHolder.itemView, pos);
                }
            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return awardArrayList.size();
    }

}

