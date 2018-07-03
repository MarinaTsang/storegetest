package com.zmy.storagetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * 文件存储test
 */
public class MainActivity extends AppCompatActivity {

    private EditText mInputEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputEdit = findViewById(R.id.edit);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String data = FIleUtils.load(this, "data");
        if (!TextUtils.isEmpty(data)){
            mInputEdit.setText(data);
            mInputEdit.setSelection(data.length());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在页面销毁时保存输入的数据
        String s = mInputEdit.getText().toString();
        FIleUtils.save(this,"data",s);
    }
}
