package co.istad.productapisimpledemo.features.file;

import co.istad.productapisimpledemo.features.file.dto.FileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring")
public abstract class FileUploadMapper {
    @Value("${file.base-url}")
    protected String baseUrl;

    @Mapping(target = "url", expression = "java(generateUrl(fileUpload))")
    public abstract FileResponse mapToResponse(FileUpload fileUpload);
    protected String generateUrl(FileUpload fileUpload) {
        // http://localhost:8080/files/image-name.png
        return baseUrl +
                fileUpload.getName() + "." + fileUpload.getExtension();
    }
}
