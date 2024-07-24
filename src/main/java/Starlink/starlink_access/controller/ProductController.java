package Starlink.starlink_access.controller;
import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.util.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/product")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductSevice productSevice;

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody ProductDTO request){
        return Response.renderJSON(
                productSevice.create(request),
                "Product created",
                HttpStatus.OK
        );
    }
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault Pageable pageable, ProductDTO request){
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody ProductDTO request){
        return Response.renderJSON(
                productSevice.update(id,request),
                "Category Updated",
                HttpStatus.ACCEPTED
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productSevice.delete(id);
        return Response.renderJSON(
                "",
                "Category Deleted",
                HttpStatus.OK
        );
    }

}
