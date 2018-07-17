package Decryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class Decrypt  {
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";

	public static void decryptfile(String key, File inputFile, File outputFile)
			throws CryptoException, KeyException {
		doDecryption(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
	}
	
	private static void doDecryption(int cipherMode, String key, File inputFile,
			File outputFile) throws CryptoException, KeyException {
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
			
			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);
			
			byte[] outputBytes = cipher.doFinal(inputBytes);
			
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);
			
			inputStream.close();
			outputStream.close();
			
		} catch (NoSuchPaddingException | NoSuchAlgorithmException
				| IllegalBlockSizeException | IOException ex) {
			throw new CryptoException("Error decrypting file", ex);
		}
                catch (InvalidKeyException | BadPaddingException e)
                {
                    throw new KeyException("IP address wrong", e);
                }
	}
}

