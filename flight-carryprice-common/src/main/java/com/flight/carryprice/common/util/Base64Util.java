package com.flight.carryprice.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
	    /**    
	     * BASE64解密   
	   * @param key          
	     * @return          
	     * @throws Exception          
	     */              
	    public static byte[] decryptBASE64(String key) throws Exception {               
	        return (new BASE64Decoder()).decodeBuffer(key);               
	    }               
	                  
	    /**         
	     * BASE64加密   
	   * @param key          
	     * @return          
	     * @throws Exception          
	     */              
	    public static String encryptBASE64(byte[] key) throws Exception {               
	        return (new BASE64Encoder()).encodeBuffer(key);               
	    }       
	         
	    public static void main(String[] args) throws Exception     
	    {     
	        String data = Base64Util.encryptBASE64("http://aub.iteye.com/".getBytes());     
	        System.out.println("加密后："+data);     
	             
	        byte[] byteArray = Base64Util.decryptBASE64("934ded36192f46bfa7c5cedba6df88da");     
	        System.out.println("解密后："+new String(byteArray));     
	    }     
}
