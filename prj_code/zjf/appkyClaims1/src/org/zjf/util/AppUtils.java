package org.zjf.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author        xuliang
 * @date          2011-08-19
 * @class         AppUtils
 * @extends       Object
 * @description   工具类
 */
public class AppUtils
{
    /**
     * 比较两个字符串是否相同. 
     * 
     * <p>1.""与null返回true
     * <p>2.两者都不为null时，trim之后返回equals的结果
     * 
     * @param one
     * @param two
     * @return
     */
    public static boolean compareString(final String one, final String two)
    {
        String a = (one == null) ? "" : one.trim();
        String b = (two == null) ? "" : two.trim();
        return a.equals(b);
    }
    
    /**
     * 比较两个数值是否相同. 
     * 
     * <p>1.0与null返回true
     * <p>2.两者都不为null时，返回equals的结果
     * 
     * @param one
     * @param two
     * @return
     */
    public static boolean compareNumber(final Number one, final Number two)
    {
        if((one == null && two != null) || (one != null && two == null))
        {
            return false;
        }
        double a = (one == null) ? 0 : one.doubleValue();
        double b = (two == null) ? 0 : two.doubleValue();
        return a == b;
    }
    
    public static boolean isEmpty(final String o)
    {
        return true;
    }
    
    /**
     * 验证IP是否在给定范围内
     * @param ipBegin
     * @param ipEnd
     * @param ipTest
     * @return
     */
    public static boolean isIpValid(String ipBegin, String ipEnd, String ipTest)
    {
        //ip地址分解成4段
        String[] begins = ipBegin.split("\\.");
        String[] ends = ipEnd.split("\\.");
        String[] ips = ipTest.split("\\.");
        
        //前2段肯定相等
        for(int i = 0; i < ips.length - 2; i++)
        {
            if(Integer.parseInt(ips[i]) != Integer.parseInt(begins[i])
                    || Integer.parseInt(ips[i]) != Integer.parseInt(ends[i]))
            {
                return false;
            }
        }
        
        //第3、4段拼接在一起转化成数值类型比较
        
        String c = "000" + ips[2];
        String d = "000" + ips[3];
        c = c.substring(c.length() - 3);
        d = d.substring(d.length() - 3);
        int ip = Integer.parseInt(c + d);
        
        c = "000" + begins[2];
        d = "000" + begins[3];
        c = c.substring(c.length() - 3);
        d = d.substring(d.length() - 3);
        int begin = Integer.parseInt(c + d);
        
        c = "000" + ends[2];
        d = "000" + ends[3];
        c = c.substring(c.length() - 3);
        d = d.substring(d.length() - 3);
        int end = Integer.parseInt(c + d);
        
        if(ip < begin || ip > end)
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * 校验是否为IP地址
     * @param s
     * @return
     */
    public static boolean isIpAddress(String s)
    {
        String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }
    
    /**
     * 根据用户ip尝试获取mac地址
     * @param ip
     * @return
     */
    public static String getMacAddress(String ip)
    {
        if(ip.equals("127.0.0.1"))
        {
            return null;
        }
        
        if(!AppUtils.isIpAddress(ip))
        {
            return null;
        }
        
        //获取mac地址
        String mac = null;
        try
        {
            UdpGetClientMacAddr tool = new UdpGetClientMacAddr(ip);
            mac = tool.GetRemoteMacAddr();
        }
        catch(Exception e)
        {
        }
        return mac;
    }
    
    /**
     * 处理sql转义字符 包含 ' & 会被替换
     * @param column
     * @return
     */
    public static String esc(String column)
    {
        return column.replaceAll("'", "''").replaceAll("&", "' || chr(38) || '");
    }
    
    /**
     * MD5加密字符串，返回32位密文
     * @param source
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encodeByMD5(String source) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(source.getBytes());
        byte[] byteDigest = md.digest();
        int i;
        StringBuilder buf = new StringBuilder(128);
        for (int offset = 0; offset < byteDigest.length; offset++)
        {
            i = byteDigest[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        // 32位加密
        return buf.toString();
    }
    
    public static void main(String[] args)
    {
//        System.out.println(isIpValid("10.10.10.1", "10.10.70.254", "10.10.0.1"));
        try
        {
            System.out.println(encodeByMD5("888888"));
            System.out.println(encodeByMD5("888888"));
            System.out.println(encodeByMD5("888888"));
            System.out.println(encodeByMD5("888888"));
            System.out.println(encodeByMD5("123456"));
            System.out.println(encodeByMD5("123456"));
            System.out.println(encodeByMD5("123456"));
            System.out.println(encodeByMD5("zxy19870616"));
        }
        catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
