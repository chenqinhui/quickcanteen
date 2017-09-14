package com.quickcanteen.quickcanteen.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 11022 on 2017/6/30.
 */
public class TypeBean {
    private Integer typeId;
    private String typeName;
    private ArrayList<DishesBean> dishesBeanList;

    public TypeBean(JSONObject jsonObject) throws JSONException {
        this.typeId = jsonObject.getInt("typeId");
        this.typeName = jsonObject.getString("typeName");
        this.dishesBeanList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("dishesBeanList");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempJsonObject = jsonArray.getJSONObject(i);
            dishesBeanList.add(new DishesBean(tempJsonObject));
        }
    }

    public ArrayList<DishesBean> getDishesBeanList() {
        return dishesBeanList;
    }

    public void setDishesBeanList(ArrayList<DishesBean> dishesBeanList) {
        this.dishesBeanList = dishesBeanList;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
