package com.zjugis.demo.utils;

/**
 * copy org.springframework.security.core.codec.Hex; version 3.0.8
 * @ClassName: Hex
 * @Description: ${TODO}(用一句话描述该文件)
 * @Author: Unclemiki <a href='mailto:unclemiki@163.com'>UncleMiki</a>
 * @Version V1.0
 * Date           2017/7/14  上午11:09
 **/
public final class Hex {

    private static final char[] HEX = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static char[] encode(byte[] bytes) {
        final int nBytes = bytes.length;
        char[] result = new char[2*nBytes];

        int j = 0;
        for (int i=0; i < nBytes; i++) {
            // Char for top 4 bits
            result[j++] = HEX[(0xF0 & bytes[i]) >>> 4 ];
            // Bottom 4
            result[j++] = HEX[(0x0F & bytes[i])];
        }

        return result;
    }

//    public static byte[] decode(char[] hex) {
//
//    }
}