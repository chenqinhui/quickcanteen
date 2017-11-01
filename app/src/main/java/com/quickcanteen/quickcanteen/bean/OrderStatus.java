package com.quickcanteen.quickcanteen.bean;


public enum OrderStatus {

    NEW(10, "新建",""),
    NOT_PAID(20, "待支付","下单"),
    PREPARING(30, "准备中","接单"),
    PEND_TO_TAKE(40, "待取餐","到窗"),
    DISTRIBUTING(50, "配送中","开始配送"),
    NOT_COMMENT(60, "待评价","取餐"),
    COMPLETE(70, "已完成","评价"),
    CANCELLED(80, "取消","退订"),
    CLOSED(90, "被商家取消","拒接"),
    CHECKING(100,"待接单","支付"),
    PEND_TO_DISTRIBUTE(110,"待配送","准备完成");


    OrderStatus(int value, String desc,String terminal) {
        this.value = value;
        this.desc = desc;
        this.terminal = terminal;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getTerminal() {
        return terminal;
    }

    public static OrderStatus valueOf(int value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.value == value) {
                return orderStatus;
            }
        }
        return NEW;
    }
    public static OrderStatus termialof(String terminal) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.terminal == terminal) {
                return orderStatus;
            }
        }
        return NEW;
    }

    private final int value;
    private final String desc;
    private final String terminal;
}
