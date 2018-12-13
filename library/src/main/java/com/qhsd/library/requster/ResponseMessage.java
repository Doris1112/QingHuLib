package com.qhsd.library.requster;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class ResponseMessage<T> {

    private boolean Result;
    private int Status;
    private String Message;
    private T InnerData;

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean result) {
        Result = result;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getInnerData() {
        return InnerData;
    }

    public void setInnerData(T innerData) {
        InnerData = innerData;
    }
}
