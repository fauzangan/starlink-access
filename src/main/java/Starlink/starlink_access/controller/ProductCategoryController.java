package Starlink.starlink_access.controller;
import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.service.ProductCategorySevice;
import Starlink.starlink_access.util.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/product-categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "Product Category API Docs", description = "Product Category API Docs")
public class ProductCategoryController {
    private final ProductCategorySevice productCategorySevice;

    @Operation(summary = "Create Product Category", description = "Create Category Product and return Http Status 201")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Create Product Category", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request")
    })
    @PostMapping
    public ResponseEntity<?> create (@RequestBody String request){
        return Response.renderJSON(
                productCategorySevice.create(request),
                "Product created",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get All Product Category", description = "Get All Product and Return Http Status 200")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Get Product Categories", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
    })
    @GetMapping
    public ResponseEntity<?> getAll(){
        return Response.renderJSON(
                productCategorySevice.getAll(),
                "Success get all category",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get Product Category By ID's", description = "Get One Product and Return Http Status 200")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Get One Category Product", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        return Response.renderJSON(
                productCategorySevice.getOne(id),
                "Success get The category",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update Product Category", description = "Update Product Category and Return Http Status Accept")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Update Product Categories", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request")
    })
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody String request){
        return Response.renderJSON(
                productCategorySevice.update(id,request),
                "Category Updated",
                HttpStatus.ACCEPTED
        );
    }

    @Operation(summary = "Delete Product Category", description = "Delete Product Category By Product Category ID's")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productCategorySevice.delete(id);
        return Response.renderJSON(
                "",
                "Category Deleted",
                HttpStatus.OK
        );
    }

}
