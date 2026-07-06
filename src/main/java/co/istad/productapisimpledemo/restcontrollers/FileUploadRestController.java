package co.istad.productapisimpledemo.restcontrollers;

import co.istad.productapisimpledemo.dto.file.FileResponse;
import co.istad.productapisimpledemo.mapper.FileUploadMapper;
import co.istad.productapisimpledemo.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadRestController {
    private final FileUploadService fileUploadService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileResponse uploadFile(@RequestPart MultipartFile file) {
        return fileUploadService.upload(file);
    }

    @PostMapping("/multiple")
    public List<FileResponse> uploadMultipleFiles(@RequestPart List<MultipartFile> files) {
        return fileUploadService.uploadMultipleFiles(files);
    }
}
