package com.example.administrator.friendshape.model;

import com.example.administrator.friendshape.model.bean.CallPhoneNetBean;
import com.example.administrator.friendshape.model.bean.CancleGroupNetBean;
import com.example.administrator.friendshape.model.bean.DynamicNetBean;
import com.example.administrator.friendshape.model.bean.FriendNetBean;
import com.example.administrator.friendshape.model.bean.GroupAboutNetBean;
import com.example.administrator.friendshape.model.bean.GroupContentNetBean;
import com.example.administrator.friendshape.model.bean.GroupOrderNetBean;
import com.example.administrator.friendshape.model.bean.HomePageNetBean;
import com.example.administrator.friendshape.model.bean.HotSearchResNetBean;
import com.example.administrator.friendshape.model.bean.ImUserContentNetBean;
import com.example.administrator.friendshape.model.bean.LoginInfoNetBean;
import com.example.administrator.friendshape.model.bean.MerchantsContentsNetWorkBean;
import com.example.administrator.friendshape.model.bean.MerchantsNetBean;
import com.example.administrator.friendshape.model.bean.MerchantsTypeFormNetBean;
import com.example.administrator.friendshape.model.bean.OrderaAllTypeNetBean;
import com.example.administrator.friendshape.model.bean.OredeContentNetBean;
import com.example.administrator.friendshape.model.bean.OtherContentNetBean;
import com.example.administrator.friendshape.model.bean.OuterLayerEntity;
import com.example.administrator.friendshape.model.bean.PeopleNearbyNetBean;
import com.example.administrator.friendshape.model.bean.PersonalContentNetBean;
import com.example.administrator.friendshape.model.bean.PraiseStatusNetBean;
import com.example.administrator.friendshape.model.bean.RefundNetBean;
import com.example.administrator.friendshape.model.bean.SubmitATuxedoNetBean;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.bean.UserDetailsNetBean;
import com.example.administrator.friendshape.model.bean.VerificationCodeNetWork;
import com.example.administrator.friendshape.model.bean.WechatPayContentNetBean;
import com.example.administrator.friendshape.model.bean.ZfbPayContentNetBean;
import com.example.administrator.friendshape.model.db.DBHelper;
import com.example.administrator.friendshape.model.db.entity.AppDBInfo;
import com.example.administrator.friendshape.model.db.entity.LoginUserInfo;
import com.example.administrator.friendshape.model.db.entity.SearchDBInfo;
import com.example.administrator.friendshape.model.http.HttpHelper;
import com.example.administrator.friendshape.model.http.response.MyHttpResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;


/**
 * Created by Administrator on 2017/10/27.
 */

public class DataManager implements HttpHelper, DBHelper {
    String TAG = "DataManager";

    private HttpHelper mHttpHelper;
    private DBHelper mDbHelper;

    public DataManager(HttpHelper mHttpHelper, DBHelper mDbHelper) {
        this.mHttpHelper = mHttpHelper;
        this.mDbHelper = mDbHelper;
    }

    //---------------------------网络请求---------------------------------------

