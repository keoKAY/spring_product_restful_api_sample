package co.istad.productapisimpledemo.features.file;


import co.istad.productapisimpledemo.features.file.dto.FileResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileUploadService {
    FileResponse upload(MultipartFile file);
    List<FileResponse> uploadMultipleFiles(List<MultipartFile> files);
    FileResponse findByName(String name);
    Page<FileResponse> findAll(int pageNumber, int pageSize);
    void deleteByName(String name);

}
