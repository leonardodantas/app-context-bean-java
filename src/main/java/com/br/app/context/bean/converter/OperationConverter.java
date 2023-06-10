package com.br.app.context.bean.converter;

import com.br.app.context.bean.domains.Operation;
import com.br.app.context.bean.domains.OperationRequest;
import com.br.app.context.bean.domains.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OperationConverter implements Converter<OperationRequest, Operation> {

    @Override
    public Operation convert(final OperationRequest request) {
        final var operationType = getOperationType(request);
        return Operation.of(request.amount(), operationType);
    }

    private OperationType getOperationType(final OperationRequest request) {
        try{
            return OperationType.valueOf(request.type());
        } catch (final Exception exception) {
            log.error("Error convert string in enum: {}", exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }
}
