package Starlink.starlink_access.controller;


import Starlink.starlink_access.DTO.AuthResponse;
import Starlink.starlink_access.DTO.DiscountDTO;
import Starlink.starlink_access.model.Discount;
import Starlink.starlink_access.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/admin/discounts")
@RequiredArgsConstructor
@Tag(name = "Discount API Docs", description = "Discount API docs")
public class DiscountController {
    private final DiscountService discountService;

    @Operation(summary = "Create Discount", description = "Create discount and return Http.created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Create Discount", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DiscountDTO request){
        DiscountDTO createdDiscount = discountService.create(request);
        return ResponseEntity.ok(createdDiscount);
    }

    @Operation(summary = "Get All Discount Data", description = "Get All and return Http Status Code 200")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful get discounts data", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
    })
    @GetMapping
    public ResponseEntity<Page<DiscountDTO>> getAll(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam (required = false) String name,
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

    @Operation(summary = "Update Discount", description = "Update discount and return Http status ok")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Create Discount", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DiscountDTO> update(@PathVariable Long id, @Valid @RequestBody DiscountDTO request){
        DiscountDTO updatedDiscount = discountService.update(id, request);
        return ResponseEntity.ok(updatedDiscount);
    }

    @Operation(summary = "Delete Discount", description = "Delete Discount By Discount ID's")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        discountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
