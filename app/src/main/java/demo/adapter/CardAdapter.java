package demo.adapter;

/**
 * Created by ragavendran on 20-06-2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.demo.ashine.bharatration.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import demo.model.DataItem;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<DataItem> mItems;
    private Spinner brand_names;
    private Spinner weight_list;
    private String[] brand_data;
    private  String[] weight_data;
    private JSONObject jo;
    public DataItem di;
    private String myjson;

    public CardAdapter() {
        super();
        mItems = new ArrayList<DataItem>();

        Gson gson = new Gson();

         //jo = RestfulAppService.source("http://localhost:80/Bharatration/");
         //System.out.println("Object"+" "+ gson.fromJson(jo.toString(),DataItem.class));


        DataItem dataItem = new DataItem();
        dataItem.setTypeID(1);
        dataItem.setBrandID(1);
        dataItem.setTypeName("Aashirvad");
        dataItem.setPrice("Rs 50");
        dataItem.setBrandimg(R.drawable.abc_ab_share_pack_holo_light);

        DataItem dataItem1 = new DataItem();
        dataItem1.setTypeID(1);
        dataItem1.setBrandID(1);
        dataItem1.setTypeName("Parrys");
        dataItem1.setPrice("Rs 40");
        dataItem1.setBrandimg(R.drawable.abc_ab_share_pack_holo_dark);

        System.out.println("Gson"+" "+gson.toJson(dataItem));

        mItems.add(dataItem);
        mItems.add(dataItem1);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        DataItem dataItem = mItems.get(i);

        viewHolder.brandimg.setImageResource(dataItem.getBrandimg());
        viewHolder.typeName.setText(dataItem.getTypeName());
        viewHolder.price.setText(dataItem.getPrice());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView brandimg;
        public Spinner brandName;
        public TextView typeName;
        public Spinner weight;
        public TextView quantity;
        public TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            brandimg = (ImageView)itemView.findViewById(R.id.brand_img);
            typeName = (TextView)itemView.findViewById(R.id.type_name);
            quantity = (TextView)itemView.findViewById(R.id.quantity);
            price = (TextView) itemView.findViewById(R.id.brp);
            weight = (Spinner) itemView.findViewById(R.id.weight);
            brandName = (Spinner) itemView.findViewById(R.id.brand_name);
        }
    }
}