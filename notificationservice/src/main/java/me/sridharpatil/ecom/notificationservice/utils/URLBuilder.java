package me.sridharpatil.ecom.notificationservice.utils;

import lombok.Getter;

import java.util.Map;

@Getter
public class URLBuilder {
    private String baseUrl;
    private String path;
    private String pathParam;
    private Map<String, String> queryParams;
//    private Map<String, String> headers;
//    private Object body;


    public static URLBuilder getBuilder() {
        return new URLBuilder();
    }

    public URLBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public URLBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public URLBuilder setPathParam(String pathParam) {
        this.pathParam = pathParam;
        return this;
    }

    public URLBuilder setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public String build() {
        StringBuilder url = new StringBuilder();
        url.append(baseUrl);
        url.append(path);
        if (pathParam != null) {
            url.append("/");
            url.append(pathParam);
        }
        if (queryParams != null) {
            url.append("?");
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                url.append(entry.getKey());
                url.append("=");
                url.append(entry.getValue());
                url.append("&");
            }
            url.deleteCharAt(url.length() - 1);
        }
        return url.toString();
    }
}
