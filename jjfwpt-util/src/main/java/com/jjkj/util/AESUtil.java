package com.jjkj.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtil {
    private static final String defaultCharset = "UTF-8";
    private static final String KEY_AES = "AES";
    private static final String KEY = "57A884C9C8B3D7C562E025E6B49C1EE75C3AE9B";
    private static SecretKey secretKey;
    //解决Linux下解密失败问题
    static {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance(KEY_AES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //2.根据ecnodeRules规则初始化密钥生成器
        //生成一个128位的随机源,根据传入的字节数组
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        secureRandom.setSeed(KEY.getBytes());
        kgen.init(128,secureRandom);
        //3.产生原始对称密钥
        secretKey = kgen.generateKey();
    }
    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key 加密密码
     * @return
     */
    public static String encrypt(String data) {
        return doAES(data, KEY, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key 解密密钥
     * @return
     */
    public static String decrypt(String data) {
        return doAES(data, KEY, Cipher.DECRYPT_MODE);
    }



    /**
     * 加解密
     *
     * @param data 待处理数据
     * @param password  密钥
     * @param mode 加解密mode
     * @return
     */
    private static String doAES(String data, String key, int mode) {
        try {
            //判断是加密还是解密
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            //true 加密内容 false 解密内容
            if (encrypt) {
                content = data.getBytes(defaultCharset);
            } else {
                content = parseHexStr2Byte(data);
            }

            //4.获得原始对称密钥的字节数组
            byte[] enCodeFormat = secretKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, KEY_AES);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(KEY_AES);// 创建密码器
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(mode, keySpec);// 初始化
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
                //将二进制转换成16进制
                return parseByte2HexStr(result);
            } else {
                return new String(result, defaultCharset);
            }
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        Long start=System.currentTimeMillis();
        String content = "eyJhbGciOiJIUzI1NiJ9.eyJjdXJyZW50ZGF0ZSI6MTU1NzIzODY5MzIyNywiaWQiOjExMjQ3MjcwNTMwMTEyMDIwNTAsInVzZXJuYW1lIjoid2oyIn0.4f6X0n6icIR-VqwlgPX-nZDh_3wjsBxTIaK6LPhCGdM2";
        System.out.println("加密前：" + content);
        System.out.println("加密密钥和解密密钥：" + KEY);
        String encrypt = encrypt(content);
        System.out.println("加密后：" + encrypt);
        String decrypt = decrypt(encrypt);
        System.out.println("解密后：" + decrypt);
        long end=System.currentTimeMillis();
        System.out.println(end-start);
    }
}