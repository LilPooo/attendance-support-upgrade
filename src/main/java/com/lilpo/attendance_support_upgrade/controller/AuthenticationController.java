package com.lilpo.attendance_support_upgrade.controller;

import com.lilpo.attendance_support_upgrade.dto.ApiResponse;
import com.lilpo.attendance_support_upgrade.dto.request.AuthenticationRequest;
import com.lilpo.attendance_support_upgrade.dto.request.AuthenticationWithRoleRequest;
import com.lilpo.attendance_support_upgrade.dto.request.IntrospectRequest;
import com.lilpo.attendance_support_upgrade.dto.response.AuthenticationResponse;
import com.lilpo.attendance_support_upgrade.dto.response.AuthenticationWithRoleResponse;
import com.lilpo.attendance_support_upgrade.dto.response.IntrospectResponse;
import com.lilpo.attendance_support_upgrade.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/token-with-role")
    ApiResponse<AuthenticationWithRoleResponse> authenticateWithRole(@RequestBody AuthenticationWithRoleRequest request) {
        var result = authenticationService.authenticateWithRole(request);
        return ApiResponse.<AuthenticationWithRoleResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }


}
