package com.br.app.context.bean.domains;

import com.br.app.context.bean.usecases.ITransactional;

import java.math.BigDecimal;

public record Operation(
        BigDecimal amount,
        OperationType type
) {
    public static Operation of(final BigDecimal amount, final OperationType operationType) {
        return new Operation(amount, operationType);
    }

    public Class<? extends ITransactional> getUseCase(){
        return type.getUseCase();
    }
}
