package com.example.administrator.friendshape.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.administrator.friendshape.global.DataClass;
import com.example.administrator.friendshape.global.MyApplication;
import com.example.administrator.friendshape.model.bean.UpLoadFileNetBean;
import com.example.administrator.friendshape.utils.LogUtil;
import com.example.administrator.friendshape.utils.SystemUtil;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：真理 Created by Administrator on 2018/11/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MultipartBuilder {

    String TAG = getClass().getSimpleName();

    Context context;
    private HashMap<String, String> textParams;
    private HashMap<String, File> fileparams;
    int flags;
    private List<String> stringList = new ArrayList<>();

    public MultipartBuilder(Context context, int flags) {
        this.context = context;
        this.flags = flags;
    }

    public void arrangementUpLoad() {
        LogUtil.e(TAG,"DataClass.AlbumFileList.size() : " + DataClass.AlbumFileList.size());
        for (int i = 0; i < DataClass.AlbumFileList.size(); i++) {
            commitFile(DataClass.AlbumFileList.get(i).getPath());
            LogUtil.e(TAG,"DataClass.AlbumFileList.get(i).getPath() : " + DataClass.AlbumFileList.get(i).getPath());
        }
    }

    public void commitFile(final String file) {

        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
//                    HashMap map = new HashMap<>();
//                    map.put("action", DataClass.IMAGE_SAVE_SET);
//                    URL url = new URL(new StringBuffer().append(DataClass.File_URL).append(new Gson().toJson(map)).toString());
                    URL url = new URL(DataClass.File_URL);
                    textParams = new HashMap<>();
                    fileparams = new HashMap<>();

                    File fileUrl = new File(file);
                    fileparams.put("image", fileUrl);

                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    linkedHashMap.put("action", DataClass.IMAGE_SAVE_SET);
                    String toJson = new Gson().toJson(linkedHashMap);
                    textParams.put("version", "v1");
                    textParams.put("vars", toJson);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setConnectTimeout(5000);

                    httpURLConnection.setDoOutput(true);

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setUseCaches(false);

                    httpURLConnection.setRequestProperty("ser-Agent", "Fiddler");

                    httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + DataClass.BOUNDARY);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                    writeStringParams(textParams, dataOutputStream);

                    writeFileParams(fileparams, dataOutputStream);

                    paramsEnd(dataOutputStream);

                    outputStream.close();

                    int responseCode = httpURLConnection.getResponseCode();
                    LogUtil.e(TAG, "");
                    if (responseCode == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        String result = new String(readBytes(inputStream));
                        switch (flags) {
                            case 0:
                                if (upLoadFileListener != null)
                                    upLoadFileListener.onUpLoadFileListener(GsonFormatNetData(result));
                                break;
                            case 1:
                                stringList.add(GsonFormatNetData(result));
                                if (stringList.size() == DataClass.AlbumFileList.size()) {
                                    if (upLoadFileListener != null)
                                        upLoadFileListener.onUpLoadFileListener(LineFormatNetData());
                                }
                                break;
                        }
                    } else {
                        LogUtil.e(TAG, "Exception - responseCode : " + responseCode);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, "MalformedURLException : " + e.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, "IOException : " + e.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, "Exception : " + e.toString());
                }
            }
        });

    }

    private String GsonFormatNetData(String result) {
        String url;
        UpLoadFileNetBean upLoadFileNetBean = new Gson().fromJson(result, UpLoadFileNetBean.class);
        if (upLoadFileNetBean.getStatus() == 1) {
            url = upLoadFileNetBean.getSrc();
        } else {
            url = null;
        }
        return url;
    }

    private String LineFormatNetData() {
        String fileList = "";
        String theComma = "";
        for (int i = 0; i < stringList.size(); i++) {
            if (i > 0) {
                theComma = ",";
            }
            fileList = new StringBuffer().append(fileList).append(theComma).append(stringList.get(i)).toString();
        }
        return fileList;
    }


    public BitmapFactory.Options getBitmapOptions(String srcPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        return options;
    }

    public void writeStringParams(Map<String, String> textParams, DataOutputStream dataOutputStream) throws Exception {
        Set<String> keySet = textParams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String name = it.next();
            String value = textParams.get(name);
            dataOutputStream.writeBytes("--" + DataClass.BOUNDARY + "\r\n");
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
            dataOutputStream.writeBytes("\r\n");
            value = value + "\r\n";
            dataOutputStream.write(value.getBytes());
        }
    }

    public void writeFileParams(Map<String, File> fileparams, DataOutputStream dataOutputStream) throws Exception {
        Set<String> keySet = fileparams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String name = it.next();
            File value = fileparams.get(name);
            dataOutputStream.writeBytes("--" + DataClass.BOUNDARY + "\r\n");
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\""
                    + URLEncoder.encode(value.getName(), "UTF-8") + "\"\r\n");
            dataOutputStream.writeBytes("Content-Type:application/octet-stream \r\n");
            dataOutputStream.writeBytes("\r\n");
            dataOutputStream.write(getBytes(value));
            dataOutputStream.writeBytes("\r\n");
        }
    }

    private byte[] getBytes(File file) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, n);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }

    public void paramsEnd(DataOutputStream ds) throws Exception {
        ds.writeBytes("--" + DataClass.BOUNDARY + "--" + "\r\n");
        ds.writeBytes("\r\n");
    }

    public byte[] readBytes(InputStream is) {
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface UpLoadFileListener {
        void onUpLoadFileListener(String url);
    }

    private UpLoadFileListener upLoadFileListener;

    public void setUpLoadFileListener(UpLoadFileListener upLoadFileListener) {
        this.upLoadFileListener = upLoadFileListener;
    }

}
