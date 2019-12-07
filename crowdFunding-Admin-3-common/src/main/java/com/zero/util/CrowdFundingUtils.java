package com.zero.util;


import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Map;

/**
 * @Author: zero
 * @Description: 工具类，封装了一些众筹项目需要的小工具
 * Date:Create: in 2019/11/12 20:34
 *
 */
public class CrowdFundingUtils extends Object {


    /**
     * 用于判断一个请求是否是异步请求
     * @param request
     * @return
     */
    public static boolean checkAsyncRequest(HttpServletRequest request){
        String accept = request.getHeader("Accept");
        String xRequested = request.getHeader("X-Requested-With");
        if ((stringEffective(accept) && accept.contains("application/json"))
        || (stringEffective(xRequested) && xRequested.equals("XMLHttpRequest"))){
            return true;
        }
        return false;
    }


    /**
     * @Description: 判断 Map是否有效
     * @Author: zero
     * @param map 待验证的map
     * @return boolean ：true有效，false无效
     * @Date 2019/11/12 20:36
     */
    public static <K,V> boolean mapEffective(Map<K,V> map){
        return map != null && map.size() > 0;
    }

    /**
     * @Description: 判断集合是否有效
     * @Author: zero
     * @param collection 待验证的集合
     * @return boolean ：true有效，false无效
     * @Date 2019/11/12 20:38
     */
    public static <E> boolean collectionEffective(Collection<E> collection){
        return collection != null && collection.size() >0 ;
    }

    /**
     * @Description: 判断字符串是否有效
     * @Author: zero
     * @param str 待验证的字符串
     * @return boolean ：true有效，false无效
     * @Date 2019/11/12 20:40
     */
    public static boolean stringEffective(String str){
        return str != null && str.length() > 0;
    }


    /**
     * @Description: MD5加密工具方法
     * @Author: zero
     * @param source 待加密的明文
     * @return String 加密后的密文
     * @Date 2019/11/12 20:53
     */
    public static String md5(String source){

        /**
         * 判断传入的明文字符串是否有效
         */
        if (!stringEffective(source)){
            /**
             * 检测到明文字符串无效，抛出异常 通知方法的调用者
             */
            throw new RuntimeException(CrowdFundingConstant.MESSAGE_CODE_INVALID);
        }

        StringBuilder builder = new StringBuilder();

        char[] characters = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        String algorithm = "MD5";
        try {
            /**
             * 执行加密操作的核心对象
             */
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] inputBytes = source.getBytes();
            /**
             * 执行加密方法，返回加密后的字节数组
             */
            byte[] outputBytes = digest.digest(inputBytes);
            /**
             * 遍历该数组
             */
            for (int i = 0; i < outputBytes.length; i++){
                byte b = outputBytes[i];
                /**
                 * 获取低四位值
                 */
                int lowValue = b & 15;
                /**
                 * 右移四位和15做与运算得到高四位值
                 */
                int highValue = (b >> 4) & 15;
                /**
                 * 以高低四位的值为下标从字符数组中获取对应字符
                 */
                char highCharacter = characters[highValue];
                char lowCharacter = characters[lowValue];
                /**
                 * 拼接
                 */
                builder.append(highCharacter).append(lowCharacter);
            }

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("jk123"));
    }
}
