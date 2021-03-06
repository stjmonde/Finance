package com.lnl.finance.util;

import java.io.IOException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class DES {
	
	private static final String DESKey = "qq:413742682";
	
	
	private static byte[] iv1 = { (byte) 0x12, (byte) 0x34, (byte) 0x56,
			(byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };

	/**
	 * 加密编码函数
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public byte[] desEncrypt(byte[] plainText) throws Exception {
		
		IvParameterSpec iv = new IvParameterSpec(iv1);
		byte[] keyByte = DESKey.getBytes();
		DESKeySpec dks = new DESKeySpec(keyByte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
		
        
//        SecureRandom sr = new SecureRandom();
//	     byte rawKeyData[] = desKey;
//	     DESKeySpec dks = new DESKeySpec(rawKeyData);
//	     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//	     SecretKey key = keyFactory.generateSecret(dks);
//	     Cipher cipher = Cipher.getInstance("DES");
//	     cipher.init(Cipher.ENCRYPT_MODE, key, sr);
//	     byte data[] = plainText;
//	     byte encryptedData[] = cipher.doFinal(data);
//	     return encryptedData;
	}
	
	/**
	 * 解密编码函数
	 * @param encryptText
	 * @return
	 * @throws Exception
	 */
	 public byte[] desDecrypt(byte[] encryptText) throws Exception {
		byte[] keyByte = DESKey.getBytes();
		DESKeySpec dks = new DESKeySpec(keyByte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// key的长度不能够小于8位字节
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(iv1);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		return cipher.doFinal(encryptText); 
			
// 	     SecureRandom sr = new SecureRandom();
// 	     byte rawKeyData[] = DESKey.getBytes();
// 	     DESKeySpec dks = new DESKeySpec(rawKeyData);
// 	     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
// 	     SecretKey key = keyFactory.generateSecret(dks);
// 	     Cipher cipher = Cipher.getInstance("DES");
// 	     cipher.init(Cipher.DECRYPT_MODE, key, sr);
// 	     byte encryptedData[] = encryptText;
// 	     byte decryptedData[] = cipher.doFinal(encryptedData);
// 	     return decryptedData;
 	 }

	 /**
	  * 实现调用加密
	  * @param input
	  * @return
	  */
	public String encrypt(String input) {
		String result = "input";
		try {
			result = base64Encode(desEncrypt(input.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 调用解密
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String input) throws Exception {

		byte[] result = base64Decode(input);

		return new String(desDecrypt(result));

	}

	public String base64Encode(byte[] s) {
		if (s == null)
			return null;
		return Base64.encodeToString(s, Base64.DEFAULT);

	}
	
	public byte[] base64Decode(String s) throws IOException {

		if (s == null)

			return null;

		return Base64.decode(s, Base64.DEFAULT);
	}
}