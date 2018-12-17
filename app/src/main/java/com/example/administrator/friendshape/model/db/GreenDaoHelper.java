package com.example.administrator.friendshape.model.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.friendshape.global.MyApplication;
import com.example.administrator.friendshape.model.db.entity.AppDBInfo;
import com.example.administrator.friendshape.model.db.entity.AppDBInfoDao;
import com.example.administrator.friendshape.model.db.entity.DaoMaster;
import com.example.administrator.friendshape.model.db.entity.DaoSession;
import com.example.administrator.friendshape.model.db.entity.LoginUserInfo;
import com.example.administrator.friendshape.model.db.entity.LoginUserInfoDao;
import com.example.administrator.friendshape.model.db.entity.SearchDBInfo;
import com.example.administrator.friendshape.model.db.entity.SearchDBInfoDao;
import com.example.administrator.friendshape.widget.Constants;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by Administrator on 2018/2/11.
 */

public class GreenDaoHelper implements DBHelper {

    private DaoSession mDaoSession;
    private final AppDBInfoDao appDBInfoDao;
    private final LoginUserInfoDao loginUserInfoDao;
    private final SearchDBInfoDao searchDBInfoDao;

    @Inject
    public GreenDaoHelper() {
        //初始化数据库
        setupDatabase();
        loginUserInfoDao = mDaoSession.getLoginUserInfoDao();
        appDBInfoDao = mDaoSession.getAppDBInfoDao();
        searchDBInfoDao = mDaoSession.getSearchDBInfoDao();

    }

    private void setupDatabase() {
        //创建数据库,DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getInstance().getApplicationContext(), Constants.DATABASE_USER_NAME, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象,DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者,DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
        mDaoSession = daoMaster.newSession();
    }

    @Override
    public LoginUserInfo queryLoginUserInfo(String mUserName) {
        mDaoSession.clear();
        return loginUserInfoDao.queryBuilder().where(LoginUserInfoDao.Properties.Username.eq(mUserName)).build().unique();
    }

    @Override
    public List<SearchDBInfo> querySearchDBInfo(String mUserId) {
        mDaoSession.clear();
        return searchDBInfoDao.queryBuilder().where(SearchDBInfoDao.Properties.UserId.eq(mUserId)).build().list();
    }

    @Override
    public List<LoginUserInfo> loadLoginUserInfo() {
        mDaoSession.clear();
        return loginUserInfoDao.loadAll();
    }

    @Override
    public List<AppDBInfo> loadAppDBInfoDao() {
        mDaoSession.clear();
        return appDBInfoDao.loadAll();
    }

    @Override
    public void insertLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        loginUserInfoDao.insertOrReplace(mLoginUserInfo);
    }

    @Override
    public void insertAppDBInfoDao(AppDBInfo appDBInfo) {
        appDBInfoDao.insertOrReplace(appDBInfo);
    }

    @Override
    public void insertSearchDBInfo(SearchDBInfo searchDBInfo) {
        searchDBInfoDao.insert(searchDBInfo);
    }

    @Override
    public void deleteLoginUserInfo(String mUserName) {
        LoginUserInfo loginUserInfo = loginUserInfoDao.queryBuilder().where(LoginUserInfoDao.Properties.Username.eq(mUserName)).build().unique();
        if (loginUserInfo != null)
            loginUserInfoDao.delete(loginUserInfo);
    }

    @Override
    public void deleteSearchDBInfo(String UserId) {
        mDaoSession.clear();
        List<SearchDBInfo> list = searchDBInfoDao.queryBuilder().where(SearchDBInfoDao.Properties.UserId.eq(UserId)).build().list();
        for (SearchDBInfo searchDBInfo : list) {
            if (searchDBInfo != null)
                searchDBInfoDao.delete(searchDBInfo);
        }
    }

    @Override
    public void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        loginUserInfoDao.insertOrReplace(mLoginUserInfo);
    }

}
