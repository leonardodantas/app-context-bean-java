package com.br.app.context.bean.domains;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record OperationRequest (
        @Positive
        BigDecimal amount,
        @NotBlank
        String type
) {
}
