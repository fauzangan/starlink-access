package Starlink.starlink_access.controller;
import Starlink.starlink_access.DTO.OnlyForProductDTO;
import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.service.CloudinaryService;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.util.response.Response;
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
public class ProductController {
    private final ProductSevice productSevice;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid OnlyForProductDTO request) {
        try {
            Product createdProduct = productSevice.create(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Response.renderJSON(createdProduct, "Product created", HttpStatus.CREATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.renderJSON(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productSevice.delete(id);
        return Response.renderJSON(
                "",
                "Product Deleted",
                HttpStatus.OK
        );
    }

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
