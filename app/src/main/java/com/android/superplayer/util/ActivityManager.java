package com.android.superplayer.util;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.superplayer.config.LogUtil;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * authr : edz on 2017/8/31  下午10:55
 * describe 管理类
 */


public class ActivityManager {

    private static final String TAG = "class_activity";
    //private static List<Activity> activities = new ArrayList<>();
    private static CopyOnWriteArrayList<Activity> activities = new CopyOnWriteArrayList<>();//线程同步
    private static ActivityManager instance = null;

    private ActivityManager() {}

    public static synchronized ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity){
        activities.add(activity);
        Log.e(TAG, "AddActivity " + activity.getLocalClassName()+"size: "+activities.size());
    }

    public void killAllActivity(){
        for (Activity activity:activities){
            try {
                if (activity.isFinishing()) {
                    continue;
                }
                activity.finish();
            }catch (Exception ignored){

            }
        }
        Log.e(TAG, "kill size: " + activities.size());
        activities.clear();
    }

    /**
     *  可用 zcs ,????有问题
     *   MyActivityManager.getInstance().KillActivity(this);
     */
    public void killActivity(Activity activity){

        boolean contains = activities.contains(activity);
        if(contains){

            if(!activity.isFinishing()){
                activity.finish();
            }
            activities.remove(activity);
            Log.e(TAG, "KillActivity: " + activity.getLocalClassName()+" size: "+activities.size());
        }

    }
    //保留 最底的一个
    public void removeFinalActivity(){
        try {
            activities.remove(activities.size() - 1);
            Log.e(TAG, "delete 1 activity,now rest " + activities.size() + " activity");
        }catch (Exception e){
            LogUtil.e(e.getMessage());
        }
    }

    @Nullable
    public static Activity getTopActivity() {
        if (activities.isEmpty()) {
            return null;
        } else {
            return activities.get(activities.size() - 1);
        }
    }


    /**
     * 结束指定的Activity
     * 方法可行  zuochunsheng
     * ActivityManager.getInstance().finishSingleActivity(this);
     */
    public  void finishSingleActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
            if(!activity.isFinishing()){
                activity.finish();
                LogUtil.e(TAG,"finishSingleActivity: "+activity.getClass());
            }
            //activity = null;
        }
        LogUtil.e(TAG,"finishSingleActivity  size: "+activities.size());
    }
    /**
     * 结束指定类名的Activity 在遍历一个列表的时候不能执行删除操作，所有我们先记住要删除的对象，遍历之后才去删除。
     * 方法可行  zuochunsheng ;
     */
    public  void finishSingleActivityByClass(Class<?> cls) {
        for (Activity activity : activities) {
            if (activity.getClass().equals(cls)) {
                finishSingleActivity(activity);

            }
        }
        Log.e(TAG,"finishSingleActivityByClass  size: "+activities.size());
    }
}
