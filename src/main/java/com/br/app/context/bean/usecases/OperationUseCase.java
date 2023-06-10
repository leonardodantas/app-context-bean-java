package com.br.app.context.bean.usecases;

import com.br.app.context.bean.domains.Operation;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class OperationUseCase {

    private final ApplicationContext applicationContext;

    public OperationUseCase(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void execute(final Operation operation){
        final var useCase = applicationContext.getBean(operation.getUseCase());
        useCase.execute(operation);
    }
}
