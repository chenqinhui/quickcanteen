package com.quickcanteen.quickcanteen.bean;

import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 11022 on 2017/6/29.
 */
public interface JsonBean<T> {
    T newInstance(JSONObject jsonObject) throws JSONException;
    T newInstance(BaseJson baseJson) throws JSONException;
}
