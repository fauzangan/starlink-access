package Starlink.starlink_access.service.implementation;
import Starlink.starlink_access.DTO.OnlyForProductDTO;
import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.mapper.ProductCategoryMapper;
import Starlink.starlink_access.mapper.ProductMapper;
import Starlink.starlink_access.service.CloudinaryService;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.repository.ProductCategoryRepository;
import Starlink.starlink_access.repository.ProductRepository;
import Starlink.starlink_access.service.CloudinaryService;
import Starlink.starlink_access.service.ProductCategorySevice;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.util.response.CloudinaryResponse;
import Starlink.starlink_access.util.specification.FileUploadUtil;
import Starlink.starlink_access.util.specification.GeneralSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductSevice {
    private final ProductRepository productRepository;
    private final ProductCategorySevice productCategorySevice;
    private final CloudinaryService cloudinaryService;


    @Override
    public Product create(OnlyForProductDTO productDTO) {
        Product product = Product.builder()
                .picture_source(productDTO.getPicture_source())
                .price(productDTO.getPrice())
                .productCategory(productCategorySevice.getOne(productDTO.getProductCategory_id()))
                .stock(productDTO.getStock())
                .name(productDTO.getName())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAll(Pageable pageable, ProductDTO searchDTO) {
        Specification<Product> specification = GeneralSpecification.getSpecification(searchDTO);
        Page<Product> products = productRepository.findAll(specification, pageable);

        return products;
    }

    @Override
    public ProductDTO getOne(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        return ProductMapper.map(product);
    }

    @Override
    public Product update(Long id, OnlyForProductDTO productDto) {
        ProductDTO productDTO = getOne(id);
        Product update = ProductMapper.map(productDTO);
        update.setPrice(productDto.getPrice());
        update.setName(productDto.getName());
        update.setStock(productDto.getStock());
        update.setProductCategory(productCategorySevice.getOne(productDto.getProductCategory_id()));
        update.setPicture_source(productDto.getPicture_source());
        return productRepository.save(update);
    }

    @Override
    public void delete(Long id) {
        Product delete = ProductMapper.map(getOne(id));
        productRepository.delete(delete);


    }
    public void uploadImage(MultipartFile file,Long id){
        Product product = ProductMapper.map(getOne(id));

        FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);

        String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
        CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName);

        product.setPicture_source(response.getUrl());
        productRepository.save(product);
    }
}
