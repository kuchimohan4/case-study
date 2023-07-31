package com.cropdeal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import com.cropdeal.entites.reviews;
import com.cropdeal.exceptions.noProductFoundException;
import com.cropdeal.rabbitmq.rabbitmqEmitter;
import com.cropdeal.repositry.cartRepositry;
import com.cropdeal.repositry.productRepositry;
import com.cropdeal.repositry.reviewRepostry;

@SpringBootTest
public class InventoryServiceImplTest {

	@Mock
	private productRepositry productRepository;

	@Mock
	private cartRepositry cartRepository;

	@Mock
	private reviewRepostry reviewRepository;

	@Mock
	private rabbitmqEmitter rabbitmqEmitter;

	@InjectMocks
	private inventryserviceimpl inventoryService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddProduct() throws noProductFoundException {
		product product = new product();
		product.setFarmerId(1);
		product.setProductName("Test Product");
		product.setAvailableQuantity(10);
		product.setInitialQuantity(10);
		product.setDate(LocalDateTime.now());
		product.setPrice(100.0);
		product.setProductDetails("Test Product Details");
		product.setReviews(new ArrayList<>());

		when(productRepository.findByFarmerIdAndProductName(anyInt(), anyString())).thenReturn(new ArrayList<>());
		when(productRepository.save(any(product.class))).thenReturn(product);

		product result = inventoryService.addproduct(1, product);

		assertEquals(product, result);
		verify(productRepository, times(1)).findByFarmerIdAndProductName(anyInt(), anyString());
		verify(productRepository, times(1)).save(any(product.class));
	}

	@Test
	public void testUpdateProduct() throws noProductFoundException {
		product existingProduct = new product();
		existingProduct.setProductId("123");
		existingProduct.setFarmerId(1);
		existingProduct.setProductName("Existing Product");
		existingProduct.setAvailableQuantity(10);
		existingProduct.setInitialQuantity(10);
		existingProduct.setDate(LocalDateTime.now());
		existingProduct.setPrice(100.0);
		existingProduct.setProductDetails("Existing Product Details");
		existingProduct.setStatus("Active");
		existingProduct.setReviews(new ArrayList<>());

		product updatedProduct = new product();
		updatedProduct.setProductId("123");
		updatedProduct.setFarmerId(1);
		updatedProduct.setProductName("Updated Product");
		updatedProduct.setAvailableQuantity(20);
		updatedProduct.setInitialQuantity(10);
		updatedProduct.setDate(LocalDateTime.now());
		updatedProduct.setPrice(200.0);
		updatedProduct.setProductDetails("Updated Product Details");
		updatedProduct.setStatus("Active");
		updatedProduct.setReviews(new ArrayList<>());

		when(productRepository.findByFarmerIdAndProductId(anyInt(), anyString()))
				.thenReturn(List.of(existingProduct));
		when(productRepository.save(any(product.class))).thenReturn(updatedProduct);

		Object result = inventoryService.updateproduct(updatedProduct, 1);

		assertEquals(updatedProduct, result);
		verify(productRepository, times(1)).findByFarmerIdAndProductId(anyInt(), anyString());
		verify(productRepository, times(1)).save(any(product.class));
	}

	@Test
	public void testRemoveProduct() throws noProductFoundException {
	    String productId = "123";
	    int farmerId = 1;

	    product existingProduct = new product();
		existingProduct.setProductId("123");
		existingProduct.setFarmerId(1);
		existingProduct.setProductName("Existing Product");
		existingProduct.setAvailableQuantity(10);
		existingProduct.setInitialQuantity(10);
		existingProduct.setDate(LocalDateTime.now());
		existingProduct.setPrice(100.0);
		existingProduct.setProductDetails("Existing Product Details");
		existingProduct.setStatus("Active");
		existingProduct.setReviews(new ArrayList<>());
		List<product> productList = new ArrayList<>();
	    productList.add(existingProduct);

	    when(productRepository.findByFarmerIdAndProductId(eq(farmerId), eq(productId))).thenReturn(productList);

	    inventoryService.deleteproduct(productId, farmerId);

	    verify(productRepository, times(1)).findByFarmerIdAndProductId(eq(farmerId), eq(productId));
	    verify(productRepository, times(1)).deleteByProductId(eq(productId));
	}


	
	
