/**
 * 
 */
package com.pingan.pama.test.invoke.util;

/**
 * @author Administrator
 *
 */
public enum AuthResultEnum {
	/**
	 * 0比对中，1比对一致，2比对不一致，3处理失败
	 */
	ZERO("0"),ONE("1"),TWO("2"),THREE("3");
	
	private String val;
	
	private AuthResultEnum(String val){
		this.val=val;
	}
	
	public String getValue(){
		return val;
	}
}
