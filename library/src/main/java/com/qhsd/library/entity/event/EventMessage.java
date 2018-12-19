package com.qhsd.library.entity.event;

/**
 * @author Doris.
 * @date 2018/12/19.
 */

public class EventMessage {

    private String pager;
    private String type;
    private int typeInt;
    private boolean flag;

    public EventMessage(String pager, String type) {
        this.pager = pager;
        this.type = type;
    }

    public EventMessage(String pager, int typeInt) {
        this.pager = pager;
        this.typeInt = typeInt;
    }

    public EventMessage(String pager, boolean flag) {
        this.pager = pager;
        this.flag = flag;
    }

    public EventMessage(String pager, String type, int typeInt) {
        this.pager = pager;
        this.type = type;
        this.typeInt = typeInt;
    }

    public EventMessage(String pager, String type, boolean flag) {
        this.pager = pager;
        this.type = type;
        this.flag = flag;
    }

    public EventMessage(String pager, String type, int typeInt, boolean flag) {
        this.pager = pager;
        this.type = type;
        this.typeInt = typeInt;
        this.flag = flag;
    }

    public String getPager() {
        return pager;
    }

    public void setPager(String pager) {
        this.pager = pager;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(int typeInt) {
        this.typeInt = typeInt;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
