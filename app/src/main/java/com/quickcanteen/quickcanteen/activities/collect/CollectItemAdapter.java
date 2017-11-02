package com.quickcanteen.quickcanteen.activities.collect;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.bean.DishesBean;
import com.quickcanteen.quickcanteen.utils.AsyncBitmapLoader;

import java.util.List;

import static com.quickcanteen.quickcanteen.utils.AsyncBitmapLoader.asyncBitmapLoader;

public class CollectItemAdapter extends RecyclerView.Adapter<CollectItemAdapter.ViewHolder> implements View.OnClickListener{
    private List<DishesBean> mDishesList;

    public CollectItemAdapter(List<DishesBean> collectList){
        mDishesList = collectList;
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
        DishesBean dishesItem = mDishesList.get(position);
        holder.collectName.setText(dishesItem.getDishesName());
        holder.collectPrice.setText(dishesItem.getPrice().toString());
        holder.collectRating.setRating(dishesItem.getRating().floatValue());
        Bitmap bitmap = asyncBitmapLoader.loadBitmap(holder.collectImg, dishesItem.getDiagrammaticSketchAddress(), holder.collectImg.getLayoutParams().width, holder.collectImg.getLayoutParams().height, new AsyncBitmapLoader.ImageCallBack() {
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
    }

    @Override
    public int getItemCount() {
        return mDishesList.size();
    }

    @Override
    public void onClick(View v) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView collectImg;
        TextView collectName;
        RatingBar collectRating;
        TextView collectPrice;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            collectImg = (ImageView)view.findViewById(R.id.collectImg);
            collectName = (TextView)view.findViewById(R.id.collectName);
            collectRating = (RatingBar)view.findViewById(R.id.collectRating);
            collectPrice = (TextView)view.findViewById(R.id.collectPrice);
        }


    }

}
