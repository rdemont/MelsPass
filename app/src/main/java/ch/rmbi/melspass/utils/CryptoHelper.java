package ch.rmbi.melspass.utils;

import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {
    private static CryptoHelper instance;
    public static final int AES_Key_Size = 256;
    private static String ENC_TYPE = "AES/CBC/PKCS5Padding";
    //private byte[] iv = new byte[]{};
    Cipher cipher;
    //byte[] secretKey;
    //SecretKeySpec aeskeySpec;
    SecretKey secretKey;
    IvParameterSpec ivSpec = new IvParameterSpec(new byte[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
    String KEY_ALIAS = "MelsPassKeys";

    static {
        instance = new CryptoHelper();
    }

    public static CryptoHelper getInstance() {
        return instance;
    }

    private CryptoHelper() {
        // create AES cipher

        try {

            cipher = Cipher.getInstance(ENC_TYPE);
            KeyStore keystore = KeyStore.getInstance("AndroidKeyStore");
            keystore.load(null);
            if (!keystore.containsAlias(KEY_ALIAS)) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                keyGenerator.init(
                        new KeyGenParameterSpec.Builder(KEY_ALIAS,
                                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                                .build());
                secretKey = keyGenerator.generateKey();

                cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            } else {
                secretKey = (SecretKey) keystore.getKey(KEY_ALIAS, null);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        } catch (InvalidAlgorithmParameterException ex) {
            ex.printStackTrace();
        } catch (UnrecoverableKeyException ex) {
            ex.printStackTrace();
        }
    }
/*
        private void createKey() throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(AES_Key_Size);
        SecretKey key = kgen.generateKey();
        secretKey = key.getEncoded();

        iv = new byte[cipher.getBlockSize()];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        ivSpec = new IvParameterSpec(iv);

        aeskeySpec = new SecretKeySpec(secretKey, "AES");
    }
*/

    public byte[] encrypt(byte[] in) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        if (in == null) {
            return null;
        }
        return cipher.doFinal(in);

    }

    public byte[] decrypt(byte[] in) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        if (in == null) {
            return null;
        }
        return cipher.doFinal(in);
    }



    public String encrypt(String in) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (in == null) {
            return null;
        }
        if (in.length() <= 0)
        {
            return "";
        }
        return String.valueOf(encrypt(in.getBytes()));
    }


    public String decrypt(String in) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if (in == null) {
            return null;
        }
        if (in.length() <= 0)
        {
            return "";
        }
        return String.valueOf(decrypt(in.getBytes()));
    }


    public OutputStream encrypt(OutputStream in) throws InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        CipherOutputStream out = new CipherOutputStream(in,cipher);
        return out;
    }

    public OutputStream decrypt(OutputStream in) throws InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        CipherOutputStream out = new CipherOutputStream(in,cipher);
        return out;
    }


    public InputStream encrypt(InputStream in) throws InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        CipherInputStream out = new CipherInputStream(in,cipher);
        return out;
    }

    public InputStream decrypt(InputStream in) throws InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        CipherInputStream out = new CipherInputStream(in,cipher);
        return out;
    }

    private void copy(InputStream is, OutputStream os) throws IOException {
        int i;
        byte[] b = new byte[1024];
        while ((i = is.read(b)) != -1) {
            os.write(b, 0, i);
        }
    }

}
