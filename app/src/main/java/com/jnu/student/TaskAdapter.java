package com.jnu.student;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public  class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    public static final int MENU_ID_UPDATE = 1;
    public static final int MENU_ID_DELETE = 2;
    protected  int notice_number = 0;
    private Context context;
    @NonNull
    private ArrayList<Task> taskArrayList;
    public boolean isNotice = false;



    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {

        private final TextView taskTitleView;
        private final TextView taskAchieveView;
        private final TextView taskTimes;
        private final ImageButton noticeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            taskAchieveView = itemView.findViewById(R.id.text_view_task_achieve);
            taskTitleView = itemView.findViewById(R.id.text_view_task_title);
            taskTimes = itemView.findViewById(R.id.text_view_task_times);
            noticeButton = itemView.findViewById(R.id.notice_button);


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
    public TaskAdapter(Context context, ArrayList<Task> taskArrayList) {
        this.context = context;
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
        viewHolder.taskTitleView.setText(task.getTitle());
        String achievementText = String.valueOf((-1)*task.getAchievement());
        viewHolder.taskAchieveView.setText(achievementText);
        viewHolder.taskTimes.setText(task.getTimes());
        viewHolder.Check(task);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

}