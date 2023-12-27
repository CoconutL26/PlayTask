package com.jnu.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
//新建奖励界面

public class NewTask extends AppCompatActivity {
    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;
    private int achieveCount=0;
    private String input_task_frequency;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // 初始化其他书籍信息的EditText
        EditText editTextTitle=findViewById(R.id.edit_task_title);
        EditText editTextAchieve=findViewById(R.id.edit_task_achieve);
        EditText editTextTimes=findViewById(R.id.edit_task_times);
//        EditText editTest

        //下拉框
        Spinner spinner_task = findViewById(R.id.spin_task_frequency);
        String[] options = {"每日任务", "每周任务","普通任务"};

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_task.setAdapter(adapter);

        // 设置添加按钮的点击事件
        Button doneButton = findViewById(R.id.button_task_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取输入的数字
                String title = editTextTitle.getText().toString();
                String inputText = editTextAchieve.getText().toString();
                String times = editTextTimes.getText().toString();
                // 将字符串转换为整数
                try {
                    achieveCount = Integer.parseInt(inputText);
                    // 这里可以使用获取到的数字进行其他操作
                } catch (NumberFormatException e) {
                    // 处理转换异常，如果用户没有输入数字
                    Toast.makeText(NewTask.this, "请输入有效的成就点数", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;

                }

                if(title.isEmpty()||inputText.isEmpty()){

                    String message = "";
                    if (title.isEmpty()) {
                        message += "请输入标题 ";
                    }
                    if (inputText.isEmpty()) {
                        message += "请输入成就点数";
                    }
                    Toast.makeText(NewTask.this, message, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(times.isEmpty()){
                    times="1";
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                // 创建一个包含书籍信息的Intent

                bundle.putString("title", title);
                bundle.putInt("achieveCount", achieveCount);
                bundle.putString("frequency",input_task_frequency);
                bundle.putInt("times",Integer.parseInt(times));
//                bundle.putInt("position", position);
                // 设置位置信息（如果有的话）
                if (getIntent().hasExtra("position")) {
                    bundle.putInt("position", getIntent().getIntExtra("position", 0));
                }

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS, intent);
                NewTask.this.finish();

            }
        });

        // 设置返回按钮的点击事件
        Button backButton = findViewById(R.id.button_task_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                // 设置位置信息（如果有的话）
                if (getIntent().hasExtra("position")) {
                    intent.putExtra("position", getIntent().getIntExtra("position", 0));
                }
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            }
        });
        //下拉框监听
        spinner_task.setSelection(0);//默认选择
        spinner_task.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 处理选择事件
                input_task_frequency=options[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}
