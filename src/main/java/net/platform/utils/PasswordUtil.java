package net.platform.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * 
 * 功能描述：密码加密
 * 
 * @author 
 * 
 * @version 0.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class PasswordUtil {

    /**
     * JAVA6支持以下任意一种算法 PBEWITHMD5ANDDES PBEWITHMD5ANDTRIPLEDES
     * PBEWITHSHAANDDESEDE PBEWITHSHA1ANDRC2_40 PBKDF2WITHHMACSHA1
     * 
     * 定义使用的算法为:PBEWITHMD5andDES算法
     */
    public static final String ALGORITHM = "PBEWithMD5AndDES";// 加密算法
    /**
     * 密钥
     */
    public static final String Salt = "20171031";// 密钥

    /**
     * 定义迭代次数为1000次
     */
    private static final int ITERATIONCOUNT = 1000;

    /**
     * 
     * 功能描述：获取加密算法中使用的盐值,
     * 
     * 解密中使用的盐值必须与加密中使用的相同才能完成操作. 盐长度必须为8字节
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:24:30
     *        </p>
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getSalt() throws Exception {
        // 实例化安全随机数
        SecureRandom random = new SecureRandom();
        // 产出盐
        return random.generateSeed(8);
    }

    /**
     * 
     * 功能描述：产出盐
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:24:58
     *        </p>
     * @return byte[]
     */
    public static byte[] getStaticSalt() {
        // 产出盐
        return Salt.getBytes();
    }

    /**
     * 
     * 功能描述：根据PBE密码生成一把密钥
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:25:24
     *        </p>
     * @param password
     * @return Key
     */
    private static Key getPBEKey(String password) {
        // 实例化使用的算法
        SecretKeyFactory keyFactory;
        SecretKey secretKey = null;
        try {
            keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 设置PBE密钥参数
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            // 生成密钥
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return secretKey;
    }

    /**
     * 
     * 功能描述：加密明文字符串
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:26:12
     *        </p>
     * @param plaintext
     * @param password
     * @param salt
     * @return String
     */
    public static String encrypt(String plaintext, String password, byte[] salt) {

        Key key = getPBEKey(password);
        byte[] encipheredData = null;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATIONCOUNT);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

            encipheredData = cipher.doFinal(plaintext.getBytes());
        } catch (Exception e) {
        }
        return bytesToHexString(encipheredData);
    }

    /**
     * 
     * 功能描述：解密密文字符串
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:27:18
     *        </p>
     * @param ciphertext
     * @param password
     * @param salt
     * @return String
     */
    public static String decrypt(String ciphertext, String password, byte[] salt) {

        Key key = getPBEKey(password);
        byte[] passDec = null;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(getStaticSalt(), ITERATIONCOUNT);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

            passDec = cipher.doFinal(hexStringToBytes(ciphertext));
        }

        catch (Exception e) {
            // TODO: handle exception
        }
        return new String(passDec);
    }

    /**
     * 
     * 功能描述：将字节数组转换为十六进制字符串
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:27:56
     *        </p>
     * @param src
     * @return String
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 
     * 功能描述：将十六进制字符串转换为字节数组
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:28:20
     *        </p>
     * @param hexString
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 
     * 功能描述：加盐输出
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:28:31
     *        </p>
     * @param c
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 
     * 功能描述：测试
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:28:48
     *        </p>
     * @param args
     */
    public static void main(String[] args) {
//        int i = 10;
//        for (int j = 0; j < i; j++) {
//            if ((j) % 3 == 0) {
//                System.out.print("<br>");
//            } else {
//                System.out.print(j);
//            }
//        }
//        System.out.print(-1 % 2 == 0);
        String str = "admin";
        String password = "123456";

        try {
            byte[] salt = PasswordUtil.getStaticSalt();
            String ciphertext = PasswordUtil.encrypt(str, password, salt);

            String plaintext = PasswordUtil.decrypt(ciphertext, password, salt);
            System.out.println("   "+ciphertext+"     " +plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}