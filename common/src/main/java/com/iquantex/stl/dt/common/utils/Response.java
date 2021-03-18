package com.iquantex.stl.dt.common.utils;

import org.springframework.http.ResponseEntity;

public class Response {

    private ResponseEntity<String> responseEntity;

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity<String> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public Response(ResponseEntity<String> responseEntity) {
        this.responseEntity = responseEntity;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseEntity=" + responseEntity +
                '}';
    }
}
