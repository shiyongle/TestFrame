package com.dongnao.autotest.common.tuple;

/**
 * 
 * @author Administrator
 *
 * @param <A>
 * @param <B>
 */
public class Tuple2<A, B> extends Tuple {
    private static final long serialVersionUID = -8719929625763890302L;

    private A a;

    private B b;

    public Tuple2(A a, B b) {
	this.a = a;
	this.b = b;
    }

    public A getA() {
	return a;
    }

    public void setA(A a) {
	this.a = a;
    }

    public B getB() {
	return b;
    }

    public void setB(B b) {
	this.b = b;
    }
}
