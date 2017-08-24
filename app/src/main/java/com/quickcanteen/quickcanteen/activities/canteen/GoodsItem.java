package com.quickcanteen.quickcanteen.activities.canteen;

import android.graphics.Bitmap;
import com.quickcanteen.quickcanteen.actions.company.ICompanyAction;
import com.quickcanteen.quickcanteen.actions.company.impl.CompanyActionImpl;
import com.quickcanteen.quickcanteen.bean.DishesBean;
import com.quickcanteen.quickcanteen.bean.TypeBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoodsItem implements Serializable {
    public int id;
    public int typeId;
    public int rating;
    public String name;
    public String typeName;
    public double price;
    public int count;
    public String introduce;
    public String pictureAddress;
    public Bitmap picture = null;
    private static int oldCompanyID = 0;

    public GoodsItem(int id, double price, String name, int typeId, String typeName) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
        this.rating = new Random().nextInt(5) + 1;
    }

    public GoodsItem(DishesBean dishes, TypeBean type) {
        this.id = dishes.getDishesId();
        this.name = dishes.getDishesName();
        this.price = dishes.getPrice();
        this.typeId = type.getTypeId();
        this.typeName = type.getTypeName();
        this.rating = (int) (double) dishes.getRating();
        this.introduce = dishes.getDishesIntroduce();
        this.pictureAddress = dishes.getDiagrammaticSketchAddress();
    }

    public GoodsItem(DishesBean dishes) {
        this.id = dishes.getDishesId();
        this.name = dishes.getDishesName();
        this.price = dishes.getPrice();
        this.rating = (int) (double) dishes.getRating();
        this.introduce = dishes.getDishesIntroduce();
        this.pictureAddress = dishes.getDiagrammaticSketchAddress();
        this.count = dishes.getCount();
    }

    private static ArrayList<GoodsItem> goodsList;
    private static ArrayList<GoodsItem> typeList;

    private static void initData(int companyID) throws Exception {
        goodsList = new ArrayList<>();
        typeList = new ArrayList<>();
        GoodsItem item = null;
        ICompanyAction companyAction = new CompanyActionImpl(null);
        BaseJson baseJson = companyAction.getTypesAndDishesByCompanyId(companyID);
        List<TypeBean> types = new ArrayList<>();
        JSONArray jsonArray = baseJson.getJSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempJsonObject = jsonArray.getJSONObject(i);
            types.add(new TypeBean(tempJsonObject));
        }
        for (TypeBean type : types) {
            int typeID = type.getTypeId();
            List<DishesBean> dishesList = type.getDishesBeans();
            for (DishesBean dishes : dishesList) {
                item = new GoodsItem(dishes, type);
                goodsList.add(item);
            }
            typeList.add(item);
        }

    }

    public static void getData() {
        goodsList = new ArrayList<>();
        typeList = new ArrayList<>();
        GoodsItem item = null;
        for (int i = 1; i < 15; i++) {
            for (int j = 1; j < 10; j++) {
                item = new GoodsItem(100 * i + j, Math.random() * 100, "商品" + (100 * i + j), i, "种类" + i);
                goodsList.add(item);
            }
            typeList.add(item);
        }
    }

    public static ArrayList<GoodsItem> getGoodsList(int companyID) throws Exception {
        if (oldCompanyID != companyID || goodsList == null) {
            //getData();
            initData(companyID);
        }
        return goodsList;
    }

    public static ArrayList<GoodsItem> getTypeList(int companyID) throws Exception {
        if (oldCompanyID != companyID || typeList == null) {
            //getData();
            initData(companyID);
        }
        return typeList;
    }

    public static GoodsItem getWholeGoodsItem(DishesBean dishes) {
        if (typeList == null || goodsList == null) {
            return null;
        } else {
            for (GoodsItem goodsItem : goodsList) {
                if (goodsItem.id == dishes.getDishesId()) {
                    return goodsItem;
                }
            }
            return null;
        }
    }

    public static GoodsItem getWholeGoodsItem(GoodsItem dishes) {
        if (typeList == null || goodsList == null) {
            return null;
        } else {
            for (GoodsItem goodsItem : goodsList) {
                if (goodsItem.id == dishes.id) {
                    return goodsItem;
                }
            }
            return null;
        }
    }

    public static ArrayList<GoodsItem> getWholeGoodsItemList(ArrayList<DishesBean> dishesList) {
        if (typeList == null || goodsList == null) {
            return null;
        } else {
            ArrayList<GoodsItem> goodsItemArrayList = new ArrayList<GoodsItem>();
            for (DishesBean dishes : dishesList) {
                goodsItemArrayList.add(getWholeGoodsItem(dishes));
            }
            return goodsItemArrayList;
        }
    }

    public static ArrayList<GoodsItem> getGoodsItemList(List<DishesBean> dishesList) {
        ArrayList<GoodsItem> goodsItemArrayList = new ArrayList<GoodsItem>();
        for (DishesBean dishes : dishesList) {
            goodsItemArrayList.add(new GoodsItem(dishes));
        }
        return goodsItemArrayList;
    }
}
