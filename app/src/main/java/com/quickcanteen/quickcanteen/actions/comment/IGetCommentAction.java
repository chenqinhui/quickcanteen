package com.quickcanteen.quickcanteen.actions.comment;

import com.quickcanteen.quickcanteen.actions.IBaseAction;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;

public interface IGetCommentAction extends IBaseAction{
    BaseJson getCommentByDishesId(int dishesId /**, List<UserCommentBean> list**/) throws IOException, JSONException;
}
