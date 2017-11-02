package com.quickcanteen.quickcanteen.actions.comment.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.comment.IGetCommentAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetCommentActionImpl extends BaseActionImpl implements IGetCommentAction {
    public GetCommentActionImpl(Activity activity){super(activity);};
    @Override
    public BaseJson getCommentByDishesId(int dishesId /**, List<UserCommentBean> list**/) throws IOException, JSONException{
        Map<String, String> map = new HashMap<String, String>();
        map.put("dishesId", String.valueOf(dishesId));
        /**
        List<String> commentList = new ArrayList<String>();

        for(UserCommentBean item: list){
            commentList.add(item.getCommentContent());
        }

        map.put("commentList", join(commentList, '_'));
         **/

        String result = httpConnectByPost("getComment/getCommentByDishesId", map);
        return new BaseJson(result);
    }

}
