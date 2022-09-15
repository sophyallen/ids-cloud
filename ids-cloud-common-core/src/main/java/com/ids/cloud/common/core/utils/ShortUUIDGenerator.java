package com.ids.cloud.common.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ID生成器
 * 以62进制（字母加数字）生成19位UUID
 * 参考 http://pittlu.iteye.com/blog/2093880
 * @author michael
 * @date 2018/5/2
 */
public class ShortUUIDGenerator {

    private final static char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    /**
     * 支持的最大进制数
     */
    private static final int MAX_RADIX = DIGITS.length;
    private final static Map<Character, Integer> DIGIT_MAP = new HashMap<Character, Integer>();
    static {
        for (int i = 0; i < DIGITS.length; i++) {
            DIGIT_MAP.put(DIGITS[i], (int) i);
        }
    }
    
    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     * @return
     */
    public static String randomUUID() {
        UUID uuid = UUID.randomUUID();
        return digits(uuid.getMostSignificantBits() >> 32, 8) +
                digits(uuid.getMostSignificantBits() >> 16, 4) +
                digits(uuid.getMostSignificantBits(), 4) +
                digits(uuid.getLeastSignificantBits() >> 48, 4) +
                digits(uuid.getLeastSignificantBits(), 12);
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return toString(hi | (val & (hi - 1)), MAX_RADIX)
                .substring(1);
    }

    /**
     * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽）
     * @param i
     * @param radix
     * @return
     */
    private static String toString(long i, int radix) {
        final int size = 65;
        int charPos = 64;

        char[] buf = new char[size];
        i = -i;

        while (i <= -radix) {
            buf[charPos--] = DIGITS[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = DIGITS[(int) (-i)];

        return new String(buf, charPos, (size - charPos));
    }

    /**
     * long 型转 数字和小写字母
     * @param num
     * @return
     */
    public static String toLowerString(long num) {
        return toString(num, 36);
    }
}
