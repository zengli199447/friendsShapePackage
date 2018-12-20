package com.example.administrator.friendshape.global;

import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWIMKit;
import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.model.DataManager;
import com.example.administrator.friendshape.model.bean.LoginInfoNetBean;
import com.example.administrator.friendshape.model.bean.PersonalContentNetBean;
import com.example.administrator.friendshape.model.db.entity.LoginUserInfo;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class DataClass {

    private String TAG = getClass().getSimpleName();

    //TODO BaseUrl
    public static String URL = "http://mengyh.027perfect.com/api/";

    public static String File_URL = URL + "api.mingfa.php?";

    public static final String BOUNDARY = "--my_boundary--";

    //登陆类型
    public static int LOGINTYPE = 1;//登录类型 1/手机登录  2/qq登录  3/wechat登录 4/sina登录

    //获取验证码
    public static String GET_CODE = "get_code";
    //修改、找回密码
    public static String USER_PWD_SET = "user_pwd_set";
    //用户注册
    public static String USER_REGISTER_SET = "user_register_set";
    //第三方登录
    public static String USER_THIRDLOGIN_SET = "user_thirdlogin_set";
    //第三方登录手机绑定
    public static String PHONE_BIND_SET = "phone_bind_set";


    //登陆
    public static String LOGIN = "user_login_get";
    //首页数据获取
    public static String HOME_PAGE = "homepage_get";
    //Search检索
    public static String HOT_SEARCH_GET = "hot_search_get";
    //商户列表
    public static String SHOP_LIST_GET = "shop_list_get";
    //商户类别
    public static String SERVICECATEGORY_LIST_GET = "servicecategory_list_get";
    //商户详情
    public static String SHOP_DETAIL_GET = "shop_detail_get";
    //发起/修改组团
    public static String GROUP_SET = "group_set";
    //订单列表
    public static String ORDER_LIST_GET = "order_list_get";
    //订单详情
    public static String ORDER_DETAIL_GET = "order_detail_get";
    //好友列表、邀请好友
    public static String FRIEND_LIST_GET = "friend_list_get";
    //发送邀请
    public static String ORDER_INVITE_SET = "order_invite_set";
    //取消订单数据
    public static String ORDER_CANCEL_INIT_GET = "order_cancel_init_get";
    //取消订单提交
    public static String ORDER_CANCEL_SET = "order_cancel_set";
    //订单评价提交
    public static String ORDER_COMMENTS_SET = "order_comments_set";
    //客服电话
    public static String SERVICEPHONE_GET = "servicephone_get";
    //退款详情
    public static String ORDER_CANCEL_DETAIL_GET = "order_cancel_detail_get";
    //动态列表
    public static String NEWS_LIST_GET = "news_list_get";
    //点赞、取消
    public static String NEWS_ZAN_SET = "news_zan_set";
    //举报动态
    public static String NEWS_REPORT_SET = "news_report_set";
    //发送评论
    public static String REPLY_ADD_SET = "reply_add_set";
    //附近人
    public static String USER_LIST_GET = "user_list_get";
    //获取主页详情
    public static String OTHER_INFO_GET = "other_info_get";
    //组团列表
    public static String GROUP_LIST_GET = "group_list_get";
    //参团
    public static String GROUP_JOIN_SET = "group_join_set";
    //获取组团信息
    public static String SHOP_GROUP_INIT_GET = "shop_group_init_get";
    //获取个人信息
    public static String USER_INFO_GET = "user_info_get";
    //修改个人信息
    public static String USER_INFO_SET = "user_info_set";
    //图片保存
    public static String IMAGE_SAVE_SET = "image_save_set";
    //确认消费
    public static String ORDER_FINISH_SET = "order_finish_set";
    //发布动态
    public static String NEWS_SET = "news_set";
    //删除动态
    public static String NEWS_DEL_SET = "news_del_set";
    //说明性内容
    public static String TEXTINFO_GET = "textinfo_get";
    //支付信息
    public static String ORDER_PAY_SET = "order_pay_set";
    //相关信息
    public static String TEXTINFO_DETAIL_GET = "textinfo_detail_get";

    //IM 用户信息
    public static String USER_IMINFO_GET = "user_IMinfo_get";
    //IM 好友申请操作
    public static String FRIEND_CHECK_SET = "friend_check_set";
    //IM 申请好友
    public static String FRIEND_ADD_SET = "friend_add_set";


    //用户登陆ID
    public static String USERID = ""; //10007 10005
    //账号标示
    public static String STANDARD_USER = "admin";
    //用户名
    public static String UNAME = "小鸟游";
    //用户年龄
    public static String AGE = "";
    //用户电话
    public static String PHONE = "";
    //用户头像
    public static String USERPHOTO = "http://wx1.sinaimg.cn/orj360/006pnLoLgy1ft6yichmarj30j60j675x.jpg";
    //当前城市
    public static String CITY = "武汉市";
    //用户性别
    public static String GENDER = "女";
    //用户密码
    public static String PASSWORD = "";
    //相册集合
    public static ArrayList<AlbumFile> AlbumFileList = new ArrayList<>();
    //用户编辑动态内容
    public static String DYNAMICCONTENT = "";
    //动态页基础索引类型 (动态、附近人、组团)
    public static int INDEX_DYNAMIC;
    public static int INDEX_PEOPLE_NEAR;
    public static int INDEX_GROUP;

    //当前经纬度
    public static double LONGITUDE;
    public static double LATITUDE;

    public static int WINDOWS_WIDTH = 0;
    public static int WINDOWS_HEIGHT = 0;

    public static int DefaultInformationFlow = 11;

    //百川云旺
    public static YWIMKit mIMKit = null;

    public static YWIMCore imCore = null;

    public DataClass() {

    }

    public DataManager dataManager;

    public DataClass(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    //引导页
    public ArrayList<Integer> getWelcomeBannerList() {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(R.drawable.welcome1);
        integers.add(R.drawable.welcome2);
        integers.add(R.drawable.welcome3);
        return integers;
    }

    //保存/修改当前帐号信息
    public void DbCurrentUser(Object object) {
        if (object instanceof LoginInfoNetBean.ResultBean) {
            LoginInfoNetBean.ResultBean result = (LoginInfoNetBean.ResultBean) object;
            dataManager.insertLoginUserInfo(new LoginUserInfo(STANDARD_USER, result.getUserid(), result.getSecondname(), result.getPhone(),
                    result.getPwd(), result.getSex()));
        } else if (object instanceof PersonalContentNetBean.ResultBean) {
            PersonalContentNetBean.ResultBean result = (PersonalContentNetBean.ResultBean) object;
            dataManager.insertLoginUserInfo(new LoginUserInfo(STANDARD_USER, result.getUserid(), result.getSecondname(), result.getPhone(),
                    result.getPwd(), result.getSex()));
        }
    }

    //清空当前用户信息
    public static void ClearUserInfo(DataManager dataManager){
        DataClass.USERID = "";
        LoginUserInfo loginUserInfo = dataManager.queryLoginUserInfo(DataClass.STANDARD_USER);
        loginUserInfo.setUserid("");
        dataManager.UpDataLoginUserInfo(loginUserInfo);
    }

}
