package com.quickcanteen.quickcanteen.actions.orders.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.orders.IOrderAction;
import com.quickcanteen.quickcanteen.activities.canteen.GoodsItem;
import com.quickcanteen.quickcanteen.bean.OrderStatus;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.quickcanteen.quickcanteen.utils.StringUtils.join;

/**
 * Created by 11022 on 2017/7/1.
 */
public class OrderActionImpl extends BaseActionImpl implements IOrderAction {
    public OrderActionImpl(Activity activity) {
        super(activity);
    }

    @Override
    public BaseJson getOrdersListByUserIDByPage(int pageNumber, int pageSize) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userID", String.valueOf(getCurrentUserID()));
        map.put("pageNumber", String.valueOf(pageNumber));
        map.put("pageSize", String.valueOf(pageSize));
        String result = httpConnectByPost("order/getOrdersListByUserIDByPage", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson placeOrder(int companyID, List<GoodsItem> list) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userID", String.valueOf(getCurrentUserID()));
        map.put("companyID", String.valueOf(companyID));
        List<Integer> dishesIDList = new ArrayList<Integer>();
        List<Integer> dishesCountList = new ArrayList<Integer>();
        double price = 0;
        for (GoodsItem temp : list) {
            dishesIDList.add(temp.id);
            dishesCountList.add(temp.count);
            price += temp.price * temp.count;
        }
        map.put("totalPrice", String.valueOf(price));
        map.put("dishesIDList", join(dishesIDList, '_'));
        map.put("dishesCountList", join(dishesCountList, '_'));
        String result = httpConnectByPost("order/placeOrder", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson getTimeSlotByOrdersID(int ordersID) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ordersID", String.valueOf(ordersID));
        String result = httpConnectByPost("order/getTimeSlotByOrdersID", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson pay(int orderID) throws IOException, JSONException {
        return updateOrderState(orderID, OrderStatus.PREPARING);
    }

    @Override
    public BaseJson unsubscribe(int ordersID) throws IOException, JSONException {
        return updateOrderState(ordersID, OrderStatus.CANCELLED);
    }

    @Override
    public BaseJson takeMeal(int ordersID) throws IOException, JSONException {
        return updateOrderState(ordersID, OrderStatus.NOT_COMMENT);
    }


    protected BaseJson updateOrderState(int ordersID, OrderStatus orderStatus) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ordersID", String.valueOf(ordersID));
        map.put("orderState", String.valueOf(orderStatus.getValue()));
        String result = httpConnectByPost("order/updateOrderState", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson pay(int orderID, String deliverWay) throws IOException, JSONException {
        switch (deliverWay) {
            case "取餐":
                return updateOrderState(orderID, OrderStatus.PEND_TO_TAKE);
            case "配送":
                return updateOrderState(orderID, OrderStatus.DISTRIBUTING);
        }
        return null;
    }
}
