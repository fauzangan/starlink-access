package Starlink.starlink_access.controller;
import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.service.ProductCategorySevice;
import Starlink.starlink_access.util.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/productcategory")
@RequiredArgsConstructor
@Validated
public class ProductCategoryController {
    private final ProductCategorySevice productCategorySevice;
    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody ProductCategoryDTO request){
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
        return Response.renderJSON(
                productCategorySevice.getOne(id),
                "Success get The category",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody ProductCategoryDTO request){
        return Response.renderJSON(
                productCategorySevice.update(id,request),
                "Category Updated",
                HttpStatus.ACCEPTED
        );
    }
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
