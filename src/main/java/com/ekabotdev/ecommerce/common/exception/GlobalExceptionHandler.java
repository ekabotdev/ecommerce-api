package com.ekabotdev.ecommerce.common.exception;



import com.ekabotdev.ecommerce.category.exception.CategoryAlreadyExistsException;
import com.ekabotdev.ecommerce.category.exception.CategoryNotFoundException;
import com.ekabotdev.ecommerce.product.exception.*;
import io.jsonwebtoken.security.PublicJwkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleCategoryAlreadyExistsException
            (CategoryAlreadyExistsException exception) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return  ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException
            (MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("validation failed");

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                message,
                LocalDateTime.now()
        );
        return  ResponseEntity
                .badRequest()
                .body(error);

    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFoundException
            (CategoryNotFoundException exception) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFoundException
            (ProductNotFoundException exception) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);

    }

    @ExceptionHandler(InvalidProductFilterException.class)
    public ResponseEntity<ApiError> handleInvalidProductFilterException
            (InvalidProductFilterException exception) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(InvalidSortFieldException.class)
    public ResponseEntity<ApiError> handleInvalidSortFieldException
            (InvalidSortFieldException exception) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiError> handleInsufficientStockException
            (InsufficientStockException exception) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ApiError> handleOptimisticLockingFailure(
            ObjectOptimisticLockingFailureException exception
    ) {

        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "The product was modified by another request. Please try again.",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
}
