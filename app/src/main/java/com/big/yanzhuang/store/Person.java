package com.big.yanzhuang.store;

public class Person {
    private String name;
    private String num;
//    private int imageId;
    public Person(String name,String num){
        this.name = name ;
        this.num = num;
//        this.imageId = imageId;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }
//    public int getImageId(){
//        return imageId;
//    }
}
