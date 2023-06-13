package com.etjava.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
/**
 * ���get��post���� ȫ������Ĺ�����
 */
public class GenericEncodingFilter implements Filter {
    @Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //����response���ַ�����
        HttpServletResponse myResponse=(HttpServletResponse) response;
        myResponse.setContentType("text/html;charset=UTF-8");
        // ת��Ϊ��Э����ض���
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // ��request��װ��ǿ
        HttpServletRequest myrequest = new MyRequest(httpServletRequest);
        chain.doFilter(myrequest, response);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
//�Զ���request����HttpServletRequest�İ�װ��
class MyRequest extends HttpServletRequestWrapper {
    private HttpServletRequest request;
    //�Ƿ����ı��
    private boolean hasEncode;
    //����һ�����Դ���HttpServletRequest����Ĺ��캯�����Ա�������װ��
    public MyRequest(HttpServletRequest request) {
        super(request);// super����д
        this.request = request;
    }
    // ����Ҫ��ǿ���� ���и���
    @Override
    public Map getParameterMap() {
        // �Ȼ������ʽ
        String method = request.getMethod();
        if (method.equalsIgnoreCase("post")) {
            // post����
            try {
                // ����post����
                request.setCharacterEncoding("utf-8");
                return request.getParameterMap();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (method.equalsIgnoreCase("get")) {
            // get����
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (!hasEncode) { // ȷ��get�ֶ������߼�ֻ����һ��
                for (String parameterName : parameterMap.keySet()) {
                    String[] values = parameterMap.get(parameterName);
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            try {
                                // ����get����
                                values[i] = new String(values[i]
                                        .getBytes("ISO-8859-1"), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                hasEncode = true;
            }
            return parameterMap;
        }
        return super.getParameterMap();
    }
    //ȡһ��ֵ
    @Override
    public String getParameter(String name) {
        Map<String, String[]> parameterMap = getParameterMap();
        String[] values = parameterMap.get(name);
        if (values == null) {
            return null;
        }
        return values[0]; // ȡ�ز����ĵ�һ��ֵ
    }
    //ȡ����ֵ
    @Override
    public String[] getParameterValues(String name) {
        Map<String, String[]> parameterMap = getParameterMap();
        String[] values = parameterMap.get(name);
        return values;
    }
}