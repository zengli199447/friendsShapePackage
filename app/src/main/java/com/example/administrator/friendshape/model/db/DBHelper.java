package com.example.administrator.friendshape.model.db;



import com.example.administrator.friendshape.model.db.entity.AppDBInfo;
import com.example.administrator.friendshape.model.db.entity.LoginUserInfo;
import com.example.administrator.friendshape.model.db.entity.SearchDBInfo;
import com.example.administrator.friendshape.model.db.entity.SearchDBInfoDao;

import java.util.List;


/**
 * Created by Administrator on 2018/1/5.
 */

public interface DBHelper {

    //---------------------------条件查询---------------------------------------

    /**
     * 查询 LoginUserInfo数据
     *
     * @return
     */
    LoginUserInfo queryLoginUserInfo(String mUserName);

    /**
     * 查询 LoginUserInfo数据
     *
     * @return
     */
    List<SearchDBInfo> querySearchDBInfo(String mUserId);


    //---------------------------查询所有（无筛选条件）---------------------------

    /**
     * 查询所有 LoginUserInfo数据
     *
     * @return
     */
    List<LoginUserInfo> loadLoginUserInfo();

    /**
     * 查询所有 AppDBInfoDao数据
     *
     * @return
     */
    List<AppDBInfo> loadAppDBInfoDao();


    //---------------------------插入数据（更新数据）-----------------------------

    /**
     * 插入一条 LoginUserInfo数据
     *
     * @return
     */
    void insertLoginUserInfo(LoginUserInfo mLoginUserInfo);

    /**
     * 插入一条 AppDBInfo数据
     *
     * @return
     */
    void insertAppDBInfoDao(AppDBInfo appDBInfo);

    /**
     * 插入一条 SearchDBInfo数据
     *
     * @return
     */
    void insertSearchDBInfo(SearchDBInfo searchDBInfo);

    //---------------------------删除数据(条件删除)-------------------------------

    /**
     * 删除一个 LoginUserInfo数据
     *
     * @return
     */
    void deleteLoginUserInfo(String mUserName);

    /**
     * 删除一个 SearchDBInfo数据
     *
     * @return
     */
    void deleteSearchDBInfo(String UserId);

    //---------------------------修改数据()-------------------------------

    /**
     * 修改一条 LoginUserInfo数据
     *
     * @return
     */
    void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo);


}
