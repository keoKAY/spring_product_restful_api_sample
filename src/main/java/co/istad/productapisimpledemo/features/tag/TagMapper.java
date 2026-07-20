package co.istad.productapisimpledemo.features.tag;
import co.istad.productapisimpledemo.features.tag.dto.TagRequest;
import co.istad.productapisimpledemo.features.tag.dto.TagResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {


    TagResponse toResponse(Tag tag);
    Tag toEntity(TagRequest tagRequest);
}
