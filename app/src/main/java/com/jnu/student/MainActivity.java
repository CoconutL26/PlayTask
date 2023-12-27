package com.jnu.student;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;

    private String[] tabHeaderStrings = {"任务", "奖励", "我"};
    private static final int NOTIFICATION_ID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取ViewPager2和TabLayout的实例
        viewPager = findViewById(R.id.view_pager);
        viewPager.setUserInputEnabled(false);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // 创建适配器
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        // 将TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
        ).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    viewPager.setUserInputEnabled(false);
                }
                if (position == 1) {
                    viewPager.setUserInputEnabled(true);
                }
                super.onPageSelected(position);
            }
        });

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
                    return TaskFragment.newInstance();
                case 1:
                    return AwardFragment.newInstance();
                case 2:
                    return MeFragment.newInstance();

            }
            return TaskFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return NUM_TABS;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onPause() {
        super.onPause();
//        TaskSaver taskSaver = new TaskSaver();
//        ArrayList<Award> awards = AwardFragment.newInstance().awards;
//        ArrayList<Task> tasks = DayTaskFragment.newInstance().tasks;
//        taskSaver.Save(this, tasks);
//        DataSaver.Save(this,awards);
//        if (tasks != null) {
//            boolean notice_number = tasks.get(0).getIsCheck();
//            for (int i = 1; i < tasks.size(); i++) {
//                notice_number = notice_number || tasks.get(i).getIsCheck();
//            }
//            if (notice_number) {
//                sendNotification();
//            }
//        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification() {
        //1、NotificationManager
        NotificationManager manager = (NotificationManager)getSystemService(this.NOTIFICATION_SERVICE);
        /** 2、Builder->Notification
         *  必要属性有三项
         *  小图标，通过 setSmallIcon() 方法设置
         *  标题，通过 setContentTitle() 方法设置
         *  内容，通过 setContentText() 方法设置*/
        Notification.Builder builder = new android.app.Notification.Builder(this,"001");
        builder.setContentText("提醒您今天有任务要做噢！")//设置通知内容
                .setContentTitle("PlayTask任务")//设置通知标题
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher_round)//不能缺少的一个属性
                .setSubText("任务")
//                .setTicker("滚动消息......")
                .setWhen(System.currentTimeMillis());//设置通知时间，默认为系统发出通知的时间，通常不用设置
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("001","my_channel",NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            manager.createNotificationChannel(channel);
            builder.setChannelId("001");
        }
        Notification n = builder.build();
        //3、manager.notify()
        manager.notify(NOTIFICATION_ID,n);

    }



}



