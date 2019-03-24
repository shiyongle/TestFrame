package com.dongnao.autotest.common.tuple;

/**
 * 
 * @author Administrator
 *
 * @param <A>
 * @param <B>
 * @param <C>
 */
public class Tuple3<A, B, C> extends Tuple2<A, B> {
    private static final long serialVersionUID = -8719929625763890303L;

    private C c;

    public Tuple3(A a, B b, C c) {
	super(a, b);
	this.c = c;
    }

    public C getC() {
	return c;
    }

    public void setC(C c) {
	this.c = c;
    }
}
