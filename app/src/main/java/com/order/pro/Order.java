package com.order.pro;

public class Order {
    long ORDER_NUMBER;
    String ORD_TYPE;
    String ORDER_DATE_TIME;
    String TOKEN_NUMBER;
    String CUST_NAME;
    String CONT_DETAILS;
    String INF_SHORT_CODE;
    String COMPLETED_BY;

    public Order() {
    }

    public long getORDER_NUMBER() {
        return ORDER_NUMBER;
    }

    public void setORDER_NUMBER(long ORDER_NUMBER) {
        this.ORDER_NUMBER = ORDER_NUMBER;
    }

    public String getORD_TYPE() {
        return ORD_TYPE;
    }

    public void setORD_TYPE(String ORD_TYPE) {
        this.ORD_TYPE = ORD_TYPE;
    }

    public String getORDER_DATE_TIME() {
        return ORDER_DATE_TIME;
    }

    public void setORDER_DATE_TIME(String ORDER_DATE_TIME) {
        this.ORDER_DATE_TIME = ORDER_DATE_TIME;
    }

    public String getTOKEN_NUMBER() {
        return TOKEN_NUMBER;
    }

    public void setTOKEN_NUMBER(String TOKEN_NUMBER) {
        this.TOKEN_NUMBER = TOKEN_NUMBER;
    }

    public String getCUST_NAME() {
        return CUST_NAME;
    }

    public void setCUST_NAME(String CUST_NAME) {
        this.CUST_NAME = CUST_NAME;
    }

    public String getCONT_DETAILS() {
        return CONT_DETAILS;
    }

    public void setCONT_DETAILS(String CONT_DETAILS) {
        this.CONT_DETAILS = CONT_DETAILS;
    }

    public String getINF_SHORT_CODE() {
        return INF_SHORT_CODE;
    }

    public void setINF_SHORT_CODE(String INF_SHORT_CODE) {
        this.INF_SHORT_CODE = INF_SHORT_CODE;
    }

    public String getCOMPLETED_BY() {
        return COMPLETED_BY;
    }

    public void setCOMPLETED_BY(String COMPLETED_BY) {
        this.COMPLETED_BY = COMPLETED_BY;
    }
}
