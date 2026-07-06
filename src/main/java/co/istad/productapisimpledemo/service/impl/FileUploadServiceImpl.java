package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.dto.file.FileResponse;
import co.istad.productapisimpledemo.repository.FileRepository;
import co.istad.productapisimpledemo.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileRepository fileRepository;
    // customize mapper for our file response
    @Override
    public FileResponse upload(MultipartFile file) {
        return null;
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
}
