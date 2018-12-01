package com.big.yanzhuang.store;

public class OrderList {
    private String orderid = "";
    private String signuptime = "";
    private String users = "";
    private String usernum= "";
    private String goodscomments = "";
    private String datas = "";
    public OrderList(String users,String signuptime,String goodscomments){
        this.users = users;
        this.signuptime = signuptime;
        this.goodscomments = goodscomments;
    }
    public OrderList(){
        this.users = "";
        this.signuptime = "";
        this.goodscomments = "";
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

    @Override
    public String toString() {
        return this.getUsers();
    }
}
