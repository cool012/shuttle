package com.example.hope.service.serviceIpm;

import com.example.hope.config.exception.BusinessException;
import com.example.hope.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @description: 文件服务实现类
 * @author: DHY
 * @created: 2021/02/08 17:31
 */

@Log4j2
@Service
public class FileServiceImp implements FileService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件的相对路径
     */
    @Override
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String fileName = uuid + "." + fileSuffix;
        String workPath = System.getProperty("user.dir");
        String path = File.separator + "upload" + File.separator + fileName;
        File updatePath = new File(workPath + path);
        if (!updatePath.getParentFile().exists()) {
            if (!updatePath.getParentFile().mkdir()) throw new BusinessException(-1, "创建文件夹失败");
        }
        try {
            file.transferTo(updatePath);
        } catch (IOException e) {
            log.error("upload error");
            throw new BusinessException(-1, "上传文件失败");
        }
        return path;
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @return 文件
     * @throws FileNotFoundException 文件不存在
     */
    @Override
    public ResponseEntity<Object> download(String fileName) throws FileNotFoundException {
        String path = "upload/" + fileName;
        File file = new File(path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment;filename=\"%s", fileName));
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt"))
                .body(resource);
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     */
    @Override
    public void remove(String fileName) {
        String workPath = System.getProperty("user.dir");
        File file = new File( workPath + File.separator + "upload" + File.separator + fileName);
        if (file.exists()) {
            if (!file.delete()) BusinessException.check(-1, "文件删除失败");
        } else BusinessException.check(-1, "文件未找到");
    }
}