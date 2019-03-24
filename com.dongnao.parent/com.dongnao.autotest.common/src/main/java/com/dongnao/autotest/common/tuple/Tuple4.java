package com.dongnao.autotest.common.tuple;

/**
 * 
 * @author Administrator
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 */
public class Tuple4<A, B, C, D> extends Tuple3<A, B, C> {
	private static final long serialVersionUID = -8719929625763890304L;
	
	public final D d;
     
    public Tuple4(A a, B b, C c, D d) {
    	super(a, b, c);
        this.d = d;
    }
}
