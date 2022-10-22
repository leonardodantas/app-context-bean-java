package com.br.app.context.bean.usecases.impl;

import com.br.app.context.bean.domains.Operation;
import com.br.app.context.bean.usecases.ITransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DebitUseCase implements ITransactional {

    @Override
    public void execute(final Operation operation) {
        log.info("Execute: DebitUseCase");
    }
}
