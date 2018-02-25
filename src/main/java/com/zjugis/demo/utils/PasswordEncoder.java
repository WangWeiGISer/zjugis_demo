package com.zjugis.demo.utils;


import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: PasswordEncoder
 * @Description: ${TODO}(用一句话描述该文件)
 * @Author: Unclemiki <a href='mailto:unclemiki@163.com'>UncleMiki</a>
 * @Version V1.0
 * Date           2017/7/6  下午5:01
 **/
@Service
public class PasswordEncoder {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private static final String algorithm = "MD5";

    private int iterations = 1;

    private boolean encodeHashAsBase64 = false;


    /**
     * 带盐加密
     *
     * @param rawPass
     * @param salt
     * @return
     */
    public String encodePassword(String rawPass, Object salt) {
        String saltedPass = mergePasswordAndSalt(rawPass, salt, false);

        MessageDigest messageDigest = getMessageDigest();

        byte[] digest = messageDigest.digest(utf8Encode(saltedPass));

        // "stretch" the encoded value if configured to do so
        for (int i = 1; i < iterations; i++) {
            digest = messageDigest.digest(digest);
        }
        if (getEncodeHashAsBase64()) {
            return utf8Decode(Base64.encode(digest));
        } else {
            return new String(Hex.encode(digest));
        }

    }

    public boolean getEncodeHashAsBase64() {
        return encodeHashAsBase64;
    }

    /**
     * The encoded password is normally returned as Hex (32 char) version of the hash bytes. Setting this
     * property to true will cause the encoded pass to be returned as Base64 text, which will consume 24 characters.
     *
     * @param encodeHashAsBase64 set to true for Base64 output
     */
    public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
        this.encodeHashAsBase64 = encodeHashAsBase64;
    }

    /**
     * @return
     * @throws IllegalArgumentException
     */
    protected final MessageDigest getMessageDigest() throws IllegalArgumentException {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
        }
    }

    /**
     * @param password
     * @param salt
     * @param strict
     * @return
     */
    protected String mergePasswordAndSalt(String password, Object salt, boolean strict) {
        if (password == null) {
            password = "";
        }

        if (strict && (salt != null)) {
            if ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1)) {
                throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
            }
        }

        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }


    /**
     * Get the bytes of the String in UTF-8 encoded form.
     */
    private byte[] utf8Encode(CharSequence string) {
        try {
            ByteBuffer bytes = CHARSET.newEncoder().encode(CharBuffer.wrap(string));
            byte[] bytesCopy = new byte[bytes.limit()];
            System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());

            return bytesCopy;
        } catch (CharacterCodingException e) {
            throw new IllegalArgumentException("Encoding failed", e);
        }
    }

    /**
     * Decode the bytes in UTF-8 form into a String.
     */
    private String utf8Decode(byte[] bytes) {
        try {
            return CHARSET.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
        } catch (CharacterCodingException e) {
            throw new IllegalArgumentException("Decoding failed", e);
        }
    }
}
