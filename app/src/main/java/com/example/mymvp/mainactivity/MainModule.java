package com.example.mymvp.mainactivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    MainPresenter presenter;

    public MainModule(MainContract.IView view, MainActivity mainActivity) {
        presenter = new MainPresenter(view, mainActivity);
    }

    @Provides
    MainPresenter provideMainPresenter() {
        return presenter;
    }
}
