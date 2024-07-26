package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.DTO.TransactionDetailDTO;
import Starlink.starlink_access.mapper.TransactionMapper;
import Starlink.starlink_access.model.ProductList;
import Starlink.starlink_access.model.Transaction;
import Starlink.starlink_access.repository.DiscountRepository;
import Starlink.starlink_access.repository.ProductListRepository;
import Starlink.starlink_access.repository.TransactionRepository;
import Starlink.starlink_access.repository.UserRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImplement implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductListServiceImplement productListServiceImplement;
    private final ProductListRepository productListRepository;
    private final UserRepository userRepository;
    private final MidtransService midtransService;
    private final ProductSevice productSevice;
    private final DiscountRepository discountRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionDTO create(TransactionDTO request) throws Exception{

        Transaction transaction = Transaction.builder()
                .expired_date(DateFormatter.convertStringDateToLong(request.getExpired_date()))
                .discount(discountRepository.findById(request.getDiscount()).orElseThrow(
                        () -> new RuntimeException("Error find discount")
                ))
                .user(userRepository.findById(request.getUser_id())
                        .orElseThrow(() -> new RuntimeException("User not found")))
                .build();

        Transaction createdTransaction = transactionRepository.save(transaction);

        Long totalTransactionPrice = 0L;
        Long totalQuantity = 0L;
        List<ProductList> temp = new ArrayList<>();

        for (var productList : request.getProductLists()){
            productList.setTransaction_id(createdTransaction.getId());
            try {
                productList.setPrice(productSevice.getOne(productList.getProduct_id()).getPrice()*productList.getQuantity());
                temp.add(productListServiceImplement.create(productList));
            } catch (Exception e){
                throw new RuntimeException("Failed creating product list, rollback transaction");
            }
            totalTransactionPrice += productList.getPrice();
            totalQuantity += productList.getQuantity();
        }

        createdTransaction.setProductLists(temp);
        totalTransactionPrice = totalTransactionPrice - (totalTransactionPrice*request.getDiscount()/100);

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
    public TransactionDTO updateTransactionStatus(Long id) throws Exception {
        MidtransResponse midtransResponse = midtransService.getTransactionStatus(id);
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Transaction not Found")
        );
        transaction.setTransaction_status(midtransResponse.getTransactionStatus());
        return TransactionMapper.map(transaction);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
