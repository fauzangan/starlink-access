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
@Tag(name = "Product API Docs", description = "Product API Docs")
public class ProductController {
    private final ProductSevice productSevice;

    @Operation(summary = "Create Product", description = "Create Product and Return Http Status OK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Create Product", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request")
    })
    @PostMapping
    public ResponseEntity<?> create (@RequestBody OnlyForProductDTO request){
        return Response.renderJSON(
                productSevice.create(request),
                "Product created",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get All Products", description = "Get All Products and Return Http Status OK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Get All Products", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
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

    @Operation(summary = "Get One Product", description = "Get One Products and Return Http Status OK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Get One Product", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        return Response.renderJSON(
                productSevice.getOne(id),
                "Success get The product",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update Product", description = "Update Product and Return Http Status OK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Update Product", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody OnlyForProductDTO request){
        return Response.renderJSON(
                productSevice.update(id,request),
                "Product Updated",
                HttpStatus.ACCEPTED
        );
    }

    @Operation(summary = "Delete Product", description = "Delete Product By ID's")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productSevice.delete(id);
        return Response.renderJSON(
                "",
                "Product Deleted",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update Image Product", description = "Update Images for Product and Return Http Response OK")
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
