package com.example.administrator.friendshape.ui.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.friendshape.R;
import com.example.administrator.friendshape.base.ControllerClassObserver;
import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.model.bean.UpLoadStatusNetBean;
import com.example.administrator.friendshape.model.event.CommonEvent;
import com.example.administrator.friendshape.model.event.EventCode;
import com.example.administrator.friendshape.rxtools.RxUtil;
import com.example.administrator.friendshape.ui.adapter.DynamicPhotoAdapter;
import com.example.administrator.friendshape.ui.dialog.ShowDialog;
import com.example.administrator.friendshape.ui.view.GridRecyclerView;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.widget.AlbumBuilder;
import com.example.administrator.friendshape.widget.CommonSubscriber;
import com.example.administrator.friendshape.widget.FullyGridLayoutManager;
import com.example.administrator.friendshape.widget.ViewBuilder;
import com.google.gson.Gson;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerReleaseNewDynamic extends ControllerClassObserver implements DynamicPhotoAdapter.PhotoClickListener {

    RecyclerView recycler_view;
    private DynamicPhotoAdapter dynamicPhotoAdapter;
    private AlbumBuilder albumBuilder;
    private ShowDialog instance;
    private int deletPosition;

    public ControllerReleaseNewDynamic(RecyclerView recycler_view) {
        this.recycler_view = recycler_view;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.PHOTO_REFRESH:
                refreshAdapter();
                break;
            case EventCode.PHOTO_SAVE:
                DataClass.AlbumFileList.remove(deletPosition);
                refreshAdapter();
                break;
        }
    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        albumBuilder = new AlbumBuilder(context);
        instance = ShowDialog.getInstance();
        initAdapter();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context,false,3));
        dynamicPhotoAdapter = new DynamicPhotoAdapter(context, DataClass.AlbumFileList);
        recycler_view.setAdapter(dynamicPhotoAdapter);
        dynamicPhotoAdapter.setPhotoClickListener(this);
    }

    private void refreshAdapter() {
        dynamicPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void AddClickListener() {
        albumBuilder.ImageSelection(9);
    }

    @Override
    public void PhotoClickListener(int positon) {
        ArrayList<String> strings = new ArrayList<>();
        for (AlbumFile albumFile : DataClass.AlbumFileList) {
            strings.add(albumFile.getPath());
        }
        albumBuilder.ImageTheExhibition(strings, true, positon);
    }

    @Override
    public void DeletPhotoClickListener(int positon) {
        deletPosition = positon;
        ShowOrSelect(EventCode.PHOTO_OR_REMOVER);
    }

    /**
     * 提示
     *
     * @param type (内容保存/图片删除)
     */
    public void ShowOrSelect(int type) {
        switch (type) {
            case EventCode.DYNAMIC_OR_SAVE:
                instance.showConfirmOrNoDialog(context, context.getString(R.string.dynamic_or_save), EventCode.ONSTART, EventCode.DYNAMIC_SAVE, EventCode.DYNAMIC_CANCLE);
                break;
            case EventCode.PHOTO_OR_REMOVER:
                instance.showConfirmOrNoDialog(context, context.getString(R.string.photo_or_remover), EventCode.ONSTART, EventCode.PHOTO_SAVE, EventCode.PHOTO_CANCLE);
                break;
        }
    }

    /**
     * 发布动态
     *
     * @param photoList 照片集  英文","隔开
     * @param about     动态文本
     */
    public void personalChangeNetWork(String photoList, String about) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("imgs", photoList);
        linkedHashMap.put("content", about);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            LogUtil.e(TAG, "发布成功");
                            DataClass.AlbumFileList.clear();
                            DataClass.DYNAMICCONTENT = "";
                            if (upLoadNetWorkListener != null)
                                upLoadNetWorkListener.onUpLoadNetWorkListener();
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.release_successful), EventCode.RELEASE_SUCCESSFUL);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        if (upLoadNetWorkListener != null)
                            upLoadNetWorkListener.onUpLoadNetWorkListener();
                        super.onError(e);
                    }
                }));
    }

    public interface UpLoadNetWorkListener {
        void onUpLoadNetWorkListener();
    }

    private UpLoadNetWorkListener upLoadNetWorkListener;

    public void setUpLoadNetWorkListener(UpLoadNetWorkListener upLoadNetWorkListener) {
        this.upLoadNetWorkListener = upLoadNetWorkListener;
    }

}
