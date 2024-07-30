package Starlink.starlink_access.controller;
import Starlink.starlink_access.DTO.OnlyForProductDTO;
import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.service.CloudinaryService;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.util.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@Validated
@Tag(name = "Product API Docs", description = "Product Docs API")
public class ProductController {
    private final ProductSevice productSevice;

    @Operation(summary = "Create Product", description = "Create Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Create Products", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OnlyForProductDTO request) {
        try {
            Product createdProduct = productSevice.create(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Response.renderJSON(createdProduct, "Product created", HttpStatus.CREATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.renderJSON(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Operation(summary = "Get All Products", description = "Get Products For Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Get Products", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
    })
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                    @ModelAttribute ProductDTO request) {
        try {
            return ResponseEntity.ok(Response.renderJSON(productSevice.getAll(pageable, request), "Success get all products", HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.renderJSON(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Operation(summary = "Get One Products", description = "Get One Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Get One Products", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            ProductDTO productDTO = productSevice.getOne(id);
            return ResponseEntity.ok(Response.renderJSON(productDTO, "Success get the product", HttpStatus.OK));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.renderJSON(null, e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    @Operation(summary = "Update Product", description = "Update Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Update Product", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid OnlyForProductDTO request) {
        try {
            Product updatedProduct = productSevice.update(id, request);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Response.renderJSON(updatedProduct, "Product updated", HttpStatus.ACCEPTED));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.renderJSON(null, e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    @Operation(summary = "Delete Product", description = "Delete Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Delete Product"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productSevice.delete(id);
        return Response.renderJSON(
                "",
                "Product Deleted",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Upload Photo Product", description = "Upload Photo Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Upload Images", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
    })
    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadImage(@PathVariable Long id,@RequestPart MultipartFile file) {
        productSevice.uploadImage(file,id);
        return Response.renderJSON(
                null,
                "Photo Uploaded",
                HttpStatus.OK
        );
    }

}
