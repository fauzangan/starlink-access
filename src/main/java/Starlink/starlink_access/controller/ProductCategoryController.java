package Starlink.starlink_access.controller;
import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.model.ProductCategory;
import Starlink.starlink_access.service.ProductCategorySevice;
import Starlink.starlink_access.util.response.Response;
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
public class ProductCategoryController {
    private final ProductCategorySevice productCategorySevice;

    @PostMapping
    public ResponseEntity<?> create (@RequestBody String request){
        return Response.renderJSON(
                productCategorySevice.create(request),
                "Product created",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return Response.renderJSON(
                productCategorySevice.getAll(),
                "Success get all category",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try {
            return Response.renderJSON(
                    productCategorySevice.getOne(id),
                    "Success get The category",
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            // Log the exception if needed
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String request) {
        try {
            ProductCategory updatedCategory = productCategorySevice.update(id, request);
            return Response.renderJSON(updatedCategory, "Category Updated", HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return Response.renderJSON(null, "Failed to update category: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            productCategorySevice.delete(id);
            return Response.renderJSON("", "Category Deleted", HttpStatus.OK);
        } catch (RuntimeException e) {
            return Response.renderJSON("", "Failed to delete category: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
