package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.repository.TransactionRepository;
import Starlink.starlink_access.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return List.of();
    }

    @Override
    public TransactionDTO getTransactionById(Long id) {
        return null;
    }

    @Override
    public TransactionDTO update(Long id, TransactionDTO request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
