package com.example.mymvp.mainactivity;

import dagger.Component;

@Component(modules = MainModule.class)
public interface MianActivityComponent {
    void inject(MainActivity activity);
}
