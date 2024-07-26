package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.util.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/products")
@RequiredArgsConstructor
@Validated
public class ProductControllerForUser {

    private final ProductSevice productSevice;
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
                                    Pageable pageable, @ModelAttribute ProductDTO request){
        return Response.renderJSON(
                productSevice.getAll(pageable,request),
                "Success get all category",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        return Response.renderJSON(
                productSevice.getOne(id),
                "Success get The category",
                HttpStatus.OK
        );
    }


}


