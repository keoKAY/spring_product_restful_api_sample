package co.istad.productapisimpledemo.features.tag;

import co.istad.productapisimpledemo.features.tag.dto.TagRequest;
import co.istad.productapisimpledemo.features.tag.dto.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    // request, response
    TagResponse createTag(TagRequest request);
    Page<TagResponse> getAllTags(Pageable pageable);
}
