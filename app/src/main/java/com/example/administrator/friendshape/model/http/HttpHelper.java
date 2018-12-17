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
import com.example.administrator.friendshape.model.http.response.MyHttpResponse;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;


/**
 * Created by Administrator on 2017/10/27.
 */


public interface HttpHelper {

    Flowable<LoginInfoNetBean> fetchLogin(Map map);

    Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody);

    Flowable<UpLoadStatusNetBean> UpLoadStatusNetBean(Map map);

    Flowable<MyHttpResponse<Object>> GetGeneralNetBean(Map map);

    Flowable<HomePageNetBean> GetHomePageNetBean(Map map);

    Flowable<HotSearchResNetBean> GetHotSearchResNetBean(Map map);

    Flowable<MerchantsNetBean> GetMerchantsNetBean(Map map);

    Flowable<MerchantsTypeFormNetBean> GetMerchantsTypeFormNetBean(Map map);

    Flowable<MerchantsContentsNetWorkBean> GetMerchantsContentsNetWorkBean(Map map);

    Flowable<GroupOrderNetBean> GetGroupOrderNetBean(Map map);

    Flowable<OrderaAllTypeNetBean> GetOrderaAllTypeNetBean(Map map);

    Flowable<OredeContentNetBean> GetOredeContentNetBean(Map map);

    Flowable<FriendNetBean> GetFriendNetBean(Map map);

    Flowable<CancleGroupNetBean> GetCancleGroupNetBean(Map map);

    Flowable<CallPhoneNetBean> GetCallPhoneNetBean(Map map);

    Flowable<RefundNetBean> GetRefundNetBean(Map map);

    Flowable<DynamicNetBean> GetDynamicNetBean(Map map);

    Flowable<PeopleNearbyNetBean> GetPeopleNearbyNetBean(Map map);

    Flowable<UserDetailsNetBean> GetUserDetailsNetBean(Map map);

    Flowable<GroupAboutNetBean> GetGroupAboutNetBean(Map map);

    Flowable<SubmitATuxedoNetBean> GetSubmitATuxedoNetBean(Map map);

    Flowable<GroupContentNetBean> GetGroupContentNetBean(Map map);

    Flowable<PersonalContentNetBean> GetPersonalContentNetBean(Map map);

    Flowable<VerificationCodeNetWork> GetVerificationCodeNetWork(Map map);

    Flowable<PraiseStatusNetBean> GetPraiseStatusNetBean(Map map);

    Flowable<OtherContentNetBean> GetOtherContentNetBean(Map map);

    Flowable<ZfbPayContentNetBean> GetZfbPayContentNetBean(Map map);

    Flowable<WechatPayContentNetBean> GetWechatPayContentNetBean(Map map);

    Flowable<ImUserContentNetBean> GetImUserContentNetBean(Map map);


}
