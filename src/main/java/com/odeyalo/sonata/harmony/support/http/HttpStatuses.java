package com.odeyalo.sonata.harmony.support.http;

import org.springframework.http.ResponseEntity;

public final class HttpStatuses {

    public static <T> ResponseEntity<T> ok() {
        return ResponseEntity.ok().build();
    }

    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    public static <T> ResponseEntity<T> accepted() {
        return ResponseEntity.accepted().build();
    }

    public static <T> ResponseEntity<T> accepted(T body) {
        return ResponseEntity.accepted().body(body);
    }

    public static <T> ResponseEntity<T> badRequest() {
        return ResponseEntity.badRequest().build();
    }

    public static <T> ResponseEntity<T> unprocessableEntity() {
        return ResponseEntity.unprocessableEntity().build();
    }

    public static <T> ResponseEntity<T> unprocessableEntity(T body) {
        return ResponseEntity.unprocessableEntity().body(body);
    }

    public <T> ResponseEntity<T> badRequest(T body) {
        return ResponseEntity.badRequest().body(body);
    }
}
