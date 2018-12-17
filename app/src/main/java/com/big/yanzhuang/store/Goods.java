package com.big.yanzhuang.store;

public class Goods {
    private String name;
    private String num;
    private String cata;
    public Goods(){
        this.name = "";
        this.num = "";
        this.cata = "";

    }
    public Goods(String name, String num,String cate){
        this.name = name;
        this.num = num;
        this.cata = cate;
    }

    public String getName() {
        return name;
    }

    public String getNum() {
        return num;
    }

    public String getCate() {
        return cata;
    }

    @Override
    public String toString() {
        return this.getName()+this.getNum();
    }
}
