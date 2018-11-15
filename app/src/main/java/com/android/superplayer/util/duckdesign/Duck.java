package com.android.superplayer.util.duckdesign;

/**
 * anther: created by zuochunsheng on 2018/11/15 20 : 58
 * descript :鸭子
 */
public class Duck {


    private int duckId;
    private String duckName;

    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;



    public void performFly() {

        flyBehavior.fly() ;

    }
    public void performQuack() {

        quackBehavior.quack() ;

    }






    public int getDuckId() {
        return duckId;
    }

    public void setDuckId(int duckId) {
        this.duckId = duckId;
    }

    public String getDuckName() {
        return duckName;
    }

    public void setDuckName(String duckName) {
        this.duckName = duckName;
    }

    public FlyBehavior getFlyBehavior() {
        return flyBehavior;
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public QuackBehavior getQuackBehavior() {
        return quackBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

}
