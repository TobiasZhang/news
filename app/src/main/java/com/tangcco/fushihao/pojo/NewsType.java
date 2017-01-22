package com.tangcco.fushihao.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/18.
 */
public class NewsType implements Serializable {
    private int typeId;
    private String typeName;

    public NewsType(){}

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
