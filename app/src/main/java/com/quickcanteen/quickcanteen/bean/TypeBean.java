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
    private ArrayList<DishesBean> dishesBeans;

    public TypeBean(JSONObject jsonObject) throws JSONException {
        this.typeId = jsonObject.getInt("typeId");
        this.typeName = jsonObject.getString("typeName");
        this.dishesBeans = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("dishesBeans");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempJsonObject = jsonArray.getJSONObject(i);
            dishesBeans.add(new DishesBean(tempJsonObject));
        }
    }

    public ArrayList<DishesBean> getDishesBeans() {
        return dishesBeans;
    }

    public void setDishesBeans(ArrayList<DishesBean> dishesBeans) {
        this.dishesBeans = dishesBeans;
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
