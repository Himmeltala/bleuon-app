package com.bleuon.utils.http;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {

    public static String getIp(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet != null ? inet.getHostAddress() : null;
                }
            }
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    public static boolean isSameSite(HttpServletRequest request) {
        String compare = "http://localhost:8080";
        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");
        if (origin != null) {
            return origin.startsWith(compare);
        } else if (referer != null) {
            return referer.startsWith(compare);
        } else {
            return true;
        }
    }

    public static String parseCookie(HttpServletRequest request, String key) {
        String value = null;
        String cookieHeader = request.getHeader("Cookie");

        if (cookieHeader != null) {
            String[] cookies = cookieHeader.split("; ");

            for (String cookie : cookies) {
                String[] parts = cookie.split("=");
                if (parts.length == 2) {
                    String name = parts[0];
                    value = parts[1];
                    if (key.equals(name)) {
                        return value;
                    }
                }
            }
        }

        return value;
    }

}
