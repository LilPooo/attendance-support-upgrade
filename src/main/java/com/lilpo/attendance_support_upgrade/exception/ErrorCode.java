package com.lilpo.attendance_support_upgrade.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You don't have Permissions", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    CLASSROOM_NOT_EXISTED(1009, "Classroom not existed", HttpStatus.NOT_FOUND),
    USER_ALREADY_IN_CLASSROOM(1010, "User has already in this classroom", HttpStatus.BAD_REQUEST),
    USER_NOT_IN_CLASSROOM(1011, "User is not in this classroom", HttpStatus.BAD_REQUEST),
    USER_NOT_IN_ANY_CLASSROOM(1012, "User is not in any classroom", HttpStatus.BAD_REQUEST),
    INVALID_ROLL_CALL_TIME(1013, "Invalid roll call time: start time must be before end time and cannot be null", HttpStatus.BAD_REQUEST),
    INVALID_DEVICE_ID(1014, "Invalid Device Id", HttpStatus.BAD_REQUEST),
    INVALID_ROLL_CALL_CHECK_IN(1015, "It is not the right time for check in", HttpStatus.BAD_REQUEST),
    USER_TOO_FAR_FROM_CLASSROOM(1016, "You are too far from the classroom", HttpStatus.BAD_REQUEST),
    CHECK_IN_RECORD_NOT_FOUND(1017, "Check in record not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_HAVE_CONVERSATION(1018, "2 users already have conversation", HttpStatus.BAD_REQUEST),
    CONVERSATION_NOT_FOUND(1019, "Conversation does not exist", HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
