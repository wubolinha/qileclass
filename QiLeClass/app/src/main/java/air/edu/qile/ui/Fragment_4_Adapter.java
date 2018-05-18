package air.edu.qile.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import air.edu.qile.R;

/**
 * Created by Administrator on 2018/5/17.
 */

public class Fragment_4_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Item>  itemlist;
    private Context mContext;

    public Fragment_4_Adapter(List<Item> itemlist, Context mContext) {
        this.itemlist = itemlist;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fg_content_4_adapter, parent, false);
        Holder_Item   holder = new Holder_Item(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemholder, int position) {
        Item item=itemlist.get(position);
        Holder_Item  holder = (Holder_Item) itemholder;
        holder.icon.setImageResource( item.iconid );
       // Log.w("test","icon: "+item.iconid);
       // holder.icon.setImageResource( R.mipmap.happycast );
        holder.name.setText(  item.itemname +"");
        holder.root.setTag( position );
        if(position>0){
            holder.devideline.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    /*****
     *
     * ******/
    public class Holder_Item extends RecyclerView.ViewHolder implements View.OnClickListener{

        public  ImageView icon;
        public TextView  name;
        public RelativeLayout  root;
        public View devideline;

        public Holder_Item(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.fg4_icon);
            name= itemView.findViewById(R.id.fg4_name);
            root= itemView.findViewById(R.id.fg4_root);
            devideline = itemView.findViewById(R.id.devideline);
            root.setOnClickListener( this );
        }
        @Override
        public void onClick(View view) {
            switch ( view.getId()){
                case R.id.fg4_root:
                    int position = (int) view.getTag();
                    Item item = itemlist.get(position);
                    Toast.makeText(mContext,""+item.itemname,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /*********
     *
     * *******/
    public  static  class   Item{
        int iconid;   //对应的小图标
        String itemname;   // 选项名字

        public Item(int iconid, String itemname) {
            this.iconid = iconid;
            this.itemname = itemname;
        }
    }

}
