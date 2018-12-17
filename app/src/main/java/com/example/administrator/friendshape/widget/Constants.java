package com.example.administrator.friendshape.widget;

import android.os.Environment;


import com.example.administrator.friendshape.global.MyApplication;

import java.io.File;


/**
 * Created by Administrator on 2017/10/30.
 */

public class Constants {
    //================= session ====================
    public static String SESSION_ID = "v1";


    //================= TYPE ====================
    public static final int HOME = 0;
    public static final int NEAR = 1;
    public static final int CHAT = 2;
    public static final int ORDER = 3;
    public static final int MINE = 4;

    //================= KEY ====================


    //================= PATH ====================

    public static final String PATH_DATA = MyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "";
    public static final String PATH_SDCARD_CAMARE = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator;
    public static final String PATH_IMAGE = PATH_SDCARD + File.separator + "image";
    public static final String PATH_RECORD = PATH_SDCARD + File.separator + "record";

    //================= PREFERENCE ====================


    //====================DB==========================

    public static final String DATABASE_USER_NAME = MyApplication.getInstance().getPackageName() + " - " + "job.db";
    public static final int TYPE_TOP_CENTER = 0;
    public static final int TYPE_SUB_CENTER = 1;
    public static final int TYPE_SUB_CENTER_1 = 2;
    public static final int TYPE_SUB_CENTER_2 = 3;
    public static final int TYPE_SUB_CENTER_3 = 4;
    public static final int TYPE_SUB_CENTER_4 = 5;
    public static final int TYPE_SUB_CENTER_5 = 6;
    public static final int TYPE_VIDEO = 10;

    //================= INTENT ====================

}
