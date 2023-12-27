package com.jnu.student;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public  class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    public static final int MENU_ID_UPDATE = 1;
    public static final int MENU_ID_DELETE = 2;
    protected  int notice_number = 0;
    @NonNull
    private ArrayList<Task> taskArrayList;




    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {

        private final TextView taskTitleView;
        private final TextView taskAchieveView;
        private final TextView taskTimes;
        private final ImageButton noticeButton;
        private final CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            taskAchieveView = itemView.findViewById(R.id.text_view_task_achieve);
            taskTitleView = itemView.findViewById(R.id.text_view_task_title);
            taskTimes = itemView.findViewById(R.id.text_view_task_times);
            noticeButton = itemView.findViewById(R.id.notice_button);
            checkBox = itemView.findViewById(R.id.checkBox_task);


            itemView.setOnCreateContextMenuListener(this);
        }

        public TextView getTextViewTitle() {
            return taskAchieveView;
        }

        public TextView getImageViewImage() {
            return taskTitleView;
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view
                , ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"添加提醒");
            contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"删除");

        }
        private ArrayList<Task> tasks = new ArrayList<>();

        public void Check(Task task){
            if(task.getIsCheck()) {
                noticeButton.setImageResource(R.drawable.pin_checked);
            }else {
                noticeButton.setImageResource(R.drawable.pin_unchecked);

            }
            // 设置CheckBox的监听器
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // 处理CheckBox状态变化事件
                int done_times = task.getDone_times();
                int gain_achievement = task.getAchievement();
                if (isChecked) {
                    // CheckBox被选中
                    done_times++;
                    task.setDone_times(done_times);
                    TaskFragment.total_score += gain_achievement;
                    if(task.getTimes()>1){
                        isChecked=false;
                    }
                }else{
                    done_times--;
                    task.setDone_times(done_times);
                    TaskFragment.total_score -= gain_achievement;
                }
                TaskFragment.total_score_text.setText(String.valueOf(TaskFragment.total_score));
                taskTimes.setText(String.valueOf(task.getDone_times())+"/"+String.valueOf(task.getTimes()));
//                TaskFragment.newInstance().changeColor();
//                if(TaskFragment.total_score<0){
//                    TaskFragment.total_score_text.setTextColor(ContextCompat.getColor(TaskFragment.total_score_text.getContext(),android.R.color.holo_red_light));
//                    AwardFragment.total_score_text.setTextColor(ContextCompat.getColor(TaskFragment.total_score_text.getContext(),android.R.color.holo_red_light));
//                }else {
//                    TaskFragment.total_score_text.setTextColor(ContextCompat.getColor(TaskFragment.total_score_text.getContext(), android.R.color.black));
////                    AwardFragment.total_score_text.setTextColor(ContextCompat.getColor(TaskFragment.total_score_text.getContext(), android.R.color.black));
//                }
            });
            noticeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    if(task.getIsCheck()) {
                        noticeButton.setImageResource(R.drawable.pin_unchecked);
                        task.setIsCheck(false);
                        notice_number--;
                    }else {
                        noticeButton.setImageResource(R.drawable.pin_checked);
                        task.setIsCheck(true);
                        notice_number++;
                    }

                }
            });

        }
    }
    public TaskAdapter(ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
    }


    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tasklayout, viewGroup, false);

        return new TaskAdapter.ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder viewHolder, final int position) {
        Task task = taskArrayList.get(position);
        viewHolder.Check(task);
        viewHolder.taskTitleView.setText(task.getTitle());
        String achievementText = String.valueOf(task.getAchievement());
        viewHolder.taskAchieveView.setText("+"+achievementText);
        viewHolder.taskTimes.setText(String.valueOf(task.getDone_times())+"/"+String.valueOf(task.getTimes()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

}