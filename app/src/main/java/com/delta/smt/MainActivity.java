package com.delta.smt;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.GridItemDecoration;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.brands.BrandListActivity;
import com.delta.smt.ui.main.mvp.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActiviy<MainPresenter> implements CommonBaseAdapter.OnItemClickListener<String> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<String> fuctionString;

    @Override
    protected void componentInject(AppComponent appComponent) {


    }

    @Override
    protected void initView() {

        CommonBaseAdapter<String> adapter = new CommonBaseAdapter<String>(this, fuctionString) {
            @Override
            protected void convert(CommonViewHolder holder, String item, int position) {

                Log.e(TAG, "convert: " + item);
                holder.setText(R.id.tv_function, item);
            }

            @Override
            protected int getItemViewLayoutId(int position, String item) {
                return R.layout.item_function;
            }
        };
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new GridItemDecoration(this));
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {
        fuctionString = new ArrayList<>();
        fuctionString.add("PCB库房");
        fuctionString.add("PCB库房1");
        fuctionString.add("PCB库房2");
        fuctionString.add("PCB库房3");
        fuctionString.add("PCB库房4");
        fuctionString.add("sample");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    public void onItemClick(View view, String item, int position) {
        Log.e(TAG, "onItemClick: " + item + position);
        ToastUtils.showMessage(this, item);
        switch (item) {
            case "PCB库房":
                break;
            case "PCB库房1":
                break;
            case "PCB库房2":
                break;
            case "PCB库房3":
                break;
            case "PCB库房4":
                break;
            case "sample":
                IntentUtils.showIntent(this, BrandListActivity.class);
                break;
            default:
                break;
        }
    }
}
