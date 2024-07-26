package Starlink.starlink_access.controller;

import Starlink.starlink_access.service.ProductSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/products")
@RequiredArgsConstructor
@Validated
public class ProductControllerForUser {

    private ProductSevice productSevice;


}


