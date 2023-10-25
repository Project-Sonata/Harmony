package testing.spring.web;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.LinkedMultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

public final class FilePartStub implements FilePart {
    private final Flux<DataBuffer> content;
    private long length;

    public FilePartStub(Flux<DataBuffer> content, long length) {
        this.content = content;
        this.length = length;
    }

    public FilePartStub(Flux<DataBuffer> content) {
        this.content = content;
    }

    @Override
    @NotNull
    public String filename() {
        return "miku.png";
    }

    @Override
    @NotNull
    public Mono<Void> transferTo(@NotNull Path dest) {
        return DataBufferUtils.write(content, dest);
    }

    @Override
    @NotNull
    public String name() {
        return "image";
    }

    @Override
    @NotNull
    public HttpHeaders headers() {
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(length));
        return HttpHeaders.readOnlyHttpHeaders(headers);
    }

    @Override
    @NotNull
    public Flux<DataBuffer> content() {
        return content;
    }
}
