package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.DTO.TransactionDetailDTO;
import Starlink.starlink_access.mapper.TransactionMapper;
import Starlink.starlink_access.model.ProductList;
import Starlink.starlink_access.model.Transaction;
import Starlink.starlink_access.repository.*;
import Starlink.starlink_access.service.AuthService;
import Starlink.starlink_access.service.MidtransService;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.service.TransactionService;
import Starlink.starlink_access.util.request.MidtransRequest;
import Starlink.starlink_access.util.response.MidtransResponse;
import Starlink.starlink_access.utils.DateFormatter;
import Starlink.starlink_access.utils.MidtransResponseInjector;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImplement implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductListServiceImplement productListServiceImplement;
    private final ProductListRepository productListRepository;
    private final AuthService authService;
    private final MidtransService midtransService;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionDTO create(TransactionDTO request) throws Exception{

        Transaction transaction = Transaction.builder()
                .discount(discountRepository.findById(request.getDiscount()).orElse(null))
                .user(authService.getUserAuthenticated())
                .build();

        Transaction createdTransaction = transactionRepository.save(transaction);

        Long totalTransactionPrice = 0L;
        Long totalQuantity = 0L;
        List<ProductList> temp = new ArrayList<>();

        for (var productList : request.getProductLists()){
            productList.setTransaction_id(createdTransaction.getId());
            try {
                var product = productRepository.findById(productList.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));
                productList.setPrice(product.getPrice()*productList.getQuantity());
                temp.add(productListServiceImplement.create(productList));

                product.setStock(product.getStock() - productList.getQuantity());
                if (product.getStock() < 0){
                    throw new RuntimeException("Product Quantity should not more than stock avalilable");
                } else {
                    productRepository.save(product);
                }

            } catch (Exception e){
                throw new RuntimeException("Failed creating product list, rollback transaction");
            }
            totalTransactionPrice += productList.getPrice();
            totalQuantity += productList.getQuantity();
        }

        createdTransaction.setProductLists(temp);
        if (transaction.getDiscount() != null){
            totalTransactionPrice = totalTransactionPrice - (totalTransactionPrice * transaction.getDiscount().getPercentage()/100);
        }

        try {
            MidtransRequest midtransRequest = MidtransRequest.builder()
                    .payment_type(request.getPayment_type())
                    .transaction_details(TransactionDetailDTO.builder()
                            .gross_amount(totalTransactionPrice)
                            .order_id(String.valueOf(createdTransaction.getId()))
                            .build())
                    .bank_transfer(request.getBank_transfer())
                    .build();

            MidtransResponse midtransResponse = midtransService.chargeTransaction(midtransRequest);
            MidtransResponseInjector.injectToTransaction(midtransResponse, createdTransaction);
            createdTransaction.setTotal_price(totalTransactionPrice);
            createdTransaction.setQuantity(totalQuantity);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        Transaction savedTransaction = transactionRepository.save(createdTransaction);
        return TransactionMapper.map(savedTransaction);
    }

    @Override
    public List<TransactionDTO> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream().map(TransactionMapper::map).collect(Collectors.toList());
    }

    @Override
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        return TransactionMapper.map(transaction);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionDTO fetchTransactionStatus(Long id) throws Exception {
        MidtransResponse midtransResponse = midtransService.fetchTransaction(id);
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Transaction not Found")
        );
        transaction.setTransaction_status(midtransResponse.getTransactionStatus());
        transactionRepository.save(transaction);
        return TransactionMapper.map(transaction);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionDTO updateTransaction(Long id, String status) {
        MidtransResponse midtransResponse = midtransService.updateTransactionStatus(id, status);
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Transaction not found")
        );
        transaction.setTransaction_status(midtransResponse.getTransactionStatus());
        transactionRepository.save(transaction);
        return TransactionMapper.map(transaction);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(Long id) {
        List<ProductList> productLists = productListRepository.findByTransactionId(id);
        for (var productList : productLists){
            productListRepository.delete(productList);
        }
        transactionRepository.deleteById(id);
    }
}
