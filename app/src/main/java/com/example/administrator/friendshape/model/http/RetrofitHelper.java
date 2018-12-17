package com.example.administrator.friendshape.model.http;

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
import com.example.administrator.friendshape.model.http.api.MyApis;
import com.example.administrator.friendshape.model.http.response.MyHttpResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/10/30.
 */

public class RetrofitHelper implements HttpHelper {

    private MyApis mMyApiService;

    @Inject
    public RetrofitHelper(MyApis myApiService) {
        this.mMyApiService = myApiService;
    }


    @Override
    public Flowable<LoginInfoNetBean> fetchLogin(Map map) {
        return mMyApiService.login(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody) {
        return mMyApiService.UpCasePicData(multipartBody);
    }

    @Override
    public Flowable<UpLoadStatusNetBean> UpLoadStatusNetBean(Map map) {
        return mMyApiService.UpLoadStatus(map);
    }

    @Override
    public Flowable<MyHttpResponse<Object>> GetGeneralNetBean(Map map) {
        return mMyApiService.GetGeneralNetBean(map);
    }

    @Override
    public Flowable<HomePageNetBean> GetHomePageNetBean(Map map) {
        return mMyApiService.GetHomePageNetBean(map);
    }

    @Override
    public Flowable<HotSearchResNetBean> GetHotSearchResNetBean(Map map) {
        return mMyApiService.GetHotSearchResNetBean(map);
    }

    @Override
    public Flowable<MerchantsNetBean> GetMerchantsNetBean(Map map) {
        return mMyApiService.GetMerchantsNetBean(map);
    }

    @Override
    public Flowable<MerchantsTypeFormNetBean> GetMerchantsTypeFormNetBean(Map map) {
        return mMyApiService.GetMerchantsTypeFormNetBean(map);
    }

    @Override
    public Flowable<MerchantsContentsNetWorkBean> GetMerchantsContentsNetWorkBean(Map map) {
        return mMyApiService.GetMerchantsContentsNetWorkBean(map);
    }

    @Override
    public Flowable<GroupOrderNetBean> GetGroupOrderNetBean(Map map) {
        return mMyApiService.GetGroupOrderNetBean(map);
    }

    @Override
    public Flowable<OrderaAllTypeNetBean> GetOrderaAllTypeNetBean(Map map) {
        return mMyApiService.GetOrderaAllTypeNetBean(map);
    }

    @Override
    public Flowable<OredeContentNetBean> GetOredeContentNetBean(Map map) {
        return mMyApiService.GetOredeContentNetBean(map);
    }

    @Override
    public Flowable<FriendNetBean> GetFriendNetBean(Map map) {
        return mMyApiService.GetFriendNetBean(map);
    }

    @Override
    public Flowable<CancleGroupNetBean> GetCancleGroupNetBean(Map map) {
        return mMyApiService.GetCancleGroupNetBean(map);
    }

    @Override
    public Flowable<CallPhoneNetBean> GetCallPhoneNetBean(Map map) {
        return mMyApiService.GetCallPhoneNetBean(map);
    }

    @Override
    public Flowable<RefundNetBean> GetRefundNetBean(Map map) {
        return mMyApiService.GetRefundNetBean(map);
    }

    @Override
    public Flowable<DynamicNetBean> GetDynamicNetBean(Map map) {
        return mMyApiService.GetDynamicNetBean(map);
    }

    @Override
    public Flowable<PeopleNearbyNetBean> GetPeopleNearbyNetBean(Map map) {
        return mMyApiService.GetPeopleNearbyNetBean(map);
    }

    @Override
    public Flowable<UserDetailsNetBean> GetUserDetailsNetBean(Map map) {
        return mMyApiService.GetUserDetailsNetBean(map);
    }

    @Override
    public Flowable<GroupAboutNetBean> GetGroupAboutNetBean(Map map) {
        return mMyApiService.GetGroupAboutNetBean(map);
    }

    @Override
    public Flowable<SubmitATuxedoNetBean> GetSubmitATuxedoNetBean(Map map) {
        return mMyApiService.GetSubmitATuxedoNetBean(map);
    }

    @Override
    public Flowable<GroupContentNetBean> GetGroupContentNetBean(Map map) {
        return mMyApiService.GetGroupContentNetBean(map);
    }

    @Override
    public Flowable<PersonalContentNetBean> GetPersonalContentNetBean(Map map) {
        return mMyApiService.GetPersonalContentNetBean(map);
    }

    @Override
    public Flowable<VerificationCodeNetWork> GetVerificationCodeNetWork(Map map) {
        return mMyApiService.GetVerificationCodeNetWork(map);
    }

    @Override
    public Flowable<PraiseStatusNetBean> GetPraiseStatusNetBean(Map map) {
        return mMyApiService.GetPraiseStatusNetBean(map);
    }

    @Override
    public Flowable<OtherContentNetBean> GetOtherContentNetBean(Map map) {
        return mMyApiService.GetOtherContentNetBean(map);
    }

    @Override
    public Flowable<ZfbPayContentNetBean> GetZfbPayContentNetBean(Map map) {
        return mMyApiService.GetZfbPayContentNetBean(map);
    }

    @Override
    public Flowable<WechatPayContentNetBean> GetWechatPayContentNetBean(Map map) {
        return mMyApiService.GetWechatPayContentNetBean(map);
    }

    @Override
    public Flowable<ImUserContentNetBean> GetImUserContentNetBean(Map map) {
        return mMyApiService.GetImUserContentNetBean(map);
    }


}
