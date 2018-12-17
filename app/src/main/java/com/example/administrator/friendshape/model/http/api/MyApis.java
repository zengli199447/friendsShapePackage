package com.example.administrator.friendshape.model.http.api;


import com.example.administrator.friendshape.global.DataClass;
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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Created by Administrator on 2017/10/30.
 * 网络请求
 * 添加请求头可以统一添加  单一添加请查看操作符
 * 统一添加查看 di 目录下 httmModule
 */

public interface MyApis {

    String HOST = DataClass.URL;

    /**
     * 登录
     *
     * @return
     */
    @GET("api.mingfa.php?")
    Flowable<LoginInfoNetBean> login(@QueryMap Map<String, String> map);

    /**
     * 上传文件
     *
     * @return
     */
    @POST("")
    Flowable<OuterLayerEntity> UpCasePicData(@Body MultipartBody multipartBody);

    /**
     * 返回提交状态
     */
    @POST("api.mingfa.php?")
    Flowable<UpLoadStatusNetBean> UpLoadStatus(@QueryMap Map<String, String> map);

    /**
     * 返回通用数据
     */
    @POST("api.mingfa.php?")
    Flowable<MyHttpResponse<Object>> GetGeneralNetBean(@QueryMap Map<String, String> map);

    /**
     * 返回首页数据
     */
    @POST("api.mingfa.php?")
    Flowable<HomePageNetBean> GetHomePageNetBean(@QueryMap Map<String, String> map);

    /**
     * 返回热门索引单词字典
     */
    @POST("api.mingfa.php?")
    Flowable<HotSearchResNetBean> GetHotSearchResNetBean(@QueryMap Map<String, String> map);

    /**
     * 商户列表
     */
    @POST("api.mingfa.php?")
    Flowable<MerchantsNetBean> GetMerchantsNetBean(@QueryMap Map<String, String> map);

    /**
     * 商户类别
     */
    @POST("api.mingfa.php?")
    Flowable<MerchantsTypeFormNetBean> GetMerchantsTypeFormNetBean(@QueryMap Map<String, String> map);

    /**
     * 商户类别
     */
    @POST("api.mingfa.php?")
    Flowable<MerchantsContentsNetWorkBean> GetMerchantsContentsNetWorkBean(@QueryMap Map<String, String> map);

    /**
     * 发起组团
     */
    @POST("api.mingfa.php?")
    Flowable<GroupOrderNetBean> GetGroupOrderNetBean(@QueryMap Map<String, String> map);

    /**
     * 订单列表
     */
    @POST("api.mingfa.php?")
    Flowable<OrderaAllTypeNetBean> GetOrderaAllTypeNetBean(@QueryMap Map<String, String> map);

    /**
     * 订单详情
     */
    @POST("api.mingfa.php?")
    Flowable<OredeContentNetBean> GetOredeContentNetBean(@QueryMap Map<String, String> map);

    /**
     * 好友列表
     */
    @POST("api.mingfa.php?")
    Flowable<FriendNetBean> GetFriendNetBean(@QueryMap Map<String, String> map);

    /**
     * 取消订单信息
     */
    @POST("api.mingfa.php?")
    Flowable<CancleGroupNetBean> GetCancleGroupNetBean(@QueryMap Map<String, String> map);

    /**
     * 客服电话列表
     */
    @POST("api.mingfa.php?")
    Flowable<CallPhoneNetBean> GetCallPhoneNetBean(@QueryMap Map<String, String> map);

    /**
     * 退款详情
     */
    @POST("api.mingfa.php?")
    Flowable<RefundNetBean> GetRefundNetBean(@QueryMap Map<String, String> map);

    /**
     * 动态列表
     */
    @POST("api.mingfa.php?")
    Flowable<DynamicNetBean> GetDynamicNetBean(@QueryMap Map<String, String> map);

    /**
     * 附近人列表
     */
    @POST("api.mingfa.php?")
    Flowable<PeopleNearbyNetBean> GetPeopleNearbyNetBean(@QueryMap Map<String, String> map);

    /**
     * 用户信息
     */
    @POST("api.mingfa.php?")
    Flowable<UserDetailsNetBean> GetUserDetailsNetBean(@QueryMap Map<String, String> map);

    /**
     * 获取组团列表、分类
     */
    @POST("api.mingfa.php?")
    Flowable<GroupAboutNetBean> GetGroupAboutNetBean(@QueryMap Map<String, String> map);

    /**
     * 提交参团
     */
    @POST("api.mingfa.php?")
    Flowable<SubmitATuxedoNetBean> GetSubmitATuxedoNetBean(@QueryMap Map<String, String> map);

    /**
     * 组团信息
     */
    @POST("api.mingfa.php?")
    Flowable<GroupContentNetBean> GetGroupContentNetBean(@QueryMap Map<String, String> map);

    /**
     * 获取个人信息
     */
    @POST("api.mingfa.php?")
    Flowable<PersonalContentNetBean> GetPersonalContentNetBean(@QueryMap Map<String, String> map);

    /**
     * 获取验证码
     */
    @POST("api.mingfa.php?")
    Flowable<VerificationCodeNetWork> GetVerificationCodeNetWork(@QueryMap Map<String, String> map);

    /**
     * 获取验证码
     */
    @POST("api.mingfa.php?")
    Flowable<PraiseStatusNetBean> GetPraiseStatusNetBean(@QueryMap Map<String, String> map);

    /**
     * 说明性内容
     */
    @POST("api.mingfa.php?")
    Flowable<OtherContentNetBean> GetOtherContentNetBean(@QueryMap Map<String, String> map);

    /**
     * 支付宝支付订单详情
     */
    @POST("api.mingfa.php?")
    Flowable<ZfbPayContentNetBean> GetZfbPayContentNetBean(@QueryMap Map<String, String> map);

    /**
     * 微信支付订单详情
     */
    @POST("api.mingfa.php?")
    Flowable<WechatPayContentNetBean> GetWechatPayContentNetBean(@QueryMap Map<String, String> map);

    /**
     * Im 用户信息
     */
    @POST("api.mingfa.php?")
    Flowable<ImUserContentNetBean> GetImUserContentNetBean(@QueryMap Map<String, String> map);


}
