package com.mcc72.ClientKelompok5.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.Charset;

public class BasicHeader {

    public static String createHeaders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = authentication.getName() + ":" + authentication.getCredentials();
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        return new String(encodedAuth);
    }

}