    @Override
    public Flowable<LoginInfoNetBean> fetchLogin(Map map) {
        return mHttpHelper.fetchLogin(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody) {
        return mHttpHelper.UpCasePicData(multipartBody);
    }

    @Override
    public Flowable<UpLoadStatusNetBean> UpLoadStatusNetBean(Map map) {
        return mHttpHelper.UpLoadStatusNetBean(map);
    }

    @Override
    public Flowable<MyHttpResponse<Object>> GetGeneralNetBean(Map map) {
        return mHttpHelper.GetGeneralNetBean(map);
    }

    @Override
    public Flowable<HomePageNetBean> GetHomePageNetBean(Map map) {
        return mHttpHelper.GetHomePageNetBean(map);
    }

    @Override
    public Flowable<HotSearchResNetBean> GetHotSearchResNetBean(Map map) {
        return mHttpHelper.GetHotSearchResNetBean(map);
    }

    @Override
    public Flowable<MerchantsNetBean> GetMerchantsNetBean(Map map) {
        return mHttpHelper.GetMerchantsNetBean(map);
    }

    @Override
    public Flowable<MerchantsTypeFormNetBean> GetMerchantsTypeFormNetBean(Map map) {
        return mHttpHelper.GetMerchantsTypeFormNetBean(map);
    }

    @Override
    public Flowable<MerchantsContentsNetWorkBean> GetMerchantsContentsNetWorkBean(Map map) {
        return mHttpHelper.GetMerchantsContentsNetWorkBean(map);
    }

    @Override
    public Flowable<GroupOrderNetBean> GetGroupOrderNetBean(Map map) {
        return mHttpHelper.GetGroupOrderNetBean(map);
    }

    @Override
    public Flowable<OrderaAllTypeNetBean> GetOrderaAllTypeNetBean(Map map) {
        return mHttpHelper.GetOrderaAllTypeNetBean(map);
    }

    @Override
    public Flowable<OredeContentNetBean> GetOredeContentNetBean(Map map) {
        return mHttpHelper.GetOredeContentNetBean(map);
    }

    @Override
    public Flowable<FriendNetBean> GetFriendNetBean(Map map) {
        return mHttpHelper.GetFriendNetBean(map);
    }

    @Override
    public Flowable<CancleGroupNetBean> GetCancleGroupNetBean(Map map) {
        return mHttpHelper.GetCancleGroupNetBean(map);
    }

    @Override
    public Flowable<CallPhoneNetBean> GetCallPhoneNetBean(Map map) {
        return mHttpHelper.GetCallPhoneNetBean(map);
    }

    @Override
    public Flowable<RefundNetBean> GetRefundNetBean(Map map) {
        return mHttpHelper.GetRefundNetBean(map);
    }

    @Override
    public Flowable<DynamicNetBean> GetDynamicNetBean(Map map) {
        return mHttpHelper.GetDynamicNetBean(map);
    }

    @Override
    public Flowable<PeopleNearbyNetBean> GetPeopleNearbyNetBean(Map map) {
        return mHttpHelper.GetPeopleNearbyNetBean(map);
    }

    @Override
    public Flowable<UserDetailsNetBean> GetUserDetailsNetBean(Map map) {
        return mHttpHelper.GetUserDetailsNetBean(map);
    }

    @Override
    public Flowable<GroupAboutNetBean> GetGroupAboutNetBean(Map map) {
        return mHttpHelper.GetGroupAboutNetBean(map);
    }

    @Override
    public Flowable<SubmitATuxedoNetBean> GetSubmitATuxedoNetBean(Map map) {
        return mHttpHelper.GetSubmitATuxedoNetBean(map);
    }

    @Override
    public Flowable<GroupContentNetBean> GetGroupContentNetBean(Map map) {
        return mHttpHelper.GetGroupContentNetBean(map);
    }

    @Override
    public Flowable<PersonalContentNetBean> GetPersonalContentNetBean(Map map) {
        return mHttpHelper.GetPersonalContentNetBean(map);
    }

    @Override
    public Flowable<VerificationCodeNetWork> GetVerificationCodeNetWork(Map map) {
        return mHttpHelper.GetVerificationCodeNetWork(map);
    }

    @Override
    public Flowable<PraiseStatusNetBean> GetPraiseStatusNetBean(Map map) {
        return mHttpHelper.GetPraiseStatusNetBean(map);
    }

    @Override
    public Flowable<OtherContentNetBean> GetOtherContentNetBean(Map map) {
        return mHttpHelper.GetOtherContentNetBean(map);
    }

    @Override
    public Flowable<ZfbPayContentNetBean> GetZfbPayContentNetBean(Map map) {
        return mHttpHelper.GetZfbPayContentNetBean(map);
    }

    @Override
    public Flowable<WechatPayContentNetBean> GetWechatPayContentNetBean(Map map) {
        return mHttpHelper.GetWechatPayContentNetBean(map);
    }

    @Override
    public Flowable<ImUserContentNetBean> GetImUserContentNetBean(Map map) {
        return mHttpHelper.GetImUserContentNetBean(map);
    }


    //---------------------------数据库查询---------------------------------------

    @Override
    public LoginUserInfo queryLoginUserInfo(String mUserName) {
        return mDbHelper.queryLoginUserInfo(mUserName);
    }

    @Override
    public List<SearchDBInfo> querySearchDBInfo(String mUserId) {
        return mDbHelper.querySearchDBInfo(mUserId);
    }

    @Override
    public List<LoginUserInfo> loadLoginUserInfo() {
        return mDbHelper.loadLoginUserInfo();
    }

    @Override
    public List<AppDBInfo> loadAppDBInfoDao() {
        return mDbHelper.loadAppDBInfoDao();
    }

    @Override
    public void insertLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.insertLoginUserInfo(mLoginUserInfo);
    }

    @Override
    public void insertAppDBInfoDao(AppDBInfo appDBInfo) {
        mDbHelper.insertAppDBInfoDao(appDBInfo);
    }

    @Override
    public void insertSearchDBInfo(SearchDBInfo searchDBInfo) {
        mDbHelper.insertSearchDBInfo(searchDBInfo);
    }

    @Override
    public void deleteLoginUserInfo(String mUserName) {
        mDbHelper.deleteLoginUserInfo(mUserName);
    }

    @Override
    public void deleteSearchDBInfo(String UserId) {
        mDbHelper.deleteSearchDBInfo(UserId);
    }

    @Override
    public void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.UpDataLoginUserInfo(mLoginUserInfo);
    }

}
