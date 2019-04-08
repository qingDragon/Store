package com.big.yanzhuang.store;

/**
 * 订单类
 */
public class OrderList {
    private String orderid = "";
    private String signuptime = "";
    private String users = "";
    private String usernum= "";
    private String goodscomments = "";
    private String datas = "";
    public OrderList(String users,String signuptime,String goodscomments,String orderid,String usernum){
        this.users = users;
        this.signuptime = signuptime;
        this.goodscomments = goodscomments;
        this.orderid = orderid;
        this.usernum = usernum;
    }
    public OrderList(){
        this.users = "";
        this.signuptime = "";
        this.goodscomments = "";
        this.orderid ="";
        this.usernum = "";
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

    public String getUsernum(){return usernum;}

    @Override
    public String toString() {
        return this.getUsers();
    }
}
