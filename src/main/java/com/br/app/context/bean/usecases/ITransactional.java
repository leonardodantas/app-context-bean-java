package com.br.app.context.bean.usecases;

import com.br.app.context.bean.domains.Operation;

public interface ITransactional {

    void execute(final Operation operation);
}
