package com.ese.cloud.client.contants;

/**
 * Created by user on 16/12/22.
 */
public enum UserStatus {

    UN_CHECK("未审核", 0), NORMAL("正常", 1), FREEZE("冻结", 2);


    private String name ;
    private int index ;

    UserStatus( String name , int index ){
        this.name = name ;
        this.index = index ;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
