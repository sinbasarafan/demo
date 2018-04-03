package priv.winnie.demo.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
	
	private static final String KEY_ALGORITHM = "AES"; // 算法名
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"; // 加密方式
	private static final String IV = "0102030405060708"; // 向量因子
	private static final String ENCODE_MODE = "UTF-8"; // 编码格式
	private static final IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
	private static final Base64.Encoder encoder = Base64.getEncoder();
	private static final Base64.Decoder decoder = Base64.getDecoder();
	
	public static byte[] encrypt(String content, String key) throws Exception {
	    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	    cipher.init(Cipher.ENCRYPT_MODE, getKey(key), iv);
	    byte[] encrypted = cipher.doFinal(content.getBytes());
	    return encrypted;
	}
	
	public static String base64Encrypt(String content, String key) throws Exception {
	    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	    cipher.init(Cipher.ENCRYPT_MODE, getKey(key), iv);
	    byte[] encrypted = cipher.doFinal(content.getBytes());
	    return new String(encoder.encode(encrypted), ENCODE_MODE);
	}
	
	public static String decrypt(byte[] content, String key) throws Exception {
	    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	    cipher.init(Cipher.DECRYPT_MODE, getKey(key), iv);
	    byte[] original = cipher.doFinal(content);
	    String originalString = new String(original);
	    return originalString;
	}
	
	public static String base64Decrypt(String content, String key) throws Exception {
	    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	    cipher.init(Cipher.DECRYPT_MODE, getKey(key), iv);
	    byte[] b = decoder.decode(content);
	    byte[] original = cipher.doFinal(b);
	    String originalString = new String(original);
	    return originalString;
	}
	
	private static SecretKeySpec getKey(String key) throws Exception {
	    byte[] temp = key.getBytes();
	    byte[] ba = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
	    for (int i = 0; i < temp.length && i < ba.length; i++) {
	        ba[i] = temp[i];
	    }
	    return new SecretKeySpec(ba, KEY_ALGORITHM);
	}
	
	public static void main(String[] args) throws Exception {
		String key = "priv.weinnie.demo";
		String content = "admin";
		String es = AESUtils.base64Encrypt(content, key);
		System.out.println(es);
		String ds = AESUtils.base64Decrypt(es, key);
		System.out.println(ds);
	}

}
