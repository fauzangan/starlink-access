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
@RequestMapping("/api/v1/admin/product")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductSevice productSevice;

    @PostMapping
    public ResponseEntity<?> create (@RequestBody OnlyForProductDTO request){
        return Response.renderJSON(
                productSevice.create(request),
                "Product created",
                HttpStatus.OK
        );
    }
    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
                                        Pageable pageable, @ModelAttribute ProductDTO request){
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
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody OnlyForProductDTO request){
        return Response.renderJSON(
                productSevice.update(id,request),
                "Product Updated",
                HttpStatus.ACCEPTED
        );
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
