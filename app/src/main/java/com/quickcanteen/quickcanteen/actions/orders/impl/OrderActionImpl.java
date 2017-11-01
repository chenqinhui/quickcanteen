package com.quickcanteen.quickcanteen.actions.orders.impl;

import android.app.Activity;
import com.quickcanteen.quickcanteen.actions.BaseActionImpl;
import com.quickcanteen.quickcanteen.actions.orders.IOrderAction;
import com.quickcanteen.quickcanteen.activities.canteen.GoodsItem;
import com.quickcanteen.quickcanteen.bean.LocationBean;
import com.quickcanteen.quickcanteen.bean.OrderStatus;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.*;

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
    public BaseJson unsubscribe(int ordersID) throws IOException, JSONException {
        return updateOrderState(ordersID, OrderStatus.CANCELLED);
    }

    @Override
    public BaseJson comment(int ordersID) throws IOException, JSONException {
        return updateOrderState(ordersID, OrderStatus.COMPLETE);
    }

    @Override
    public BaseJson takeMeal(int ordersID) throws IOException, JSONException {
        return updateOrderState(ordersID, OrderStatus.NOT_COMMENT);
    }


    protected BaseJson updateOrderState(int ordersID, OrderStatus orderStatus) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", String.valueOf(ordersID));
        map.put("orderStatus", String.valueOf(orderStatus.getValue()));
        String result = httpConnectByPost("order/updateOrderState", map);
        return new BaseJson(result);
    }

    public BaseJson payWithTimeSlot(int ordersID, String timeSlot) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", String.valueOf(ordersID));
        map.put("timeSlot", timeSlot);
        String result = httpConnectByPost("order/payWithTimeSlot", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson updateFinishTime(int orderID) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", String.valueOf(orderID));
        String result = httpConnectByPost("order/updateFinishTime", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson updateStartTime(int orderID) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", String.valueOf(orderID));
        String result = httpConnectByPost("order/updateStartTime", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson pay(int orderID, String deliverWay) throws IOException, JSONException {
        return updateOrderState(orderID, OrderStatus.CHECKING);
    }

    @Override
    public BaseJson payWithDeliverAddress(int orderID, LocationBean locationBean,double deliverPrice) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", String.valueOf(orderID));
        map.put("locationId", String.valueOf(locationBean.getLocationId()));
        map.put("deliverPrice",String.valueOf(deliverPrice));
        String result = httpConnectByPost("order/setOrderLocationAndDeliverPrice", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson getNeedDeliverOrdersByPage(int pageNumber, int pageSize) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNumber", String.valueOf(pageNumber));
        map.put("pageSize", String.valueOf(pageSize));
        String result = httpConnectByPost("order/getNeedDeliverOrdersByPage", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson askForDeliverOrder(int orderID) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", String.valueOf(orderID));
        map.put("userID", String.valueOf(getCurrentUserID()));
        String result = httpConnectByPost("order/askForDeliverOrder", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson getDeliverOrdersByPage(int pageNumber, int pageSize) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNumber", String.valueOf(pageNumber));
        map.put("pageSize", String.valueOf(pageSize));
        String result = httpConnectByPost("order/getDeliverOrdersByPage", map);
        return new BaseJson(result);
    }

    @Override
    public BaseJson completeDeliverOrder(int orderID) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", String.valueOf(orderID));
        map.put("userID", String.valueOf(getCurrentUserID()));
        String result = httpConnectByPost("order/completeDeliverOrder", map);
        return new BaseJson(result);
    }
}