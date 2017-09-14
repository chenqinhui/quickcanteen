package com.quickcanteen.quickcanteen.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.activities.canteen.GoodsItem;
import com.quickcanteen.quickcanteen.bean.OrderBean;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentActivity extends BaseActivity {
    private ProgressBar commentProgressBar;
    private Button button_to_submit;
    private ListView dishesCommentsList;
    private TextView companyNameTextView;
    private EditText commentContents;
    private RatingBar commentStars;
    private static Handler handler = new Handler();

    private OrderBean orderBean;
    private int companyID;
    private int ordersID;
    private String companyName,message;
    private  ArrayList<String> comments = new ArrayList();
    private ArrayList<Float> scores = new ArrayList();
    private ArrayList<GoodsItem> dishesList;
    private ArrayList<HashMap<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        companyNameTextView = (TextView) findViewById(R.id.companyName);
        Bundle bundle = this.getIntent().getExtras();
        orderBean =(OrderBean)bundle.getSerializable("orderBean");
        companyID = orderBean.getCompanyId();
        dishesList = GoodsItem.getGoodsItemList(orderBean.getDishesBeanList());

        //orders = (Orders) bundle.getSerializable("order");
        ordersID = bundle.getInt("ordersID");

        dishesCommentsList = (ListView)findViewById(R.id.dishesCommentsList);
        CommentsAdapter adapter = new CommentsAdapter(this);
        data = getData(dishesList);
        dishesCommentsList.setAdapter(adapter);

        BaseActivity.initializeTop(this, true, "评价");

        commentProgressBar = (ProgressBar) findViewById(R.id.commentProgressBar);
        commentProgressBar.setVisibility(0);
        commentProgressBar.setIndeterminate(false);

        button_to_submit=(Button)findViewById(R.id.submitButton);
        button_to_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                commentContents = (EditText)findViewById(R.id.totalContent);
                if( commentContents.getText().toString()!=null)
                    comments.add(commentContents.getText().toString());
                else
                    comments.add("");
                commentStars = (RatingBar)findViewById(R.id.totalStar);
                scores.add(commentStars.getRating());
                for(int i = 0; i < dishesCommentsList.getChildCount(); i++) {
                    commentContents = (EditText)findViewById(R.id.dishContent);

                    if( commentContents.getText().toString()!=null)
                        comments.add(commentContents.getText().toString());
                    else
                        comments.add("");
                    commentStars = (RatingBar)findViewById(R.id.dishStar);
                    scores.add(commentStars.getRating());
                }

                finish();

            }
        });
        companyName= orderBean.getCompanyName();
        companyNameTextView.setText(companyName);
        data = getData(dishesList);
        commentProgressBar.setVisibility(View.GONE);

    }


    private ArrayList<HashMap<String, Object>> getData(ArrayList<GoodsItem> list) {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        for (GoodsItem temp : list) {
            arrayList.add(getElement(temp));
        }
        return arrayList;
    }

    private HashMap<String, Object> getElement(GoodsItem goodsItem) {
        HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
        tempHashMap.put("name", goodsItem.name);
        return tempHashMap;
    }

    static class ViewHolder {
        public ImageView img;
        public TextView dish;
        public RatingBar stars;
        public EditText comment;
    }

    public class CommentsAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private Context context;

        public CommentsAdapter(Context context)
        {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // How many items are in the data set represented by this Adapter.(在此适配器中所代表的数据集中的条目数)
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // Get the data item associated with the specified position in the data set.(获取数据集中与指定索引对应的数据项)
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // Get the row id associated with the specified position in the list.(取在列表中与指定索引对应的行id)
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get a View that displays the data at the specified position in the data set.
            ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建View
            if(convertView == null) {
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.dishes_comment_list_content,null);
                holder.img = (ImageView)convertView.findViewById(R.id.dishLogo);
                holder.dish = (TextView)convertView.findViewById(R.id.dishName);
                holder.stars = (RatingBar)convertView.findViewById(R.id.dishStar);
                holder.comment = (EditText)convertView.findViewById(R.id.dishContent);

                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img = (ImageView)convertView.findViewById(R.id.dishLogo);
            holder.dish .setText(data.get(position).get("name").toString());
            holder.stars = (RatingBar)convertView.findViewById(R.id.dishStar);
            holder.comment = (EditText)convertView.findViewById(R.id.dishContent);

            return convertView;
        }

    }

}
