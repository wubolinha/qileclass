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
import air.edu.qile.model.RootOssHttp;
import air.edu.qile.model.bean.ModuleData;
import air.edu.qile.model.bean.MsgEvent;
import air.edu.qile.model.bean.OpenMuduleData;
import air.edu.qile.model.bean.TokenBean;
import air.edu.qile.tool.CommonTool;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/16.
 */

public class Fragment_1 extends BaseFragment {


    private RecyclerView recyclerView;
    private String rootDir = "奇乐课堂/微课堂/";
    private RcycleviewAdapter adapter;
    public static String fg_tag = "微课堂";
    private List<OpenMuduleData> openMuduleDataList =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("test", getClass().getSimpleName()+ "   onCreateView...");
        View view = inflater.inflate(R.layout.fg_content_1, container, false);

        initview(view);
        return view;
    }

    private void initview(View view) {
        recyclerView = view.findViewById(R.id.fg1_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //     OssBrowser.getInstance( ).disPatchTask("ShowModule", rootDir );
        adapter = new RcycleviewAdapter(getContext(), openMuduleDataList, R.layout.card1);
        recyclerView.setAdapter(adapter);
    }




    @Override
    public void EventBusEvent(MsgEvent msgEvent) {
// 显示 每个文件夹下有多少个文件
        if (msgEvent.getCmd().equals("showNumInModule")) {
            String path = (String) msgEvent.getContent();
            String num = (String) msgEvent.getExtradata();
            List mDatas = adapter.getmDatas();
            for (int i = 0; i < mDatas.size(); i++) {
                OpenMuduleData data = (OpenMuduleData) mDatas.get(i);
                if (path.contains(data.getConfig().getName())) {
                    Log.w("test", "找到需要改变的 item:" + path);
                    data.setNumInModule(num);
                    recyclerView.getAdapter().notifyItemChanged(i, data);
                    return;
                }
            }
        }
        //  显示模块详情
        if (msgEvent.getCmd().equals("class_" + fg_tag)) {
            Log.w("test", "收到 ........ msgEvent：" + msgEvent.getCmd());
            openMuduleDataList.clear();
            openMuduleDataList.addAll( msgEvent.getListdata() );
            if (openMuduleDataList.size() == 0) {
                return;
            }
            Log.w("test", " module 总数   : " + openMuduleDataList.size());
            adapter.notifyDataSetChanged();
            for (OpenMuduleData openMuduleData : openMuduleDataList) {
                String foldername = openMuduleData.getFatherurl() + openMuduleData.getConfig().getName();
                OssBrowser.getInstance().disPatchTask("showNumInModule",
                        CommonTool.pathTrans(foldername));
            }
        }
        if (msgEvent.getCmd().equals("class_init")) {
          getOpenOssConfigData(RootOssHttp.rootUrl+fg_tag+"/"+RootOssHttp.configName);
        }
    }



}
