package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.service.TransactionService;
import Starlink.starlink_access.util.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionDTO request) throws Exception {
        return Response.renderJSON(
                transactionService.create(request),
                "Transaction Created",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return Response.renderJSON(
                transactionService.getAll(),
                "Success Get Transaction",
                HttpStatus.OK
        );
    }

    @GetMapping("/fetch-status/{id}")
    public ResponseEntity<?> fetchTransactionStatus(@PathVariable Long id) throws Exception{
        return Response.renderJSON(
                transactionService.fetchTransactionStatus(id),
                "Success Fetch Transaction",
                HttpStatus.OK
        );
    }

    @PostMapping("/update-status/{id}/{status}")
    public ResponseEntity<?> updateTransactionStatus(@PathVariable Long id, @PathVariable String status) throws Exception{
        return Response.renderJSON(
                transactionService.updateTransaction(id, status),
                "Success Update Transaction",
                HttpStatus.OK
        );
    }

    @DeleteMapping("{id}")
    public void deleteTransaction(@PathVariable Long id) throws Exception {
        transactionService.delete(id);
    }
}
