package com.example.hexa_neekuankok.mylocation;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by HI-KOKNEE.KUAN on 16/11/2016.
 */

public class App extends Application {
    private AppComponent appComponent;
    private MyLocation myLocation = new MyLocation();
    private Boolean sentToBackground = false;
    private static final String instanceStateFile = "instanceState.ser";
    private static final String backgroudFlagFile = "backgroundFlag.ser";
    private Application.ActivityLifecycleCallbacks appActivityCallBack;

    @Override public void onCreate() {
        super.onCreate();

        appActivityCallBack = new Application.ActivityLifecycleCallbacks(){

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

                Activity checkActivity = activity;
//                sentToBackground = true;
//
//                File file = new File(getFilesDir(), instanceStateFile);
//
//                try {
//                    persistBackgroundFlag();
//
//                    FileOutputStream fos = new FileOutputStream(file);
//                    ObjectOutputStream os = new ObjectOutputStream(fos);
//                    os.writeObject(myLocation);
//                    os.close();
//                    fos.close();
//                }
//                catch(Exception ex){
//                    ex.getMessage();
//                }
            }
        };

        registerActivityLifecycleCallbacks(appActivityCallBack);

        File backgroundFlagFile = new File(getFilesDir(), backgroudFlagFile);
        if(backgroundFlagFile.exists()){
            try{
                FileInputStream fisBackgroundFlag = new FileInputStream(backgroundFlagFile);
                ObjectInputStream isBackgroundFlag = new ObjectInputStream(fisBackgroundFlag);
                sentToBackground = (Boolean) isBackgroundFlag.readObject();
                isBackgroundFlag.close();
                fisBackgroundFlag.close();

            }
            catch(Exception ex){
                ex.getMessage();
            }
        }

        if(sentToBackground == true){
            sentToBackground = false;
            persistBackgroundFlag();

            File file = new File(getFilesDir(), instanceStateFile);
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream is = new ObjectInputStream(fis);
                myLocation = (MyLocation) is.readObject();
                is.close();
                fis.close();
            }
            catch(Exception ex){
                ex.getMessage();
            }
        }

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(myLocation)).build();
    }

    @Override
    public void onTrimMemory (int level){
        if(level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
            String msg = "Level = " + Integer.toString(level);
        }
        else if(level == ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE){
            String msg = "Level = " + Integer.toString(level);
        }
        else if(level == ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW){
            String msg = "Level = " + Integer.toString(level);
        }
        else if(level == ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL){
            String msg = "Level = " + Integer.toString(level);
        }
        else if(level == ComponentCallbacks2.TRIM_MEMORY_BACKGROUND){
            String msg = "Level = " + Integer.toString(level);
        }
        else if(level == ComponentCallbacks2.TRIM_MEMORY_MODERATE){
            String msg = "Level = " + Integer.toString(level);
        }
        else if(level == ComponentCallbacks2.TRIM_MEMORY_COMPLETE){
            String msg = "Level = " + Integer.toString(level);
        }
        else{
            String msg = "Level = " + Integer.toString(level);
        }


        sentToBackground = true;

        File file = new File(getFilesDir(), instanceStateFile);

        try {
            persistBackgroundFlag();

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(myLocation);
            os.close();
            fos.close();
        }
        catch(Exception ex){
            ex.getMessage();
        }
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    public void persistBackgroundFlag(){
        try{
            File backgroundFlagFile = new File(getFilesDir(), backgroudFlagFile);
            FileOutputStream fosBackgroundFlag = new FileOutputStream(backgroundFlagFile);
            ObjectOutputStream osBackgroundFlag = new ObjectOutputStream(fosBackgroundFlag);
            osBackgroundFlag.writeObject(sentToBackground);
            osBackgroundFlag.close();
            fosBackgroundFlag.close();
        }
        catch(Exception ex){
            ex.getMessage();
        }
    }
}
