package com.quickcanteen.quickcanteen.activities.collect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.activities.canteen.CanteenActivity;
import com.quickcanteen.quickcanteen.activities.userinformation.UserInformation;
import com.quickcanteen.quickcanteen.bean.CompanyInfoBean;
import com.quickcanteen.quickcanteen.bean.DishesBean;
import com.quickcanteen.quickcanteen.utils.AsyncBitmapLoader;

import java.util.List;

import static com.quickcanteen.quickcanteen.utils.AsyncBitmapLoader.asyncBitmapLoader;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> implements View.OnClickListener {
    private List<DishesBean> mCollectList;
    private Activity activity;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView canteenName;
        //RecyclerView collectDishesView;
        ImageView collectImg;
        TextView collectName;
        RatingBar collectRating;
        TextView collectPrice;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            canteenName = (TextView)view.findViewById(R.id.collectCompanyName);
            collectImg = (ImageView)view.findViewById(R.id.collectImg);
            collectName = (TextView)view.findViewById(R.id.collectName);
            collectRating = (RatingBar)view.findViewById(R.id.collectRating);
            collectPrice = (TextView)view.findViewById(R.id.collectPrice);
            //collectDishesView = (RecyclerView)view.findViewById(R.id.collectDishesList);
        }
    }

    public CollectAdapter(List<DishesBean> collectList, Activity activity){
        mCollectList = collectList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DishesBean collectItem = mCollectList.get(position);
        holder.canteenName.setText(collectItem.getCompanyName());
        holder.collectName.setText(collectItem.getDishesName());
        holder.collectPrice.setText(collectItem.getPrice().toString());
        holder.collectRating.setRating(collectItem.getRating().floatValue());
        Bitmap bitmap = asyncBitmapLoader.loadBitmap(holder.collectImg, collectItem.getDiagrammaticSketchAddress(), holder.collectImg.getLayoutParams().width, holder.collectImg.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
            }
        });
        if(bitmap!=null){
            holder.collectImg.setImageBitmap(bitmap);
        }
        holder.itemView.setTag(position);

        //CollectItemAdapter collectItemAdapter = new CollectItemAdapter(mCollectList);
        //holder.collectDishesView.setAdapter(collectItemAdapter);

    }

    @Override
    public int getItemCount() {
        return mCollectList.size();
    }

    @Override
    public void onClick(View v) {
        toAddCart(mCollectList.get((int)v.getTag()));
    }

    public void toAddCart(DishesBean dishesBean) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("companyId",dishesBean.getCompanyId());
        intent.putExtras(bundle);
        intent.setClass(activity, CanteenActivity.class);
        activity.startActivity(intent);
    }
}
