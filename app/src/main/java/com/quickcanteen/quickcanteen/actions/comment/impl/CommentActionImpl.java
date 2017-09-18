package com.quickcanteen.quickcanteen.actions.comment.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.comment.ICommentAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommentActionImpl extends BaseActionImpl implements ICommentAction {
    public CommentActionImpl(Activity activity) {
        super(activity);
    }

    @Override
    public BaseJson postComment(int objectId, String commentContent, double rating,int isCompany) throws IOException, JSONException {
        Map<String,String> map = new HashMap<>();
        map.put("objectId",String.valueOf(objectId));
        map.put("userId",String.valueOf(getCurrentUserID()));
        map.put("commentContent",commentContent);
        map.put("rating",String.valueOf(rating));
        map.put("isCompany",String.valueOf(isCompany));
        String result = httpConnectByPost("comment/postComment", map);
        return new BaseJson(result);
    }
}
