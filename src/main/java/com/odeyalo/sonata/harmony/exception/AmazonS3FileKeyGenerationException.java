package com.odeyalo.sonata.harmony.exception;

/**
 * Exception to be thrown when file key for Amazon S3 cannot be generated.
 */
public class AmazonS3FileKeyGenerationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Failed to generate the file key";

    public static AmazonS3FileKeyGenerationException defaultException() {
        return new AmazonS3FileKeyGenerationException();
    }

    public static AmazonS3FileKeyGenerationException withCustomMessage(String message) {
        return new AmazonS3FileKeyGenerationException(message);
    }

    public static AmazonS3FileKeyGenerationException withCustomMessage(String messageFormat, Object... args) {
        return new AmazonS3FileKeyGenerationException(String.format(messageFormat, args));
    }

    public static AmazonS3FileKeyGenerationException withMessageAndCause(String message, Throwable cause) {
        return new AmazonS3FileKeyGenerationException(message, cause);
    }

    public AmazonS3FileKeyGenerationException() {
        super(DEFAULT_MESSAGE);
    }

    public AmazonS3FileKeyGenerationException(String message) {
        super(message);
    }

    public AmazonS3FileKeyGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmazonS3FileKeyGenerationException(Throwable cause) {
        super(cause);
    }
}
