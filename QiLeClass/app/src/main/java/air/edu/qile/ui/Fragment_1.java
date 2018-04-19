package air.edu.qile.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Handler;
import air.edu.qile.R;
import air.edu.qile.databinding.FgContent1Binding;
import air.edu.qile.model.DataAccess;

/**
 * Created by Administrator on 2018/4/16.
 */

public class Fragment_1 extends Fragment {

    private Handler  showhandler;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_content_1, container, false);
         initdata();
        return view;
    }

    private void initdata(){

        showhandler=new Handler();


        final DataAccess   dataAccess=new DataAccess();
        showhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataAccess.getData("/奇乐课堂/微课堂");
            }
        },2000);


    }



}