	@Test
	public void testAddReviewToProduct() throws noProductFoundException {
		product existingProduct = new product();
		existingProduct.setProductId("123");
		existingProduct.setFarmerId(1);
		existingProduct.setProductName("Existing Product");
		existingProduct.setAvailableQuantity(10);
		existingProduct.setInitialQuantity(10);
		existingProduct.setDate(LocalDateTime.now());
		existingProduct.setPrice(100.0);
		existingProduct.setProductDetails("Existing Product Details");
		existingProduct.setStatus("Active");
		existingProduct.setReviews(new ArrayList<>());

		
		reviews review = new reviews();
		review.setReviewId(456);
		review.setProductId("123");
		review.setDealearId(5);
		review.setRating(4);
		review.setDescription("Great product!");
		List<product> prodlistList=new ArrayList<>();
		prodlistList.add(existingProduct);
		when(productRepository.findByProductId(anyString())).thenReturn(prodlistList);
		when(reviewRepository.save(any(reviews.class))).thenReturn(review);
		when(reviewRepository.findReviewWithMaxReviewId()).thenReturn(review);
		reviews result = inventoryService.addreview(existingProduct.getProductId(),review,review.getDealearId());
		assertEquals(review, result);
		verify(productRepository, times(1)).findByProductId(anyString());
		verify(reviewRepository, times(1)).save(any(reviews.class));
	}

	@Test
	public void testGetCartByUserId() {
		int userId = 1;
		List<cart> cartItems = new ArrayList<>();
		cartItems.add(new cart());
		cartItems.add(new cart());

		when(cartRepository.findByMarchentId(anyInt())).thenReturn(cartItems);

		List<cart> result = inventoryService.getCartitemsCartsBymarchent(userId);

		assertEquals(cartItems, result);
		verify(cartRepository, times(1)).findByMarchentId(userId);
	}

	@Test
	public void testAddToCart() throws noProductFoundException {
	    int merchantId = 1;
	    String productId = "123";
	    int quantity = 5;

	    product existingProduct = new product();
	    existingProduct.setProductId("123");
	    existingProduct.setFarmerId(1);
	    existingProduct.setProductName("Existing Product");
	    existingProduct.setAvailableQuantity(10);
	    existingProduct.setInitialQuantity(10);
	    existingProduct.setDate(LocalDateTime.now());
	    existingProduct.setPrice(100.0);
	    existingProduct.setProductDetails("Existing Product Details");
	    existingProduct.setStatus("Active");
	    existingProduct.setReviews(new ArrayList<>());

	    Optional<cart> cartItem = Optional.empty();

	    when(inventoryService.getProductById(eq(productId))).thenReturn(existingProduct);
	    when(cartRepository.findByProductProductIdAndMarchentId(eq(productId), eq(merchantId))).thenReturn(cartItem);
	    when(productRepository.findByProductId(eq("123"))).thenReturn(List.of(existingProduct));
	    when(cartRepository.save(any(cart.class))).thenReturn(new cart(quantity, merchantId, "incart", LocalDateTime.now(), existingProduct));

	    cart result = inventoryService.addtocart(merchantId, Map.of("productId", productId, "quantity", String.valueOf(quantity)));

	    assertEquals(quantity, result.getQuantity());
	    assertEquals(merchantId, result.getMarchentId());
	    assertEquals(existingProduct, result.getProduct());

	    verify(inventoryService, times(1)).getProductById(eq(productId));
	    verify(cartRepository, times(1)).findByProductProductIdAndMarchentId(productId, quantity);
	    verify(cartRepository, times(1)).save(any(cart.class));
	}

	
	
	@Test
	public void testReduceProductsFromCart() throws noProductFoundException {
	    int merchantId = 1;
	    String productId = "123";
	    int quantity = 2;

	    product existingProduct = new product();
		existingProduct.setProductId("123");
		existingProduct.setFarmerId(1);
		existingProduct.setProductName("Existing Product");
		existingProduct.setAvailableQuantity(10);
		existingProduct.setInitialQuantity(10);
		existingProduct.setDate(LocalDateTime.now());
		existingProduct.setPrice(100.0);
		existingProduct.setProductDetails("Existing Product Details");
		existingProduct.setStatus("Active");
		existingProduct.setReviews(new ArrayList<>());
		Optional<cart> cartItem = Optional.of(new cart(5, merchantId, "incart", LocalDateTime.now(), existingProduct));

	    when(inventoryService.getProductById(eq(productId))).thenReturn(existingProduct);
	    when(cartRepository.findByMarchentIdAndProductProductId(eq(merchantId),eq(productId))).thenReturn(cartItem);
	    when(cartRepository.save(any(cart.class))).thenReturn(new cart(3, merchantId, "incart", LocalDateTime.now(), existingProduct));
		   
	    cart result = inventoryService.reduceProductsFromcart(merchantId, Map.of("productId", productId, "quantity", String.valueOf(quantity)));

	    assertEquals(3, result.getQuantity());
	    assertEquals(merchantId, result.getMarchentId());
	    assertEquals(existingProduct, result.getProduct());

	    verify(inventoryService, times(1)).getProductById(eq(productId));
	    verify(cartRepository, times(1)).findByMarchentIdAndProductProductId(eq(merchantId),eq(productId));
	    verify(cartRepository, times(1)).save(any(cart.class));
	}
	
