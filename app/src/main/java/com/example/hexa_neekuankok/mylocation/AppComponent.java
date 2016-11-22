package com.example.hexa_neekuankok.mylocation;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by HI-KOKNEE.KUAN on 16/11/2016.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void passIn(MainActivity mainActivity);
    void passIn(LocationInstanceState locationInstanceState);
}
