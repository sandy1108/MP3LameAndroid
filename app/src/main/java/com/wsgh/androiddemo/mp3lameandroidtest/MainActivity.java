package com.wsgh.androiddemo.mp3lameandroidtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wsgh.androidutils.mp3lame.MP3LameUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        initListener();
    }

    private void initListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 源文件路径
                String sourceFilePath = "";
                // 目标文件路径
                String destinationFilePath = "";
                // 转换音频文件为mp3文件
                new MP3LameUtils().raw2mp3(sourceFilePath,destinationFilePath);
            }
        });
    }
}
