package com.omf.service.impl;

import com.omf.model.CreditCardInfo;
import com.omf.model.Order;
import com.omf.model.Payment;
import com.omf.repository.OrderRepository;
import com.omf.repository.PaymentRepository;
import com.omf.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(RestTemplate restTemplate, OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

   // @HystrixCommand(fallbackMethod = "processPaymentFallback")
    @Override
    public void processPayment(Payment payment) {
        System.out.println("ProcessPayment called()");
        payment = paymentRepository.save(payment);
        String orderCompleteUpdater = "http://order-complete-updater";
        String orderId = payment.getOrderId();
        if (orderId == null) {
            sendErrorMessage("Missing orderId in payment");
            return;
        }
        Order order = orderRepository.findByOrderId(orderId);
        if (order != null && validateCreditCardInfo(payment.getCreditCardInfo())) {
            if (order.getTotalPrice() != payment.getAmount()) {
                String errorMessage = "Payment amount: " + payment.getAmount() + " doesn't match with order price: " +
                        order.getTotalPrice() + ", orderId = " + orderId;
                sendErrorMessage(errorMessage);
                return;
            } else {
                order.setPaymentId(payment.getId());
                long deliveryTime = getDeliveryTime();
                order.setDeliveryTime(deliveryTime);
                orderRepository.save(order);
              //  restTemplate.postForLocation("http://localhost:8005" + "/api/orders", order);
            }
        } else {
            String errorMessage = order == null ? "Failed to retrieve order for orderId: " + orderId
                    : "Invalid credit card information for orderId: " + orderId;
            sendErrorMessage(errorMessage);
        }
    }

    public void processPaymentFallback(Payment payment) {
        System.out.println("Fallback method is called.");
//        log.error("Hystrix Fallback Method. Unable to process payment for orderId: " + payment == null ? ""
//                : (payment.getOrderId() == null ? "" : payment.getOrderId()));
    }

    private boolean validateCreditCardInfo(CreditCardInfo creditCardInfo) {
        return true;
    }

    private long getDeliveryTime() {
        // randomly generate delivery estimation period, range: 5 ~ 1 hour
        Random random = new Random();
        int randomPeriod = 5 + random.nextInt(60 - 5 + 1);
        return System.currentTimeMillis() + randomPeriod * 60 * 1000;
    }

    private void sendErrorMessage(String errorMessage) {
        log.warn(errorMessage);
        String orderCompleteUpdater = "http://order-complete-updater";
        restTemplate.postForLocation("http://localhost:8005" + "/api/errors", errorMessage);
    }
}
