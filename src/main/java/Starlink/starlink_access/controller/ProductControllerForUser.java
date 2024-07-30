package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.util.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product Controller")
public class ProductControllerForUser {

    private final ProductSevice productSevice;

    @Operation(summary = "Get All Products", description = "Get Products For Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Get Products", content = @Content(schema = @Schema(implementation = Product.class))),
    })
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
                                    Pageable pageable, @ModelAttribute ProductDTO request){
        return Response.renderJSON(
                productSevice.getAll(pageable,request),
                "Success get all product",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get One Product", description = "Get One Product For Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Get Products", content = @Content(schema = @Schema(implementation = Product.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        return Response.renderJSON(
                productSevice.getOne(id),
                "Success get The product",
                HttpStatus.OK
        );
    }


}


