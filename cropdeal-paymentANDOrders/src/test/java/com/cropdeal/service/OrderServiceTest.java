package com.cropdeal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cropdeal.entites.*;
import com.cropdeal.exception.*;
import com.cropdeal.models.*;
import com.cropdeal.rabbitmq.rabbitmqEmitter;
import com.cropdeal.repositry.*;
import com.razorpay.RazorpayException;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private orderService orderService;

    @MockBean
    private billRepostry billRepository;

    @MockBean
    private coponRepositry couponRepository;

    @MockBean
    private orderRepostry orderRepository;

    @MockBean
    private inventryServiceProxy inventoryServiceProxy;

    @MockBean
    private paymentService paymentService;
    
    @MockBean
    private rabbitmqEmitter rabbitmqEmitter;

    @MockBean
    private transactionRepostry transactionRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder() throws noProductFoundException, invalidQuantityException, RazorpayException {
        // Mock product
    	product product = new product();
        product.setProductId("1");
        product.setAvailableQuantity(10);
        product.setPrice(100);

        when(inventoryServiceProxy.getProductById(eq("1"))).thenReturn(product);

        // Mock coupon
        Optional<copons> couponOptional = Optional.of(new copons());
        when(couponRepository.findByCoupon(anyString())).thenReturn(couponOptional);

        // Mock payment service
        transactionDetails transactionDetails = new transactionDetails();
        transactionDetails.setOrderId("order123");
        transactionDetails.setAmount("100");
        when(paymentService.getPayment(anyDouble())).thenReturn(transactionDetails);

        // Mock order repository
        orders order = new orders();
        when(orderRepository.save(any(orders.class))).thenReturn(order);

        // Perform the test
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("productId", "1");
        inputMap.put("quantity", "5");
        inputMap.put("coupon", "coupon123");

        transactionDetails result = orderService.placeOrder(1, inputMap);

        // Assertions
        assertEquals("order123", result.getOrderId());
        verify(orderRepository, times(1)).save(any(orders.class));
    }

    @Test
    public void testPlaceOrder_InvalidQuantity() throws noProductFoundException, invalidQuantityException, RazorpayException {
    	product product = new product();
        product.setProductId("1");
        product.setAvailableQuantity(10);
        product.setPrice(100);

        when(inventoryServiceProxy.getProductById(eq("1"))).thenReturn(product);

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("productId", "1");
        inputMap.put("quantity", "15");

        assertThrows(invalidQuantityException.class, () -> orderService.placeOrder(1, inputMap));
    }

    @Test
    public void testPaymentConfirmation() throws noProductFoundException {
    	
        transactions transaction = new transactions();
        transaction.setTransactionId("1");
        transaction.setStatus("pending");

        when(transactionRepository.findById(eq("1"))).thenReturn(Optional.of(transaction));

        // Mock order
        orders order = new orders();
        order.setOrderId(1);
        order.setProductIdList(List.of("1","2"));
        order.setQuantity(List.of(1,2));
        when(orderRepository.findByTransactionsTransactionId(anyString())).thenReturn(Optional.of(order));
        when(rabbitmqEmitter.emmitmsg(any())).thenReturn(" ");
        // Perform the test
        Map<String, String> paymentDetails = new HashMap<>();
        paymentDetails.put("orderid", "1");

        orders result = orderService.paymentConformation(paymentDetails);

        // Assertions
        assertEquals(null, result);
    }
    
    
    
    
    
}
