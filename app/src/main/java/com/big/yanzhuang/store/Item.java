package com.big.yanzhuang.store;

/**
 * @package com.big.yanzhuang.store
 * @name Item
 * @date 2019/4/9 14:27
 * @auther Bibi
 * @decribe TODO 物品类
 **/
public class Item {
    private int id;
    private String contents;
    private String parent_id;
    private String is_leaf;
    private String lay_num;

    public Item(int id,String contents,String parent_id,String is_leaf,String lay_num){

        /**
         *@author Bibi
         *@date 2019/4/9 14:36
         *@name Item
         *@returnType
         *@decribe TODO 构造方法
        **/
        this.id = id;
        this.contents = contents;
        this.parent_id = parent_id;
        this.is_leaf = is_leaf;
        this.lay_num = lay_num;
    }

    public int getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public String getParent_id() {
        return parent_id;
    }

    public String getIs_leaf() {
        return is_leaf;
    }

    public String getLay_num() {
        return lay_num;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void setIs_leaf(String is_leaf) {
        this.is_leaf = is_leaf;
    }

    public void setLay_num(String lay_num) {
        this.lay_num = lay_num;
    }

    public String toString(){
        return "id:"+id+",contents:"+contents+",parent_id:"+parent_id;
    }
}
