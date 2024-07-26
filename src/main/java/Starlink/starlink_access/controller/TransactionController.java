package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.service.TransactionService;
import Starlink.starlink_access.service.implementation.TransactionServiceImplement;
import Starlink.starlink_access.util.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
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
}
