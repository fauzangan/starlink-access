//package Starlink.starlink_access.service;
//
//import Starlink.starlink_access.model.*;
//import Starlink.starlink_access.repository.*;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class SeederService implements CommandLineRunner {
//    private final DiscountRepository discountRepository;
//    private final ObjectMapper objectMapper;
//    private final ProductRepository productRepository;
//    private final TransactionRepository transactionRepository;
//    private final ProductCategoryRepository productCategoryRepository;
//    private final ProductListRepository productListRepository;
//    private final UserRepository userRepository;
//    private final StatisticRepository statisticRepository;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        seedDiscount();
//        seedProductCategory();
//        seedProduct();
//        seedUser();
//        seedStatistic();
//        seedTransaction();
//        seedProductList();
//    }
//
//    private void seedDiscount() {
//        if (discountRepository.count() == 0) {
//            try {
//                TypeReference<List<Discount>> typeReference = new TypeReference<>() {
//                };
//                InputStream inputStream = new ClassPathResource("seeder/discount.json").getInputStream();
//                List<Discount> discount = objectMapper.readValue(inputStream, typeReference);
//                discountRepository.saveAll(discount);
//                System.out.println("Discount Seeded");
//            } catch (IOException e) {
//                System.out.println("Unable to seed products: " + e.getMessage());
//            }
//        }
//    }
//
//    private void seedProduct() {
//        if (productRepository.count() == 0) {
//            try {
//                TypeReference<List<Product>> typeReference = new TypeReference<>() {
//                };
//                InputStream inputStream = new ClassPathResource("seeder/product.json").getInputStream();
//                List<Product> product = objectMapper.readValue(inputStream, typeReference);
//                for(int i = 0; i < (long) product.size(); i++) {
//                    product.get(i).setProductCategory(productCategoryRepository.findById(product.get(i).getProductCategory().getId()).get());
//                }
//                productRepository.saveAll(product);
//                System.out.println("Product Seeded");
//            } catch (IOException e) {
//                System.out.println("Unable to seed products: " + e.getMessage());
//            }
//        }
//    }
//
//    private void seedProductCategory() {
//        if (productCategoryRepository.count() == 0) {
//            try {
//                TypeReference<List<ProductCategory>> typeReference = new TypeReference<>() {
//                };
//                InputStream inputStream = new ClassPathResource("seeder/productCategory.json").getInputStream();
//                List<ProductCategory> productCategory = objectMapper.readValue(inputStream, typeReference);
//                productCategoryRepository.saveAll(productCategory);
//                System.out.println("Product Category Seeded");
//            } catch (IOException e) {
//                System.out.println("Unable to seed products: " + e.getMessage());
//            }
//        }
//    }
//
//    private void seedProductList() {
//        if (productListRepository.count() == 0) {
//            try {
//                TypeReference<List<ProductList>> typeReference = new TypeReference<>() {
//                };
//                InputStream inputStream = new ClassPathResource("seeder/productList.json").getInputStream();
//                List<ProductList> productList = objectMapper.readValue(inputStream, typeReference);
//
//                for(int i = 0; i < (long) productList.size(); i++) {
//                    productList.get(i).setProduct(productRepository.findById(productList.get(i).getProduct().getId()).get());
//                    productList.get(i).setTransaction(transactionRepository.findById(productList.get(i).getTransaction().getId()).get());
//                }
//
//                productListRepository.saveAll(productList);
//                System.out.println("Product List Seeded");
//            } catch (IOException e) {
//                System.out.println("Unable to seed products: " + e.getMessage());
//            }
//        }
//    }
//
//    private void seedTransaction() {
//        if (transactionRepository.count() == 0) {
//            try {
//                TypeReference<List<Transaction>> typeReference = new TypeReference<>() {
//                };
//                InputStream inputStream = new ClassPathResource("seeder/transaction.json").getInputStream();
//                List<Transaction> transaction = objectMapper.readValue(inputStream, typeReference);
//
//                for(int i = 0; i < (long) transaction.size(); i++) {
//                    transaction.get(i).setUser(userRepository.findById(transaction.get(i).getUser().getId()).get());
//                    transaction.get(i).setDiscount(discountRepository.findById(transaction.get(i).getDiscount().getId()).get());
//                }
//
//                transactionRepository.saveAll(transaction);
//                System.out.println("transaction Seeded");
//            } catch (IOException e) {
//                System.out.println("Unable to seed products: " + e.getMessage());
//            }
//        }
//    }
//
//    private void seedUser() {
//        if (userRepository.count() == 0) {
//            try {
//                TypeReference<List<User>> typeReference = new TypeReference<>() {
//                };
//                InputStream inputStream = new ClassPathResource("seeder/user.json").getInputStream();
//                List<User> user = objectMapper.readValue(inputStream, typeReference);
//                userRepository.saveAll(user);
//                System.out.println("user Seeded");
//            } catch (IOException e) {
//                System.out.println("Unable to seed products: " + e.getMessage());
//            }
//        }
//    }
//
//    private void seedStatistic() {
//        if (statisticRepository.count() == 0) {
//            try {
//                TypeReference<List<Statistic>> typeReference = new TypeReference<>() {
//                };
//                InputStream inputStream = new ClassPathResource("seeder/statistic.json").getInputStream();
//                List<Statistic> user = objectMapper.readValue(inputStream, typeReference);
//                statisticRepository.saveAll(user);
//                System.out.println("statistic Seeded");
//            } catch (IOException e) {
//                System.out.println("Unable to seed products: " + e.getMessage());
//            }
//        }
//    }
//}
