//package com.cropdeal.service;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.mockito.Mockito.*;
//
//import org.json.JSONObject;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.cropdeal.models.transactionDetails;
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
//
//@SpringBootTest
//public class PaymentServiceImplTest {
//
//    private static final String SECRET_ID = "rzp_test_snvXEPmPJNCZ3U";
//    private static final String SECRET_KEY = "xSexxVJlCuQxC4L0DcvT9LAX";
//    private static final String CURRENCY = "INR";
//
//    private paymentService paymentService;
//
//    @Mock
//    private RazorpayClient razorpayClient;
//
//    @BeforeAll
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        paymentService = new paymentServiceimpl();
//    }
//
//    @Test
//    public void testGetPayment_ValidAmount_ReturnsTransactionDetails() throws RazorpayException {
//        double amount = 1000.0;
//
//        JSONObject jsObject = new JSONObject();
//        jsObject.put("amount", amount);
//        jsObject.put("currency", CURRENCY);
//
//        Order order = new Order();
//        order.put("id", "order123");
//        order.put("currency", CURRENCY);
//        order.put("amount", amount);
//
//        when(razorpayClient.orders()).thenReturn(order);
//
//        transactionDetails transaction = paymentService.getPayment(amount);
//
//        assertNotNull(transaction);
//        assertEquals("order123", transaction.getOrderId());
//        assertEquals(CURRENCY, transaction.getCurrency());
//        assertEquals(Double.toString(amount), transaction.getAmount());
//        assertEquals(SECRET_KEY, transaction.getSecretKey());
//    }
//
//    @Test(expected = RazorpayException.class)
//    public void testGetPayment_ZeroAmount_ThrowsRazorpayException() throws RazorpayException {
//        double amount = 0.0;
//
//        transactionDetails transaction = paymentService.getPayment(amount);
//        // The test is expected to throw a RazorpayException due to the zero amount
//    }
//
//    @Test(expected = RazorpayException.class)
//    public void testGetPayment_NegativeAmount_ThrowsRazorpayException() throws RazorpayException {
//        double amount = -500.0;
//
//        transactionDetails transaction = paymentService.getPayment(amount);
//        // The test is expected to throw a RazorpayException due to the negative amount
//    }
//
//    // ... other test cases ...
//
//}
