package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.TransactionDTO;

import java.util.List;

public interface TransactionService {
    TransactionDTO create(TransactionDTO request) throws Exception;
    List<TransactionDTO> getAll();
    TransactionDTO getTransactionById(Long id);
    TransactionDTO updateTransactionStatus(Long id) throws Exception;
    void delete(Long id);
}
