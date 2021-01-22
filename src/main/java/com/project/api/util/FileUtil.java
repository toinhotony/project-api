package com.project.api.util;


import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static void saveFile(MultipartFile file, String path) throws IOException {
        File saveFile = new File(path);
        FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
    }
}