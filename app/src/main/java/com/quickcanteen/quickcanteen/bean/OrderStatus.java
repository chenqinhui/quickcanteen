package com.quickcanteen.quickcanteen.bean;


public enum OrderStatus {

    NEW(10, "新建"),
    NOT_PAID(20, "待支付"),
    PREPARING(30, "准备中"),
    PEND_TO_TAKE(40, "待取餐"),
    PEND_TO_DISTRIBUTE(50, "待配送"),
    NOT_COMMENT(60, "待评价"),
    COMPLETE(70, "已完成"),
    CANCELLED(80, "取消"),
    CLOSED(90, "被商家取消"),
    WAITING(100,"等待商家接单"),
    DISTRIBUTING(110,"配送中");


    OrderStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


    public static OrderStatus valueOf(int value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.value == value) {
                return orderStatus;
            }
        }
        return NEW;
    }

    private final int value;
    private final String desc;

}
