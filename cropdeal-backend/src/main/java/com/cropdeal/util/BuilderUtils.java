package com.cropdeal.util;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import com.cropdeal.models.cartDto;
import com.cropdeal.models.productdto;
import org.springframework.stereotype.Service;

@Service
public class BuilderUtils {


    public cartDto carttoDtoMapping(cart cart){
        return cartDto.builder().quantity(cart.getQuantity()).marchentId(cart.getMarchentId()).status(cart.getStatus()).addedDateTime(cart.getAddedDateTime()).product(producttoDtoMapping(cart.getProduct())).build();
    }



    public productdto producttoDtoMapping(product cart){
        return productdto.builder().farmerId(cart.getFarmerId()).productName(cart.getProductName()).status(cart.getStatus()).availableQuantity(cart.getAvailableQuantity()).initialQuantity(cart.getInitialQuantity()).date(cart.getDate()).price(cart.getPrice()).productImages(cart.getProductImages()).build();
    }
}
