package com.quickcanteen.quickcanteen.activities.canteen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.bean.UserCommentBean;

import java.util.List;

public class DisplayCommentAdapter extends RecyclerView.Adapter<DisplayCommentAdapter.ViewHolder> {
    private List<UserCommentBean> mUserCommentBeans;

    class ViewHolder extends RecyclerView.ViewHolder {

        private RatingBar showRatingBar;
        private TextView commentContent, commenterName, commentTime;

        public ViewHolder(View view) {
            super(view);
            showRatingBar = (RatingBar) view.findViewById(R.id.showRatingBar);
            commentContent = (TextView) view.findViewById(R.id.commentContent);
            commenterName = (TextView) view.findViewById(R.id.commenterName);
            commentTime = (TextView) view.findViewById(R.id.commentTime);
        }
    }

    public DisplayCommentAdapter(List<UserCommentBean> userCommentBeans) {
        mUserCommentBeans = userCommentBeans;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_dishes_comment_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserCommentBean userCommentBean = mUserCommentBeans.get(position);

        holder.showRatingBar.setRating(userCommentBean.getRating().floatValue());
        holder.commentContent.setText(userCommentBean.getCommentContent());
        holder.commenterName.setText(userCommentBean.getCommenterName());
        holder.commentTime.setText(userCommentBean.getCommentTimeStr());

    }

    public int getItemCount() {
        return mUserCommentBeans.size();
    }
}
