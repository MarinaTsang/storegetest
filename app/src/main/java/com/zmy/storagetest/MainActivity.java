package com.zmy.storagetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zmy.storagetest.sqlite.MySqlHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件存储test
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    //    @Bind(R.id.edit)
//    EditText edit;
//    @Bind(R.id.db)
//    Button db;
    @BindView(R.id.db)
    Button db;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.check)
    Button check;
    @BindView(R.id.edit)
    EditText edit;
    private EditText mInputEdit;

    private Button mDbBtn;

    private MySqlHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mInputEdit = findViewById(R.id.edit);
        mDbBtn = findViewById(R.id.db);
        sqlHelper = new MySqlHelper(this, "News", null, 1);
        mDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelper.getReadableDatabase();
            }
        });


//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }


    public void addData() {
        SQLiteDatabase database = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("topic", "enjoygame");
        values.put("content", "has new game");
        values.put("price", 9.99);
        values.put("time", 1531794150);

        //添加数据
        database.insert("News", null, values);
        values.clear();

        //再次添加数据
        values.put("topic", "notification");
        values.put("content", "you win");
        values.put("price", 99.99);
        values.put("time", 1531794288);

        database.insert("News", null, values);

        Log.e(TAG, "onClick: add  success");
    }

    private void updateData() {

        SQLiteDatabase database = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", 0.99);

        //更新 数据          第三第四参数 表示更新某一行
        database.update("News", values,"topic=?",new String[]{"engjoygame"});
        Log.e(TAG, "onClick: update  success");
    }

    private void deleteData(){
        SQLiteDatabase database = sqlHelper.getWritableDatabase();

        database.delete("News","price>=?",new String[]{"9.99"});
    }

    private void queryData(){
        SQLiteDatabase database = sqlHelper.getWritableDatabase();
        //查询所有数据  :参数-->表名，查询第几列 ，查询条件（where语句，包括占位符），where语句占位符替代条件，
        Cursor cursor = database.query("News", null, null, null, null, null, null);
        if (null!=cursor){
            if (cursor.moveToFirst()){
                do {
                    //获取索引，根据索引获取值
                    String topic = cursor.getString(cursor.getColumnIndex("topic"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    Log.d(TAG, "queryData: "+topic+content);
                }while (cursor.moveToNext());
            }
            //关闭游标,避免资源泄漏
            cursor.close();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        String data = FIleUtils.load(this, "data");
        if (!TextUtils.isEmpty(data)) {
            mInputEdit.setText(data);
            mInputEdit.setSelection(data.length());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在页面销毁时保存输入的数据
        String s = mInputEdit.getText().toString();
        FIleUtils.save(this, "data", s);
    }

    @OnClick({R.id.db, R.id.add, R.id.delete, R.id.update, R.id.check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.db:
                break;
            case R.id.add:
                addData();
                break;
            case R.id.delete:
                deleteData();
                break;
            case R.id.update:
                updateData();
                break;
            case R.id.check:
                queryData();
                break;
        }
    }
}
