package com.example.murphysl.imtest.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.murphysl.imtest.R;
import com.example.murphysl.imtest.bean.UserBean;
import com.example.murphysl.imtest.model.UserModel;
import com.example.murphysl.imtest.util.CacheUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * SelectPicPopupWindow
 *
 * @author: lenovo
 * @time: 2016/9/12 13:33
 */

public class SelectPicPopupWindow extends Activity implements View.OnClickListener {

    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private LinearLayout layout;

    private String dateTime;
    private static final int REQUEST_CODE_CAMERA = 2;
    UserBean user = UserModel.getInstance().getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        btn_take_photo = (Button) this.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (Button) this.findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) this.findViewById(R.id.btn_cancel);

        layout=(LinearLayout)findViewById(R.id.pop_layout);

        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //添加按钮监听
        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:
                Date date = new Date(System.currentTimeMillis());
                dateTime = date.getTime() + "";

                File f = new File(CacheUtils.getCacheDirectory(SelectPicPopupWindow.this, true, "pic") + dateTime);
                if (f.exists()) {
                    f.delete();
                }
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.fromFile(f);
                Log.e("uri", uri + "");

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(camera, REQUEST_CODE_CAMERA);
                break;
            case R.id.btn_pick_photo:
                startActivityForResult(new Intent(SelectPicPopupWindow.this , ImageActivity.class) , 3);
                break;
            case R.id.btn_cancel:
                finish();
                break;
            default:
                break;
        }
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String targeturl = null;
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    uploadAvatar(targeturl);
                    break;
                case 3:
                    targeturl = data.getStringArrayListExtra("imgs").get(0);
                    Log.i("SPP" , "-----><" + targeturl);
                    uploadAvatar(targeturl);
                    break;
            }
        }
    }

    private Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(SelectPicPopupWindow.this, true, "pic")
                + dateTime + "_11.jpg";
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("SPP" , file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    public void uploadAvatar(String targeturl){
        File file = null;
        if(targeturl == null){
            String files = CacheUtils.getCacheDirectory(SelectPicPopupWindow.this, true, "pic") + dateTime;
            file = new File(files);
            Bitmap bitmap = compressImageFromFile(files);
            targeturl = saveToSdCard(bitmap);
        }else{
            file = new File(targeturl);
        }

        if (file.exists()) {
            Log.i("SPP" , "----->" + targeturl);
            final BmobFile bfile = new BmobFile(new File(targeturl));
            bfile.upload(SelectPicPopupWindow.this, new UploadFileListener() {
                @Override
                public void onSuccess() {
                    String url = bfile.getFileUrl(SelectPicPopupWindow.this);
                    user.setAvatar(url);
                    user.update(SelectPicPopupWindow.this, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(SelectPicPopupWindow.this ,"原图已上传至云端" , Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(SelectPicPopupWindow.this ,"图片上传失败" , Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(SelectPicPopupWindow.this ,"头像上传错误" + s , Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
