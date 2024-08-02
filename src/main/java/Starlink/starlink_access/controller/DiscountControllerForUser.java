package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.DiscountDTO;
import Starlink.starlink_access.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/discounts")
@RequiredArgsConstructor
@Validated
@Tag(name = "Discount Controller")
public class DiscountControllerForUser {
    private final DiscountService discountService;

    @Operation(summary = "Get All Discount Data", description = "Get All and return Http Status Code 200")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful get discounts data", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
    })
    @GetMapping
    public ResponseEntity<Page<DiscountDTO>> getAll(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam (required = false) Integer percentage){
        Page<DiscountDTO> discounts = discountService.getAll(pageable, name, percentage);
        return ResponseEntity.ok(discounts);
    }

    @Operation(summary = "Get Discount by ID's", description = "Get One Discount data by Discount ID's")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful get discounts data", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<DiscountDTO> getById(@PathVariable Long id){
        DiscountDTO discount = discountService.getById(id);
        return ResponseEntity.ok(discount);
    }

}
