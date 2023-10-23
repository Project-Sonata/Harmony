package com.odeyalo.sonata.harmony.service.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.codec.multipart.FilePart;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class FileUploadTarget {
    String id;
    FilePart filePart;
}
