package com.dahuaboke.mvc.view;

import com.dahuaboke.mvc.exception.MvcViewException;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author dahua
 * @Date 2021/5/7 22:15
 * @Description mvc
 */
public class MvcDefaultViewResolver implements MvcViewResolver {

    private String prefix;
    private String suffix;
    private String[] excludes;
    private String debugPath;

    @Override
    public void resolve(HttpServletRequest request, HttpServletResponse response, String uri) throws MvcViewException {
        boolean isExclude = checkExclude(uri);
        FileInputStream fis = null;
        ClassPathResource resource;
        File file;
        String filePath = prefix + uri;
        if (!isExclude) {
            filePath = filePath + suffix;
            response.setContentType("text/html;charset=utf-8");
        }
        try {
            if (debugPath != null) {
                filePath = debugPath + filePath;
                file = new File(filePath);
            } else {
                resource = new ClassPathResource(filePath);
                file = resource.getFile();
            }
            OutputStream out = response.getOutputStream();
            fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            throw new MvcViewException(e);
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    public void setDebugPath(String debugPath) {
        this.debugPath = debugPath;
    }

    private boolean checkExclude(String requestURI) {
        for (String s : excludes) {
            if (requestURI.toLowerCase().endsWith(s)) {
                return true;
            }
        }
        return false;
    }
}
