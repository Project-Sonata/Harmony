package com.odeyalo.sonata.harmony.service.upload;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class FileUploadingStatus {
    Type type;
    Event event;

    public static FileUploadingStatus received(FileReceivedEvent event) {
        return of(Type.RECEIVED, event);
    }

    public static FileUploadingStatus started(UploadingStartedEvent event) {
        return of(Type.STARTED, event);
    }

    public static FileUploadingStatus uploaded(UploadingFinishedEvent event) {
        return of(Type.UPLOADED, event);
    }

    public enum Type {
        RECEIVED,
        STARTED,
        UPLOADED
    }

    @Data
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PROTECTED)
    public static abstract class Event {
        // ID of the file that targeted
        String fileId;
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class FileReceivedEvent extends Event {

        public FileReceivedEvent(String fileId) {
            super(fileId);
        }

        public static FileReceivedEvent of(String fileId) {
            return new FileReceivedEvent(fileId);
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class UploadingStartedEvent extends Event {

        public UploadingStartedEvent(String fileId) {
            super(fileId);
        }

        public static UploadingStartedEvent of(String fileId) {
            return new UploadingStartedEvent(fileId);
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class UploadingFinishedEvent extends Event {
        String url;

        protected UploadingFinishedEvent(String fileId, String url) {
            super(fileId);
            this.url = url;
        }

        public static UploadingFinishedEvent of(String fileId, String url) {
            return new UploadingFinishedEvent(fileId, url);
        }
    }
}
