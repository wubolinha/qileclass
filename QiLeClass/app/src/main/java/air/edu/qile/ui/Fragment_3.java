package air.edu.qile.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import air.edu.qile.R;
import air.edu.qile.model.OssBrowser;
import air.edu.qile.model.bean.ModuleData;
import air.edu.qile.model.bean.MsgEvent;
import air.edu.qile.model.bean.TokenBean;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/16.
 */


public class Fragment_3 extends BaseFragment {

    private RecyclerView recyclerView;
    private String  rootDir="奇乐课堂/故事汇/";
    private RcycleviewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_3, container, false);
        initview(view);
        return view;
    }

    private void initview(View view) {
        recyclerView=view.findViewById(R.id.fg3_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        OssBrowser.getInstance( ).disPatchTask("ShowModule", rootDir );

    }



    @Override
    public void EventBusEvent(List<ModuleData> moduleDataList) {

        final List<ModuleData> newModulelist=new ArrayList<>();
        for(ModuleData moduleData: moduleDataList){
            if(moduleData.getFatherModule().equals( rootDir )){
                newModulelist.add( moduleData );
                OssBrowser.getInstance( ).disPatchTask("showNumInModule", moduleData.getFolder().getFullpath() );

            }
        }
        if(newModulelist.size()==0){
            return;
        }
        adapter=new RcycleviewAdapter(getContext(),newModulelist,R.layout.card1);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBusEvent( MsgEvent msgEvent ) {
        if(msgEvent.getCmd().equals("showNumInModule")){
            String path = (String) msgEvent.getContent();
            String num = (String) msgEvent.getExtradata();

            List mDatas =adapter.getmDatas();
            for(  int i=0;i<mDatas.size();i++ ){
                ModuleData moduleData= (ModuleData) mDatas.get(i);
                if(moduleData.getFolder().getFullpath().equals( path )){
                    Log.w("test","找到需要改变的 item:"+path);
                    moduleData.setNumInModule(      num    );
                    recyclerView.getAdapter().notifyItemChanged( i , moduleData );
                    return;
                }
            }
        }
    }

}
