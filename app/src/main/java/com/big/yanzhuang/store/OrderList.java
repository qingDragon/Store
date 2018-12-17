package com.big.yanzhuang.store;

public class OrderList {
    private String orderid = "";
    private String signuptime = "";
    private String users = "";
    private String usernum= "";
    private String goodscomments = "";
    private String datas = "";
    public OrderList(String users,String signuptime,String goodscomments,String orderid){
        this.users = users;
        this.signuptime = signuptime;
        this.goodscomments = goodscomments;
        this.orderid = orderid;
    }
    public OrderList(){
        this.users = "";
        this.signuptime = "";
        this.goodscomments = "";
        this.orderid ="";
    }
    public String getUsers() {
        return users;
    }

    public String getSignuptime() {
        return signuptime;
    }

    public String getGoodscomments() {
        return goodscomments;
    }

    public String getOrderid() {
        return orderid;
    }

    @Override
    public String toString() {
        return this.getUsers();
    }
}
