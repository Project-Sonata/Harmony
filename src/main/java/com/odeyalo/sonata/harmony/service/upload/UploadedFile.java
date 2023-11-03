package com.odeyalo.sonata.harmony.service.upload;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class UploadedFile {
    String fileId;
    FileUrl fileUrl;
}
