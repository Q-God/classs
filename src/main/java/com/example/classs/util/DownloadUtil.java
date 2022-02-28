package com.example.classs.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DownloadUtil {

    /**
     * 下载文件
     *
     * @param response
     * @param file
     * @param finishDeleteFile 下载完成之后是否需要删除文件
     */
    public static void download(HttpServletResponse response, File file, boolean finishDeleteFile) {
        FileInputStream fis = null;
        ServletOutputStream out = null;
        try {
            // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String downLoadName = new String(file.getName().getBytes(StandardCharsets.UTF_8), "iso8859-1");
            // 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + downLoadName);
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
            fis = new FileInputStream(file);
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[1024];
            while ((b = fis.read(buffer)) != -1) {
                out.write(buffer, 0, b);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (finishDeleteFile) {
                if ((file != null) && file.exists()) {
                    file.delete();
                }
            }
        }
    }

}
