package com.cropdeal.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cropdeal.entites.shop;
import com.cropdeal.exceptions.noshopfoundexception;
import com.cropdeal.repositry.shoprepositry;

@SpringBootTest
class ShopServiceImplTest {

    @Mock
    private shoprepositry shopRepository;

    @InjectMocks
    private shopServiceImpl shopService;

    @Test
    public void testNewShop_WithUniqueShopId_ShouldReturnSavedShop() throws noshopfoundexception {
        int shopId = 1;
        shop userShop = new shop();
        userShop.setShopid(shopId);
        userShop.setShopName("Test Shop");

        when(shopRepository.findById(eq(shopId))).thenReturn(Optional.empty());
        when(shopRepository.save(any(shop.class))).thenReturn(userShop);

        shop result = shopService.newshop(userShop, shopId);

        assertEquals(userShop, result);

        verify(shopRepository, times(1)).findById(eq(shopId));
        verify(shopRepository, times(1)).save(eq(userShop));
    }

    @Test
    public void testNewShop_WithDuplicateShopId_ShouldThrowNoShopFoundException() {
        int shopId = 1;
        shop userShop = new shop();
        userShop.setShopid(shopId);
        userShop.setShopName("Test Shop");

        when(shopRepository.findById(eq(shopId))).thenReturn(Optional.of(userShop));

        assertThrows(noshopfoundexception.class, () -> shopService.newshop(userShop, shopId));

        verify(shopRepository, times(1)).findById(eq(shopId));
        verify(shopRepository, times(0)).save(any(shop.class));
    }

    @Test
    public void testUpdateShop_WithExistingShopId_ShouldReturnUpdatedShop() throws noshopfoundexception {
        int shopId = 1;
        shop updatedShop = new shop();
        updatedShop.setShopid(shopId);
        updatedShop.setShopName("Updated Shop");

        when(shopRepository.findById(eq(shopId))).thenReturn(Optional.of(updatedShop));
        when(shopRepository.save(any(shop.class))).thenReturn(updatedShop);

        shop result = shopService.updateShop(updatedShop, shopId);

        assertEquals(updatedShop, result);

        verify(shopRepository, times(1)).findById(eq(shopId));
        verify(shopRepository, times(1)).save(eq(updatedShop));
    }

    @Test
    public void testUpdateShop_WithNonExistingShopId_ShouldThrowNoShopFoundException() {
        int shopId = 1;
        shop updatedShop = new shop();
        updatedShop.setShopid(shopId);
        updatedShop.setShopName("Updated Shop");

        when(shopRepository.findById(eq(shopId))).thenReturn(Optional.empty());

        assertThrows(noshopfoundexception.class, () -> shopService.updateShop(updatedShop, shopId));

        verify(shopRepository, times(1)).findById(eq(shopId));
        verify(shopRepository, times(0)).save(any(shop.class));
    }

    @Test
    public void testGetAllShops_WithExistingShops_ShouldReturnAllShops() {
        List<shop> shopList = new ArrayList<>();
        shopList.add(new shop(1, "Shop 1", "", "", ""));
        shopList.add(new shop(2, "Shop 2", "", "", ""));
        shopList.add(new shop(3, "Shop 3", "", "", ""));

        when(shopRepository.findAll()).thenReturn(shopList);

        List<shop> result = shopService.gettallshops();

        assertEquals(3, result.size());
        assertEquals(shopList, result);

        verify(shopRepository, times(1)).findAll();
    }

    @Test
    public void testGetShopById_WithExistingShopId_ShouldReturnShop() throws noshopfoundexception {
        int shopId = 1;
        shop shop = new shop(shopId, "Test Shop", "", "", "");

        when(shopRepository.findById(eq(shopId))).thenReturn(Optional.of(shop));

        shop result = shopService.getShopById(shopId);

        assertEquals(shop, result);

        verify(shopRepository, times(1)).findById(eq(shopId));
    }

    @Test
    public void testGetShopById_WithNonExistingShopId_ShouldThrowNoShopFoundException() {
        int shopId = 1;

        when(shopRepository.findById(eq(shopId))).thenReturn(Optional.empty());

        assertThrows(noshopfoundexception.class, () -> shopService.getShopById(shopId));

        verify(shopRepository, times(1)).findById(eq(shopId));
    }

    @Test
    public void testDoesFarmerHaveShop_WithExistingShopId_ShouldReturnTrue() {
        int farmerId = 1;

        when(shopRepository.findById(eq(farmerId))).thenReturn(Optional.of(new shop()));

        boolean result = shopService.doesFarmerHaveShop(farmerId);

        assertTrue(result);

        verify(shopRepository, times(1)).findById(eq(farmerId));
    }

    @Test
    public void testDoesFarmerHaveShop_WithNonExistingShopId_ShouldReturnFalse() {
        int farmerId = 1;

        when(shopRepository.findById(eq(farmerId))).thenReturn(Optional.empty());

        boolean result = shopService.doesFarmerHaveShop(farmerId);

        assertFalse(result);

        verify(shopRepository, times(1)).findById(eq(farmerId));
    }
}
