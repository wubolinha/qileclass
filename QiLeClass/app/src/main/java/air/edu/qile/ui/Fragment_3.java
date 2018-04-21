package air.edu.qile.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import air.edu.qile.R;

/**
 * Created by Administrator on 2018/4/16.
 */


public class Fragment_3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_3, container, false);

        initdata();
        return view;
    }

    private void initdata(){
        Handler  showhandler=new Handler();

   //     DataAccess.getInstance().getData( "奇乐课堂/故事汇/" );

    }




}
