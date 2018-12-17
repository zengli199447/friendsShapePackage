package com.example.administrator.friendshape.widget;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.example.administrator.friendshape.base.BaseLifecycleObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxBus;
import com.example.administrator.friendshape.utils.LogUtil;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class AlbumBuilder {

    String TAG = getClass().getSimpleName();
    Context context;

    public AlbumBuilder(Context context) {
        this.context = context;
    }

    /**
     * 单选
     */
    public void ImageSingleSelection() {
        Album.image(context).singleChoice().camera(true).columnCount(3).onResult(new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                String theAssignment = result.get(0).getPath();
                DataClass.USERPHOTO = theAssignment;
                RxBus.getDefault().post(new CommonEvent(EventCode.PICTURE, theAssignment));
                LogUtil.e(TAG, "albumFile : " + "file://" + theAssignment);
            }
        }).onCancel(new Action<String>() {
            @Override
            public void onAction(@NonNull String result) {
            }
        }).start();


    }

    /**
     * 多选
     *
     * @param count 数量限制
     */
    public void ImageSelection(int count) {
        Album.image(context).multipleChoice().camera(true).columnCount(3).selectCount(count).checkedList(DataClass.AlbumFileList)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        DataClass.AlbumFileList.clear();
                        DataClass.AlbumFileList.addAll(result);
                        RxBus.getDefault().post(new CommonEvent(EventCode.PHOTO_REFRESH));
                    }
                }).onCancel(new Action<String>() {
            @Override
            public void onAction(@NonNull String result) {
            }
        }).start();
    }

    /**
     * 画廊
     *
     * @param imageList   画廊数据集
     * @param isCheckable 是否可以操作(正反选)
     * @param position    当前查看下标
     */
    public void ImageTheExhibition(ArrayList<String> imageList, final boolean isCheckable, int position) {
        Album.gallery(context).currentPosition(position).checkedList(imageList).checkable(isCheckable)
                .onResult(new Action<ArrayList<String>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<String> result) {
                        if (isCheckable) {
                            DataClass.AlbumFileList.clear();
                            for (String s : result) {
                                AlbumFile albumFile = new AlbumFile();
                                albumFile.setPath(s);
                                DataClass.AlbumFileList.add(albumFile);
                            }
                            RxBus.getDefault().post(new CommonEvent(EventCode.PHOTO_REFRESH));
                        }
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

}
