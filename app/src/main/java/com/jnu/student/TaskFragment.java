package com.jnu.student;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskFragment extends Fragment {
    private String []tabHeaderStrings = {"每日任务","每周任务","普通任务"};
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
        // 创建适配器
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        // 将TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
        ).attach();
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


}
