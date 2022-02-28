package com.example.classs.service.impl;

import com.example.classs.entity.Protocol;
import com.example.classs.service.ProtocolService;
import com.example.classs.util.FileUtil;
import org.springframework.stereotype.Service;

@Service
public class ProtocolServiceImpl implements ProtocolService {

    String headerTemplate = "Windows Registry Editor Version 5.00 \n %s";

    private static String portocolTemplate = "[HKEY_CLASSES_ROOT\\%s]\n" +
            "\"URL Protocol\"=\"\"\n" +
            "@=\"URL:%s\"\n" +
            "\n" +
            "[HKEY_CLASSES_ROOT\\%s\\shell]\n" +
            "\n" +
            "[HKEY_CLASSES_ROOT\\%s\\shell\\open]\n" +
            "\n" +
            "[HKEY_CLASSES_ROOT\\%s\\shell\\open\\command]\n" +
            "@=\"\\\"%s\"\\\" \\\"%%1\\\"\"";

    @Override
    public String createProtocol(Protocol protocol) {
        String sechema = protocol.getProtocol();
        String target = protocol.getTarget();
        String pro = String.format(portocolTemplate, sechema, sechema, sechema, sechema, sechema, target);
        String reg = String.format(headerTemplate, pro);
        String path = String.format("tmp/%s.reg", sechema);
        FileUtil.writeFile(reg, path);
        return path;
    }

}
