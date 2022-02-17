package com.yemei.image.http;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.Header;

@Setter
@Getter
public class Response {
    private int statusCode;
    private String localizedMessage;
    private String message;
    private String result;
    private Header[] headers;
    private Throwable throwable;
}
