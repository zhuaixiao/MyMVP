package com.example.mymvp.application;

import com.example.mymvp.mainactivity.MainModel;

import dagger.Component;

@Component(modules = ApiModule.class)
public interface AppComponent {
    void inject(MainModel mainModel);
}
