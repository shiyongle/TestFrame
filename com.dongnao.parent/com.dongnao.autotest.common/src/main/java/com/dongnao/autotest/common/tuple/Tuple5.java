package com.dongnao.autotest.common.tuple;

/**
 * 
 * @author Administrator
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 * @param <E>
 */
public class Tuple5<A, B, C, D, E> extends Tuple4<A, B, C, D> {
	private static final long serialVersionUID = -8719929625763890305L;
	
	public final E e;
     
    public Tuple5(A a, B b, C c, D d, E e) {
    	super(a, b, c, d);
        this.e = e;
    }
}
