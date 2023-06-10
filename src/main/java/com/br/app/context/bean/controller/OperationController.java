package com.br.app.context.bean.controller;

import com.br.app.context.bean.converter.OperationConverter;
import com.br.app.context.bean.domains.OperationRequest;
import com.br.app.context.bean.usecases.OperationUseCase;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Operation")
@RequestMapping("operation")
public class OperationController {

    private final OperationUseCase operationUseCase;
    private final OperationConverter converter;

    public OperationController(final OperationUseCase operationUseCase, final OperationConverter converter) {
        this.operationUseCase = operationUseCase;
        this.converter = converter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void execute(@Valid @RequestBody final OperationRequest request) {
        final var operation = converter.convert(request);
        operationUseCase.execute(operation);
    }
}
