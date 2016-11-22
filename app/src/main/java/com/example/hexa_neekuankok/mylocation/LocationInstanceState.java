package com.example.hexa_neekuankok.mylocation;

import javax.inject.Inject;

/**
 * Created by HI-KOKNEE.KUAN on 17/11/2016.
 */

public class LocationInstanceState {
    @Inject
    MyLocation myLocation;

    public MyLocation getMyLocation(){
        return myLocation;
    }

}
