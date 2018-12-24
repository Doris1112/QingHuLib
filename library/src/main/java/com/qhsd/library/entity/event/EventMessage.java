package com.qhsd.library.entity.event;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Doris.
 * @date 2018/12/19.
 */

public class EventMessage {

    private String pager;
    private String type;
    private int typeInt;
    private boolean flag;

    private Map<String, Object> extra;

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

    public Map<String, Object> getExtra() {
        if (extra == null) {
            extra = new HashMap<>(0);
        }
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public EventMessage putExtra(String key, Object value) {
        if (extra == null) {
            extra = new HashMap<>(0);
        }
        extra.put(key, value);
        return this;
    }

    public EventMessage removeExtra(String key) {
        if (extra != null) {
            extra.remove(key);
        }
        return this;
    }
}
