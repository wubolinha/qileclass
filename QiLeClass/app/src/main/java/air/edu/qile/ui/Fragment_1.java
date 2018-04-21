package air.edu.qile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import air.edu.qile.R;
import air.edu.qile.model.bean.BaseData;
import air.edu.qile.model.bean.ModuleData;
import air.edu.qile.model.bean.TokenBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/16.
 */

public class Fragment_1 extends BaseFragment {


    private  RecyclerView recyclerView;
    private String  rootDir="奇乐课堂/微课堂/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_content_1, container, false);
        initview(view);
        return view;
    }

    private void initview(View view) {
        recyclerView=view.findViewById(R.id.fg1_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }

    @Override
    public void EventBusEvent(TokenBean bean) {
        super.EventBusEvent(bean);
        ossBrowser.ShowFolderFile(rootDir);
    }

    @Override
    public void EventBusEvent(List<ModuleData> moduleDataList) {

        final List<ModuleData> newModulelist=new ArrayList<>();
        for(ModuleData moduleData: moduleDataList){
            if(moduleData.getFatherModule().equals( rootDir )){
                newModulelist.add( moduleData );
            }
        }
        if(newModulelist.size()==0){
            return;
        }
        final FragmentRcycleAdapter  adapter=new FragmentRcycleAdapter(getContext(),newModulelist);
        Observable.just(  ""   )
                .subscribeOn(   Schedulers.io() )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.w("test", " module 总数   : "+newModulelist.size() );
                        recyclerView.setAdapter( adapter );
                    }
                });



    }



}
