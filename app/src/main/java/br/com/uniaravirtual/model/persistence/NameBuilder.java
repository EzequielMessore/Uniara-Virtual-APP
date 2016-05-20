package br.com.uniaravirtual.model.persistence;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.uniaravirtual.util.AppUtil;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class NameBuilder {

    private NameBuilder() {
        super();
    }

    public static NameBuilder getInstance() {
        return LazyHolder.sInstance;
    }

    public synchronized final String buildName(final String name) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            synchronized (messageDigest) {
                final String part1 = getMD5(messageDigest, name);
                final String part2 = getMD5(messageDigest, AppUtil.getAndroidId());
                final String md5 = getMD5(messageDigest, part1 + part2);
                return "_" + md5 + "_";
            }
        } catch (NoSuchAlgorithmException e) {

        }
        return null;
    }

    private synchronized String getMD5(final MessageDigest messageDigest, final String input) {
        byte[] source = new byte[0];
        try {
            source = input.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = null;
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            messageDigest.update(source);
            final byte temp[] = messageDigest.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = temp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            result = new String(str);
        } catch (Exception e) {
        }
        return result;
    }

    private static class LazyHolder {
        private static final NameBuilder sInstance = new NameBuilder();
    }
}
