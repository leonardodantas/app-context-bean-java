package com.br.app.context.bean.domains;

import com.br.app.context.bean.usecases.impl.CreditUseCase;
import com.br.app.context.bean.usecases.impl.DebitUseCase;
import com.br.app.context.bean.usecases.ITransactional;
import com.br.app.context.bean.usecases.impl.MoneyUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OperationType {

    CREDIT(CreditUseCase.class), DEBIT(DebitUseCase.class), MONEY(MoneyUseCase.class);

    private final Class<? extends ITransactional> useCase;

    public Class<? extends ITransactional> getUseCase(){
        return useCase;
    }

}