	@Test
	public void testGetCartitemsCartsByMerchant() {
	    int merchantId = 1;

	    List<cart> cartItems = new ArrayList<>();
	    cartItems.add(new cart(2, merchantId, "incart", LocalDateTime.now(), new product()));
	    cartItems.add(new cart(3, merchantId, "incart", LocalDateTime.now(), new product()));

	    when(cartRepository.findByMarchentId(eq(merchantId))).thenReturn(cartItems);

	    List<cart> result = inventoryService.getCartitemsCartsBymarchent(merchantId);

	    assertEquals(2, result.size());
	    assertEquals(merchantId, result.get(0).getMarchentId());
	    assertEquals(merchantId, result.get(1).getMarchentId());

	    verify(cartRepository, times(1)).findByMarchentId(merchantId);
	}
	
	
	@Test
	public void testGetAllProducts() {
	    List<product> products = new ArrayList<>();
	    products.add(new product());
	    products.add(new product());

	    when(productRepository.findAll()).thenReturn(products);

	    List<product> result = inventoryService.getallProducts();

	    assertEquals(2, result.size());

	    verify(productRepository, times(1)).findAll();
	}

	@Test
	public void testGetAllProductsByFarmerId() {
	    int farmerId = 1;

	    List<product> products = new ArrayList<>();
	    products.add(new product());
	    products.add(new product());

	    when(productRepository.findByFarmerId(eq(farmerId))).thenReturn(products);

	    List<product> result = inventoryService.getallProductsByFarmerId(farmerId);

	    assertEquals(2, result.size());

	    verify(productRepository, times(1)).findByFarmerId(eq(farmerId));
	}

	@Test
	public void testRemoveFromCart() throws noProductFoundException {
	    int merchantId = 1;
	    String productId = "123";

	    Optional<cart> cartItem = Optional.of(new cart(2, merchantId, "incart", LocalDateTime.now(), new product()));

	    when(cartRepository.findByProductProductIdAndMarchentId(eq(productId), eq(merchantId))).thenReturn(cartItem);

	    inventoryService.removefromCart(productId, merchantId);

	    verify(cartRepository, times(1)).findByProductProductIdAndMarchentId(eq(productId), eq(merchantId));
	    verify(cartRepository, times(1)).deleteByProductProductIdAndMarchentId(productId, merchantId);
	}
	
	@Test
	public void testRemoveAllFromCart() {
	    int merchantId = 1;

	    inventoryService.removeAllFromCart(merchantId);

	    verify(cartRepository, times(1)).deleteByMarchentId(merchantId);
	}


	@Test
	public void testGetExistingProductById() throws noProductFoundException {
	    String productId = "123";
	    product existingProduct = new product();
	    existingProduct.setProductId(productId);

	    when(productRepository.findByProductId(eq(productId))).thenReturn(List.of(existingProduct));

	    product result = inventoryService.getProductById(productId);

	    assertEquals(existingProduct, result);

	    verify(productRepository, times(1)).findByProductId(eq(productId));
	}

	@Test
	public void testGetNonExistingProductById() {
	    String productId = "456";

	    when(productRepository.findByProductId(eq(productId))).thenReturn(Collections.emptyList());

	    assertThrows(noProductFoundException.class, () -> {
	        inventoryService.getProductById(productId);
	    });

	    verify(productRepository, times(1)).findByProductId(eq(productId));
	}	
	
	@Test
	public void testGenerateReviewId() {
	    reviews maxReview = new reviews();
	    maxReview.setReviewId(100); // Assuming max review ID is 100

	    when(reviewRepository.findReviewWithMaxReviewId()).thenReturn(maxReview);

	    int result = inventoryService.gentratereviewid();

	    assertEquals(101, result);

	    verify(reviewRepository, times(1)).findReviewWithMaxReviewId();
	}
	
