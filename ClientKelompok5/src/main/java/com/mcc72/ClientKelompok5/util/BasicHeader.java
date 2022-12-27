package com.mcc72.ClientKelompok5.util;

import org.apache.tomcat.util.codec.binary.Base64;
import java.nio.charset.Charset;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 *
 * @author HP
 */
public class BasicHeader {
    
    public static String createHeaders() {
        Authentication authtication = SecurityContextHolder.getContext().getAuthentication();
        String auth = authtication.getName() + ":" + authtication.getCredentials();
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        return new String(encodedAuth);
    }
}
