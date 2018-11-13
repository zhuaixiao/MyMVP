package com.example.mymvp.mainactivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mymvp.R;
import com.example.mymvp.adapter.AdapterItem;
import com.example.mymvp.adapter.MyRecyclerViewAdapter;
import com.example.mymvp.db.ProvinceDb;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.jwenfeng.library.pulltorefresh.ViewStatus;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements MainContract.IView {
    @Inject
    MainPresenter presenter;
    private PullToRefreshLayout layout;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    List<AdapterItem> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }


    private void init() {
        DaggerMianActivityComponent.builder().mainModule(new MainModule(this, this))
                .build().inject(this);
        layout = findViewById(R.id.main_layout);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new MyRecyclerViewAdapter(mlist);
        recyclerView.setAdapter(adapter);
        if (mlist.isEmpty()) {
            presenter.handleData();
        }
        layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                presenter.handleData();
            }

            @Override
            public void loadMore() {
                presenter.handleData();
                DataSupport.deleteAll(ProvinceDb.class);
            }
        });
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (!aBoolean) {
                    Toast.makeText(MainActivity.this, "请允许该权限", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void loadData(List<AdapterItem> list) {
        if (!list.isEmpty()) {
            mlist = list;
            init();
            layout.finishRefresh();
            layout.finishLoadMore();
        } else {
            layout.showView(ViewStatus.EMPTY_STATUS);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layout.showView(ViewStatus.CONTENT_STATUS);
                    layout.finishLoadMore();
                    layout.finishRefresh();
                }
            }, 2000);

        }
    }

    @Override
    public void error() {
        layout.showView(ViewStatus.ERROR_STATUS);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.showView(ViewStatus.CONTENT_STATUS);
                layout.finishLoadMore();
                layout.finishRefresh();
            }
        }, 2000);
    }
}
