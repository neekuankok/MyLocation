package com.example.hexa_neekuankok.mylocation;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HI-KOKNEE.KUAN on 18/11/2016.
 */

@Module
public class AppModule {
    MyLocation myLocation;

    public AppModule(MyLocation myLocation){
        this.myLocation = myLocation;
    }

    @Provides
    MyLocation provideMyLocation(){
        return myLocation;
    }
}
