package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.DTO.ProductListDTO;
import Starlink.starlink_access.model.ProductList;
import Starlink.starlink_access.repository.ProductListRepository;
import Starlink.starlink_access.repository.ProductRepository;
import Starlink.starlink_access.repository.TransactionRepository;
import Starlink.starlink_access.service.ProductListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductListServiceImplement implements ProductListService {

    private final ProductListRepository productListRepository;
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public ProductList create(ProductListDTO productDTO) {
        ProductList productList = ProductList.builder()
                .id(productDTO.getId())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .product(productRepository.findById(productDTO.getProduct_id())
                        .orElseThrow(() -> new RuntimeException("Product not found")))
                .transaction(transactionRepository.findById(productDTO.getTransaction_id())
                        .orElseThrow(() -> new RuntimeException("Transaction not found")))
                .build();

        return productListRepository.save(productList);
    }

    @Override
    public Page<ProductList> getAll(Pageable pageable, ProductListDTO productListDTO) {
        Page<ProductList> productLists = productListRepository.findAll(pageable);
        return productLists;
    }

    @Override
    public ProductListDTO getOne(Long id) {
        ProductList productList = productListRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductListDTO.builder()
                .id(productList.getId())
                .product_id(productList.getProduct().getId())
                .transaction_id(productList.getTransaction().getId())
                .price(productList.getPrice())
                .quantity(productList.getQuantity())
                .build();
    }

    @Override
    public ProductListDTO update(Long id, ProductListDTO productlistDTO) {
        ProductList newProductList = productListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product List not Found"));

        newProductList.setProduct(productRepository.findById(productlistDTO.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found")));
        newProductList.setPrice(productlistDTO.getPrice());
        newProductList.setQuantity(productlistDTO.getQuantity());

        productListRepository.save(newProductList);

        return ProductListDTO.builder()
                .id(newProductList.getId())
                .quantity(newProductList.getQuantity())
                .price(newProductList.getPrice())
                .product_id(productlistDTO.getProduct_id())
                .build();
    }

    @Override
    public void delete(Long id) {
        productListRepository.deleteById(id);
    }
}
