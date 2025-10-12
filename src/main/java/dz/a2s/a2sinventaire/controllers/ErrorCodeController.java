package dz.a2s.a2sinventaire.controllers;

import dz.a2s.a2sinventaire.api.ErrorCodeApi;
import dz.a2s.a2sinventaire.dto.errorCodes.ErrorCodeResponse;
import dz.a2s.a2sinventaire.dto.response.SuccessResponseDto;
import dz.a2s.a2sinventaire.exceptions.AppErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/error-codes")
public class ErrorCodeController implements ErrorCodeApi {
    @Override
    public ResponseEntity<SuccessResponseDto<List<ErrorCodeResponse>>> getErrorCodes() {
        log.info("| Entry | ErrorCodeController.getErrorCodes");

        List<ErrorCodeResponse> list = new ArrayList<>();
        for (AppErrorCodes errorCode : AppErrorCodes.values()) {
            list.add(new ErrorCodeResponse(errorCode.getCode(), errorCode.getMessage()));
        }
        var response = new SuccessResponseDto<>(
                200,
                "Codes d'erreur récupérés avec succès",
                list
        );

        log.info("Returning {}", response);

        return ResponseEntity.ok(response);
    }
}
