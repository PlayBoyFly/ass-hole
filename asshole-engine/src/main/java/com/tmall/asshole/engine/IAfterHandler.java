package com.tmall.asshole.engine;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface IAfterHandler<F,C> {
	 boolean afterHandle(F f, C c) throws Exception;
}
