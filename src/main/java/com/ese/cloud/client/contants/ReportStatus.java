package com.ese.cloud.client.contants;

/**
 * Created by user on 16/12/24.
 */
public enum ReportStatus {

    UN_CHECK("未处理", 0), CHECKING("处理中", 1), CHECKED("已处理", 2);


    private String name ;
    private int index ;

    ReportStatus( String name , int index ){
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
