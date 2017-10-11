package com.quickcanteen.quickcanteen.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.comment.impl.CommentActionImpl;
import com.quickcanteen.quickcanteen.actions.comment.ICommentAction;
import com.quickcanteen.quickcanteen.actions.orders.IOrderAction;
import com.quickcanteen.quickcanteen.actions.orders.impl.OrderActionImpl;
import com.quickcanteen.quickcanteen.activities.main.MainActivity;
import com.quickcanteen.quickcanteen.bean.OrderBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;

import java.util.*;

public class CommentActivity extends BaseActivity {

    private RecyclerView commentsView;
    private LinearLayoutManager layoutManager;
    private TextView companyName;
    private Button submitButton;
    private RatingBar totalStar, dishStar;
    private EditText totalContent, dishContent;
    private List<String> dishesName = new LinkedList<String>();
    private ICommentAction commentAction = new CommentActionImpl(this);
    private IOrderAction orderAction = new OrderActionImpl(this);
    private static Handler handler = new Handler();
    private String message;
    private OrderBean orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        initializeTop(this, true, "评价");
        commentsView = (RecyclerView) findViewById(R.id.dishesCommentsList);
        companyName = (TextView) findViewById(R.id.companyName);
        submitButton = (Button) findViewById(R.id.submitButton);
        totalStar = (RatingBar) findViewById(R.id.totalStar);
        totalContent = (EditText) findViewById(R.id.totalContent);

        Bundle bundle = this.getIntent().getExtras();
        orders = (OrderBean) bundle.getSerializable("orderBean");
        companyName.setText(orders.getCompanyName());

        layoutManager = new LinearLayoutManager(this);
        commentsView.setLayoutManager(layoutManager);
        for (int i = 0; i < orders.getDishesBeanList().size(); i++) {
            dishesName.add(orders.getDishesBeanList().get(i).getDishesName());
        }
        CommentAdapter adapter = new CommentAdapter(dishesName);
        commentsView.setAdapter(adapter);

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) commentsView.getLayoutParams();
        linearParams.height = dishesName.size()*500;
        commentsView.setLayoutParams(linearParams);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new submitThread()).start();
            }
        });
    }

    public class submitThread implements Runnable {
        @Override
        public void run() {
            message = "提交成功";
            try {
                int hasComment = 1;

                double totalRating = totalStar.getRating();
                String totalComment = totalContent.getText().toString();
                if (totalRating == 0)
                    hasComment = 0;

                double dishesRating;
                String dishesComment;
                View mView;
                LinearLayout mLayout;
                for (int i = 0; i < dishesName.size(); i++) {
                    mView = commentsView.getChildAt(i);
                    mLayout = (LinearLayout) mView;
                    dishStar = (RatingBar) mLayout.findViewById(R.id.dishStar);
                    dishesRating = dishStar.getRating();
                    if (dishesRating == 0) {
                        hasComment = 0;
                        break;
                    }
                }
                if (hasComment == 1) {
                    BaseJson postComJson = commentAction.postComment(orders.getCompanyId(), totalComment, totalRating, 1);
                    switch (postComJson.getReturnCode()) {
                        case "9.0":
                            break;
                        default:
                            message = postComJson.getErrorMessage();
                            break;
                    }
                    Log.d("ReturnCode", postComJson.getReturnCode());

                    BaseJson postDishJson;
                    for (int i = 0; i < dishesName.size(); i++) {
                        mView = commentsView.getChildAt(i);
                        mLayout = (LinearLayout) mView;
                        dishStar = (RatingBar) mLayout.findViewById(R.id.dishStar);
                        dishesRating = dishStar.getRating();
                        dishContent = (EditText) mLayout.findViewById(R.id.dishContent);
                        dishesComment = dishContent.getText().toString();
                        postDishJson = commentAction.postComment(orders.getDishesBeanList().get(i).getDishesId(), dishesComment, dishesRating, 0);
                        switch (postDishJson.getReturnCode()) {
                            case "9.0":
                                break;
                            default:
                                message = postDishJson.getErrorMessage();
                                break;
                        }
                        Log.d("ReturnCode", postComJson.getReturnCode());
                    }
                    BaseJson commentJson = orderAction.comment(orders.getOrderId());

                    Intent intent = new Intent();
                    intent.setClass(CommentActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    message = "评星不能为0";
                }

            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        message = "连接错误";
                    }
                });
                return;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CommentActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
        private List<String> mDishesName;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView dishName;

            public ViewHolder(View view) {
                super(view);
                dishName = (TextView) view.findViewById(R.id.dishName);
            }
        }

        public CommentAdapter(List<String> dishesName) {
            mDishesName = dishesName;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishes_comment_list_content, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String dish = mDishesName.get(position);
            holder.dishName.setText(dish);
        }

        @Override
        public int getItemCount() {
            return mDishesName.size();
        }
    }
}
