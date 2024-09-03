package com.aditya.electronic.store.services.impl;

import com.aditya.electronic.store.exceptions.BadApiRequest;
import com.aditya.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFileName = file.getOriginalFilename();
        logger.info("Logger : {}",originalFileName);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String fileNameWithExtension = fileName+extension;
        String fullPathWithExtension = path+ File.pathSeparator+fileNameWithExtension;
        if(fullPathWithExtension.equalsIgnoreCase(".png") || fullPathWithExtension.equalsIgnoreCase(".jpg") || fullPathWithExtension.equalsIgnoreCase(".jpeg"))
        {
            File folder = new File(path);
            if(!folder.exists())
            {
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullPathWithExtension));
            return fileNameWithExtension;
        }
        else {
            throw new BadApiRequest("Image with "+extension+"Not Allowed");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path+ File.separator+name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
