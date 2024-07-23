package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.DTO.ProductListDTO;
import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.mapper.TransactionMapper;
import Starlink.starlink_access.model.Transaction;
import Starlink.starlink_access.repository.TransactionRepository;
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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionDTO create(TransactionDTO request) throws Exception{

        return null;
    }

    @Override
    public List<TransactionDTO> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream().map(TransactionMapper::map).collect(Collectors.toList());
    }

    @Override
    public TransactionDTO getTransactionById(Integer id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        return TransactionMapper.map(transaction);
    }

    @Override
    public TransactionDTO update(Integer id, TransactionDTO request) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        transactionRepository.deleteById(id);
    }
}
