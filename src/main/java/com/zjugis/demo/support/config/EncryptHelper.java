package com.zjugis.demo.support.config;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author <a href="mailto:zhangbixi.cool@163.com">zhangbixi</a>
 * @version 1.0
 * @date: 2015/3/20
 */
public class EncryptHelper {

    private static Cipher cipher;
    private final static Log LOG = LogFactory.getLog(EncryptHelper.class);

    static {
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decodeBase64("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAI/vQqErd8s4eTFixaoNQZ6wQKtaV+x/kyIdyM/802EDzfHYsE5LIMS0keVSYn2yd+IZqmiubSI4GLun9EhEuOsCAwEAAQ=="))));
        } catch (Exception ignored) {
        }
    }

    public static String decrypt(String value) {
        try {
            return new String(cipher.doFinal(Base64.decodeBase64(value)), "utf-8");
        } catch (Exception e) {
            LOG.warn("Error to decrypt value [" + value + "]");
            return value;
        }
    }
}
