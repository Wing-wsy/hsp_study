package com.yz.service.base.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 *
 */
public class HttpResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream outputStream;
    private ServletOutputStream servletOutputStream;
    private PrintWriter writer;

    public HttpResponseWrapper(HttpServletResponse response) {
        super(response);
        this.outputStream = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (servletOutputStream == null) {
            servletOutputStream = new ServletOutputStreamWrapper(outputStream);
        }
        return servletOutputStream;
    }

    /**
     * 重写 getWriter 方法，返回经过包装后的 PrintWriter 对象
     *
     * @return 经过包装后的 PrintWriter 对象
     */
    @Override
    public PrintWriter getWriter() {
        if (writer == null) {
            writer = new PrintWriter(getOutputStream());
        }
        return writer;
    }

    /**
     * 获取响应数据，并指定字符集
     *
     * @param charsetName 字符集名称
     * @return 响应数据字符串
     */
    public String getResponseData(String charsetName) {
        Charset charset = Charset.forName(charsetName);
        byte[] bytes = outputStream.toByteArray();
        return new String(bytes, charset);
    }

    /**
     * 设置响应数据，并指定字符集
     *
     * @param responseData 响应数据字符串
     * @param charsetName  字符集名称
     */
    public void setResponseData(String responseData, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        byte[] bytes = responseData.getBytes(charset);
        outputStream.reset();
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            // 处理异常
        }
        setCharacterEncoding(charsetName);
    }


    /**
     * 私有内部类，用于包装 ServletOutputStream 对象
     */
    private static class ServletOutputStreamWrapper extends ServletOutputStream {
        private final ByteArrayOutputStream outputStream;

        /**
         * 构造函数，传入待包装的 ByteArrayOutputStream 对象
         *
         * @param outputStream 待包装的 ByteArrayOutputStream 对象
         */
        public ServletOutputStreamWrapper(ByteArrayOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        /**
         * 重写 write 方法，将指定字节写入输出流
         *
         * @param b 字节
         */
        @Override
        public void write(int b) {
            outputStream.write(b);
        }

        /**
         * 重写 isReady 方法，指示输出流是否准备好接收写入操作
         *
         * @return 始终返回 false，表示输出流未准备好接收写入操作
         */
        @Override
        public boolean isReady() {
            return false;
        }

        /**
         * 重写 setWriteListener 方法，设置写入监听器
         *
         * @param writeListener 写入监听器
         */
        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}
