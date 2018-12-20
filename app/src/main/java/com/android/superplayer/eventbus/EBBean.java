package com.android.superplayer.eventbus;


/**
 * @author Xyjian
 * @Description: TODO(){EventBus传递信息的实体类  简单的公用的}
 * @date: 2018/4/20 9:05
 */
public class EBBean {
    private int value;//根据此辨识是干什么的
    private String stringValue;//字符串值
    private boolean booleanValue;//布尔值
    private int intValue;//整形值

    private EBBean() {
    }

    public EBBean(int index) {
        this.value = index;
    }
    public EBBean(int index, String stringValue) {
        this.value = index;
        this.stringValue = stringValue;
    }

    public EBBean(int index, int intValue) {
        this.value = index;
        this.intValue = intValue;
    }

    public int getValue() {
        return this.value;
    }

    public EBBean setValue(int value) {
        this.value = value;
        return this;
    }

    public String getStringValue() {
        return stringValue;
    }

    public EBBean setStringValue(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public EBBean setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
        return this;
    }

    public int getIntValue() {
        return intValue;
    }

    public EBBean setIntValue(int intValue) {
        this.intValue = intValue;
        return this;
    }
}
