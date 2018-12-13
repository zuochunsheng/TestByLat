package com.android.superplayer.ui.activity.test;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.android.superplayer.R;
import com.android.superplayer.base.BaseActivity;
import com.android.superplayer.config.LogUtil;
import com.king.view.giftsurfaceview.GiftSurfaceView;
import com.king.view.giftsurfaceview.util.PointUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class giftActivity extends BaseActivity {

    @BindView(R.id.btnRandom)
    Button btnRandom;
    @BindView(R.id.btnV)
    Button btnV;
    @BindView(R.id.btnHeart)
    Button btnHeart;
    @BindView(R.id.btnLove)
    Button btnLove;
    @BindView(R.id.btnSmile)
    Button btnSmile;
    @BindView(R.id.btnX)
    Button btnX;
    @BindView(R.id.btn520)
    Button btn520;
    @BindView(R.id.btn1314)
    Button btn1314;

    @BindView(R.id.llBtn)
    LinearLayout llBtn;
    @BindView(R.id.hsl)
    HorizontalScrollView hsl;
    @BindView(R.id.frame)
    FrameLayout frame;


    /** 随机 */
    public static final int RANDOM = 0X0001;
    /** V型 */
    public static final int V = 0X0002;
    /** 心型 */
    public static final int HEART = 0X0003;
    /** 笑脸 */
    public static final int SMILE = 0X0004;
    /** LOVE */
    public static final int LOVE = 0X0005;
    /** 比翼双飞  */
    public static final int X = 0X0006;
    /** 一见钟情  */
    public static final int V520 = 0X0007;
    /** 一生一世  */
    public static final int V1314 = 0X0008;

    private static final String ASSET_HEART = "assets/json/heart.json";
    private static final String ASSET_V = "assets/json/v.json";
    private static final String ASSET_LOVE = "assets/json/love.json";
    private static final String ASSET_SMILE = "assets/json/smile.json";
    private static final String ASSET_X = "assets/json/x.json";
    private static final String ASSET_V520 = "assets/json/v520.json";
    private static final String ASSET_V1314 = "assets/json/v1314.json";




    private Bitmap bitmap;

    private Context context;

    private int width,height;


    @Override
    protected int getLayoutId() {
        return R.layout.giftlayout;
    }

    @Override
    protected void initViewAndData() {
        context = this;

        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;


        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.rose);

        //updateGiftSurfaceView(RANDOM);
    }


    public void updateGiftSurfaceView(int type){

        frame.removeAllViews();

        GiftSurfaceView giftSurfaceView = new GiftSurfaceView(context);
        if(type == RANDOM){
            giftSurfaceView.setImageResource(R.drawable.rose);
        }else{
            giftSurfaceView.setImageBitmap(bitmap,0.5f);
        }

        giftSurfaceView.setPointScale(1,width/10,(int)(height/3.8f));
        giftSurfaceView.setRunTime(10000);

        try {

            switch (type){
                case RANDOM:
                    giftSurfaceView.setRandomPoint(9);
                    break;
                case V:
                    LogUtil.e(getJson(context,ASSET_V));
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_V) ,true);
                    break;
                case HEART:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_HEART),true);
                    break;
                case LOVE:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_LOVE));
                    break;
                case SMILE:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_SMILE));
                    break;
                case X:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_X));
                    break;
                case V520:
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_V520));
                    break;
                case V1314:
                    giftSurfaceView.setRunTime(GiftSurfaceView.LONG_TIME);
                    giftSurfaceView.setListPoint(PointUtils.getListPointByResourceJson(context,ASSET_V1314));
                    break;

            }
            frame.addView(giftSurfaceView);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String getJson(Context context,String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @OnClick({R.id.btnRandom, R.id.btnV, R.id.btnHeart, R.id.btnLove, R.id.btnSmile, R.id.btnX,
            R.id.btn520, R.id.btn1314})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRandom:
                updateGiftSurfaceView(RANDOM);
                break;
            case R.id.btnV:
                updateGiftSurfaceView(V);
                break;
            case R.id.btnHeart:
                updateGiftSurfaceView(HEART);
                break;
            case R.id.btnLove:
                updateGiftSurfaceView(LOVE);
                break;
            case R.id.btnSmile:
                updateGiftSurfaceView(SMILE);
                break;
            case R.id.btnX:
                updateGiftSurfaceView(X);
                break;
            case R.id.btn520:
                updateGiftSurfaceView(V520);
                break;
            case R.id.btn1314:
                updateGiftSurfaceView(V1314);
                break;
        }
    }
}