	@Test
	public void testOrderPlaced() throws noProductFoundException {
	    String productId = "123";
	    int quantity = 5;

	    product product = new product();
	    product.setProductId(productId);
	    product.setAvailableQuantity(10); // Assuming initial available quantity is 10

	    Map<String, String> orderDetails = new HashMap();
	    orderDetails.put("productId", productId);
	    orderDetails.put("quantity", String.valueOf(quantity));

	    when(productRepository.findByProductId(eq(productId))).thenReturn(List.of(product));

	    String result = inventoryService.orderPlaced(orderDetails);

	    assertEquals("success", result);
	    assertEquals(5, product.getAvailableQuantity());

	    verify(productRepository, times(1)).findByProductId(eq(productId));
	    verify(productRepository, times(1)).save(eq(product));
	}
	
	
	@Test
	public void testCartOrderPlaced() throws noProductFoundException {
	    int merchantId = 1;

	    String productId1 = "123";
	    int quantity1 = 3;

	    String productId2 = "456";
	    int quantity2 = 2;

	    product product1 = new product();
	    product1.setProductId(productId1);
	    product1.setAvailableQuantity(5); // Assuming initial available quantity is 5

	    product product2 = new product();
	    product2.setProductId(productId2);
	    product2.setAvailableQuantity(10); // Assuming initial available quantity is 10

	    Map<String, String> orderDetails = new HashMap<>();
	    orderDetails.put(productId1, String.valueOf(quantity1));
	    orderDetails.put(productId2, String.valueOf(quantity2));

	    when(productRepository.findByProductId(eq(productId1))).thenReturn(List.of(product1));
	    when(productRepository.findByProductId(eq(productId2))).thenReturn(List.of(product2));

	    String result = inventoryService.cartOrderplaced(orderDetails, merchantId);

	    assertEquals("success", result);
	    assertEquals(2, product1.getAvailableQuantity());
	    assertEquals(8, product2.getAvailableQuantity());

	    verify(productRepository, times(1)).findByProductId(eq(productId1));
	    verify(productRepository, times(1)).findByProductId(eq(productId2));
	    verify(productRepository, times(1)).save(eq(product1));
	    verify(productRepository, times(1)).save(eq(product2));
	}


	@Test
	public void testOrderCanceled() throws noProductFoundException {
	    String productId1 = "123";
	    int quantity1 = 3;

	    String productId2 = "456";
	    int quantity2 = 2;

	    product product1 = new product();
	    product1.setProductId(productId1);
	    product1.setAvailableQuantity(2); // Assuming initial available quantity is 2

	    product product2 = new product();
	    product2.setProductId(productId2);
	    product2.setAvailableQuantity(8); // Assuming initial available quantity is 8

	    Map<String, String> orderDetails = new HashMap<>();
	    orderDetails.put(productId1, String.valueOf(quantity1));
	    orderDetails.put(productId2, String.valueOf(quantity2));

	    when(productRepository.findByProductId(eq(productId1))).thenReturn(List.of(product1));
	    when(productRepository.findByProductId(eq(productId2))).thenReturn(List.of(product2));

	    String result = inventoryService.orderCanceled(orderDetails);

	    assertEquals("success", result);
	    assertEquals(5, product1.getAvailableQuantity());
	    assertEquals(10, product2.getAvailableQuantity());

	    verify(productRepository, times(1)).findByProductId(eq(productId1));
	    verify(productRepository, times(1)).findByProductId(eq(productId2));
	    verify(productRepository, times(1)).save(eq(product1));
	    verify(productRepository, times(1)).save(eq(product2));
	}

	@Test
	public void testGetAverageReviewOfShop() {
	    int merchantId = 1;

	    double averageRating = 4.5; // Assuming average rating is 4.5

	    when(reviewRepository.getAverageRatingByFarmerId(eq(merchantId))).thenReturn(averageRating);

	    double result = inventoryService.getavgreviewofshop(merchantId);

	    assertEquals(4.5, result, 0.001);

	    verify(reviewRepository, times(1)).getAverageRatingByFarmerId(eq(merchantId));
	}

	
	@Test
	public void testGetAverageRatingOfProduct() {
	    String productId = "123";

	    double averageRating = 3.8; // Assuming average rating is 3.8

	    when(reviewRepository.getAverageRatingByProductId(eq(productId))).thenReturn(averageRating);

	    double result = inventoryService.getavgRatingOfProduct(productId);

	    assertEquals(3.8, result, 0.001);

	    verify(reviewRepository, times(1)).getAverageRatingByProductId(eq(productId));
	}
	
	@Test
	public void testIsDelearAddedReviewForProduct() {
	    int dealerId = 1;
	    String productId = "123";

	    when(reviewRepository.findByProductIdAndDealearId(eq(productId), eq(dealerId))).thenReturn(Optional.of(new reviews()));

	    boolean result = inventoryService.isDelearAddedreviewForProduct(dealerId, productId);

	    assertTrue(result);

	    verify(reviewRepository, times(1)).findByProductIdAndDealearId(eq(productId), eq(dealerId));
	}
	
	@Test
	public void testGetReviewsByProductId() {
	    String productId = "123";

	    List<reviews> expectedReviews = new ArrayList<>(); // Assuming you have a list of reviews for the product

	    when(reviewRepository.findByProductId(eq(productId))).thenReturn(expectedReviews);

	    List<reviews> result = inventoryService.getReviewsByProductId(productId);

	    assertEquals(expectedReviews, result);

	    verify(reviewRepository, times(1)).findByProductId(eq(productId));
	}



}
