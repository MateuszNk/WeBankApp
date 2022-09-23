package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.payment.PaymentDao;

public class PaymentService {
    private final PaymentDao paymentDao = new PaymentDao();
    public void payment(PaymentBasicInfo paymentBasicInfo) {
        paymentDao.sendPayment(
                paymentBasicInfo.getSenderAccount(),
                paymentBasicInfo.getReceiverAccount(),
                paymentBasicInfo.getAmount()
        );
    }
}
