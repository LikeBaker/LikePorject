package com.like26th.likeproject.greendao;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.like26th.likeproject.R;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/14.
 */

public class GreenDaoActivity extends Activity {

    UserDao userDao;
    @BindView(R.id.result)
    TextView result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_greendao);
        ButterKnife.bind(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "jwthdb", null);
        //权限问题，不能从 data/data 中复制出来
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();

        User user = new User("2", "cookie");
        long insert = userDao.insert(user);
        Log.d("GreenDaoActivity", "insert:" + insert);
    }

    @OnClick(R.id.query)
    public void onClick() {
        QueryBuilder<User> qb = userDao.queryBuilder();
        List<User> list = qb.list();
        Log.d("GreenDaoActivity", "list.size():" + list.size());
        result.setText("查询结果:"+list.get(1).getName());
    }
}
