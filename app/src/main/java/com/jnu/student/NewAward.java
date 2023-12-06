package com.jnu.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
//新建奖励界面

public class NewAward extends AppCompatActivity {
    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;
    private int number=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_award);

        // 初始化其他书籍信息的EditText
        EditText editTextTitle=findViewById(R.id.edit_title);
        EditText editTextAchieve=findViewById(R.id.edit_achieve);
        // 获取输入的数字
        String title = editTextTitle.getText().toString();
        String inputText = editTextAchieve.getText().toString();

        // 将字符串转换为整数
        try {
            number = Integer.parseInt(inputText);
            // 这里可以使用获取到的数字进行其他操作
        } catch (NumberFormatException e) {
            // 处理转换异常，如果用户没有输入数字
            e.printStackTrace();
        }

        // 设置添加按钮的点击事件
        Button doneButton = findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.isEmpty()||inputText.isEmpty()){
                    String message = "";
                    if (title.isEmpty()) {
                        message += "请输入标题 ";
                    }
                    if (inputText.isEmpty()) {
                        message += "请输入耗费的成就点数";
                    }
                    Toast.makeText(NewAward.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    // 创建一个包含书籍信息的Intent
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("标题", title);
                    bundle.putInt("成就点数", number);
                    bundle.putInt("position", position);

                    intent.putExtras(bundle);
                    setResult(RESULT_CODE_SUCCESS, intent);
                    NewAward.this.finish();
                }
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


    }

}
