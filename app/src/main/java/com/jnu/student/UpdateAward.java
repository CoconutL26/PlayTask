package com.jnu.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
//新建奖励界面

public class UpdateAward extends AppCompatActivity {
    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;
    private int achievement=0;
    private String input_award_times;
    private String times;
    private EditText editTextTitle;
    private EditText editTextAchieve;
    private EditText editTextLabel;
    private Spinner spinner_award;
    private String[] options= {"单次", "不限"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_award);


//        editTextLabel=findViewById(R.id.edit_award_label);

        //加载已有的数据
        Intent intentBefore = getIntent();
        position = intentBefore.getIntExtra("position",0);
        String titleBefore = intentBefore.getStringExtra("title");
        int achievementBefore = intentBefore.getIntExtra("achievement",0);
        String labelBefore = intentBefore.getStringExtra("label");
        String timesBefore = intentBefore.getStringExtra("times");

        // 初始化其他书籍信息的EditText
        editTextTitle=findViewById(R.id.edit_award_title);
        editTextAchieve=findViewById(R.id.edit_award_achievement);


        //下拉框
        spinner_award = findViewById(R.id.spin_award_times);
        if(spinner_award != null) {

            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner_award.setAdapter(adapter);
        }

        if(null!=titleBefore) {
            editTextTitle.setText(titleBefore);
            editTextAchieve.setText(String.valueOf(achievementBefore));
            if(timesBefore.equals("0/1")){
                spinner_award.setSelection(0);
            }
            else{
                spinner_award.setSelection(1);
            }
        }else {
            spinner_award.setSelection(0);//默认选择
        }

        // 设置添加按钮的点击事件
        Button doneButton = findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取输入的数字
                String title = editTextTitle.getText().toString();
                String inputText = editTextAchieve.getText().toString();
                // 将字符串转换为整数
                try {
                    achievement = Integer.parseInt(inputText);
                    // 这里可以使用获取到的数字进行其他操作
                } catch (NumberFormatException e) {
                    // 处理转换异常，如果用户没有输入数字
                    Toast.makeText(UpdateAward.this, "请输入有效的成就点数", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }

                if(title.isEmpty()||inputText.isEmpty()){

                    String message = "";
                    if (title.isEmpty()) {
                        message += "请输入标题 ";
                    }
                    if (inputText.isEmpty()) {
                        message += "请输入耗费的成就点数";
                    }
                    Toast.makeText(UpdateAward.this, message, Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent resultintent = new Intent();
                Bundle bundle = new Bundle();

                // 创建一个包含书籍信息的Intent
                bundle.putInt("position",position);
                bundle.putString("title", title);
                bundle.putInt("achievement", achievement);
                if(input_award_times.equals("单次")){
                    times="0/1";
                }
                else if(input_award_times.equals("不限")){
                    times="0/∞";
                }
                bundle.putString("times",times);
//                bundle.putInt("position", position);
                // 设置位置信息（如果有的话）
                if (getIntent().hasExtra("position")) {
                    bundle.putInt("position", getIntent().getIntExtra("position", 0));
                }

                resultintent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS, resultintent);
                UpdateAward.this.finish();

            }
        });
        // 设置返回按钮的点击事件
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        //下拉框监听

        spinner_award.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 处理选择事件
                input_award_times=options[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

}
