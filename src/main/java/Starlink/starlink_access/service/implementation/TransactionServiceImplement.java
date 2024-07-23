package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.DTO.ProductListDTO;
import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.mapper.TransactionMapper;
import Starlink.starlink_access.model.ProductList;
import Starlink.starlink_access.model.Transaction;
import Starlink.starlink_access.repository.TransactionRepository;
import Starlink.starlink_access.repository.UserRepository;
import Starlink.starlink_access.service.TransactionService;
import Starlink.starlink_access.utils.DateFormatter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImplement implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductListServiceImplement productListServiceImplement;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionDTO create(TransactionDTO request) throws Exception{

        Transaction transaction = Transaction.builder()
                .quantity(request.getQuantity())
                .total_price(request.getTotal_price())
                .created_at(DateFormatter.convertStringDateToLong(request.getCreated_at()))
                .updated_at(DateFormatter.convertStringDateToLong(request.getUpdated_at()))
                .expired_date(DateFormatter.convertStringDateToLong(request.getExpired_date()))
                .is_settled(false)
                .user(userRepository.findById(request.getUser_id())
                        .orElseThrow(() -> new RuntimeException("User not found")))
                .build();

        transactionRepository.save(transaction);

        for (var productList : request.getProductLists()){
            productListServiceImplement.create(productList);
        }

        return TransactionMapper.map(transaction);
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
    public TransactionDTO update(Long id, TransactionDTO request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
