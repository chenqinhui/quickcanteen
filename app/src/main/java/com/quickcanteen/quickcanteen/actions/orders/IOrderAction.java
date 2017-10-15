package com.quickcanteen.quickcanteen.actions.orders;

import com.quickcanteen.quickcanteen.activities.canteen.GoodsItem;
import com.quickcanteen.quickcanteen.utils.BaseJson;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by 11022 on 2017/7/1.
 */
public interface IOrderAction {
    BaseJson getOrdersListByUserIDByPage(int pageNumber, int pageSize) throws IOException, JSONException;

    BaseJson placeOrder(int companyID, List<GoodsItem> list) throws IOException, JSONException;

    BaseJson getTimeSlotByOrdersID(int ordersID) throws IOException, JSONException;

    BaseJson pay(int orderID) throws IOException, JSONException;

    BaseJson newOrder(int orderID) throws IOException, JSONException;

    BaseJson unsubscribe(int ordersID) throws IOException, JSONException;

    BaseJson takeMeal(int ordersID) throws IOException, JSONException;

    BaseJson comment(int ordersID) throws IOException, JSONException;

    BaseJson pay(int orderID,String deliverWay) throws IOException, JSONException;

    BaseJson updateTimeSlot(int orderID,String timeSlot) throws  IOException,JSONException;

    BaseJson updateFinishTime(int orderID) throws IOException,JSONException;

    BaseJson updateStartTime(int orderID) throws IOException,JSONException;

}
