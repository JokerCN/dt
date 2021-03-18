package com.iquantex.stl.dt.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.util.Map;

@Slf4j
//TODO: implementation
@Validated
public class Request {

    public enum Method{
        POST, GET
    }

    private String ipAddress;
    private Method method;
    private int port;
    private String path;
    private HttpEntity httpEntity;
    private Map<String, ?> variables;

    public void setVariables(Map<String, ?> variables){
        this.variables = variables;
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

    public String getIpAddress(){
        return this.ipAddress;
    }

    public void setMethod(Method method){
        this.method = method;
    }

    public Method getMethod(){
        return this.method;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl(){
        return getUrl(port, path);
    }

    public Map<String, ?> getVariables(){
        return variables;
    }

    /**
     * @param port bigger than 0
     * @param path start with /
     * @return url
     */
    private String getUrl(@Min(0) int port, String path){
        return StringUtils.isNotBlank(path)
                ? String.format("http://%s:%d%s", this.ipAddress, port, path)
                : String.format("http://%s:%d", this.ipAddress, port);
    }

    @Override
    public String toString() {
        return "Request{" +
                "url=" + getUrl() +
                ", method=" + method +
                ", httpEntity=" + httpEntity +
                ", variables=" + variables +
                '}';
    }
}
