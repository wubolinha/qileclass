package air.edu.qile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import air.edu.qile.R;

/**
 * Created by Administrator on 2018/4/16.
 */

public class Fragment_4 extends BaseFragment {

    public static String fg_tag="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_4, container, false);

        return view;
    }

}
