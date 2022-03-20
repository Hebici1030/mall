package org.imooc.mall.emnu;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public enum PaymentType {
    PAYMENT_TYPE(1);

    PaymentType(Integer code) {
        this.code = code;
    }

    Integer code;
}
