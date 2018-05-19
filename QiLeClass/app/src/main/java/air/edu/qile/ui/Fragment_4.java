package air.edu.qile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import air.edu.qile.R;

/**
 * Created by Administrator on 2018/4/16.
 */

public class Fragment_4 extends BaseFragment {

    public static String fg_tag="";
    private RecyclerView recyclerView;
    private List<Fragment_4_Adapter.Item> itemlist=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content_4, container, false);
        initview(view);
        return view;
    }

    private void initview(View  view){

        String[] itemname = getResources().getStringArray(R.array.fg4_item_name);
      //  int[]  itemicon = getResources().getIntArray(R.array.fg4_item_icon);
        int[]  itemicon = {R.mipmap.happycast,R.mipmap.download , R.mipmap.nightmode ,
                R.mipmap.video_logo  , R.mipmap.cleancache    ,R.mipmap.sharelogo };

        int length = Math.min( itemicon.length , itemname.length );
        for(int i=0;i<length;i++){
            itemlist.add( new Fragment_4_Adapter.Item(itemicon[i] , itemname[i] ));
        }
        recyclerView =view.findViewById(R.id.fg4_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
      //  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Fragment_4_Adapter adapter=new Fragment_4_Adapter(itemlist,getActivity()  );
        recyclerView.setAdapter( adapter );
    }


}
