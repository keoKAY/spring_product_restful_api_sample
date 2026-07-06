package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.dto.file.FileResponse;
import co.istad.productapisimpledemo.entity.FileUpload;
import co.istad.productapisimpledemo.mapper.FileUploadMapper;
import co.istad.productapisimpledemo.repository.FileRepository;
import co.istad.productapisimpledemo.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final FileRepository fileRepository;
    private final FileUploadMapper fileUploadMapper;
    @Value("${file.storage-location}")
    private String fileStorageLocation;
    // customize mapper for our file response
    @Override
    public FileResponse upload(MultipartFile file) {
        return uploadFile(file);
    }

    @Override
    public List<FileResponse> uploadMultipleFiles(List<MultipartFile> files) {
        return List.of();
    }

    @Override
    public FileResponse findByName(String name) {
        return null;
    }

    @Override
    public Page<FileResponse> findAll(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public void deleteByName(String name) {

    }

    // file for saving the uploaded file to the local machine (server)
    private FileResponse uploadFile(MultipartFile file) {
        // 1. Rename the file
        String name = UUID.randomUUID().toString();
        // 2. Get extension of the file
        // file.myimage.png -> png ,pdf
        String ext = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String filename = name + "."+ext ;

        // 3. construct path (/User/kk/Engineering/ite-images/uuid.png)
        Path path = Paths.get(fileStorageLocation+ filename);

        // 4. (save) copy the file to local machine
        try{
            Files.copy(
                    file.getInputStream(),
                    path
            );
            // StandardCopyOption.REPLACE_EXISTING
        }catch(IOException exception){
            // must handle it later
            throw new IllegalArgumentException("Error uploading file");
        }
        // 5. save file upload data to the database
        var fileUpload = new FileUpload();
            fileUpload.setName(name);
            fileUpload.setExtension(ext);
            fileUpload.setCaption("ISTAD media service ");
            fileUpload.setSize(file.getSize());
            fileUpload.setMediaType(file.getContentType());

            fileRepository.save(fileUpload);

          return  fileUploadMapper.mapToResponse(fileUpload);
    }

}
