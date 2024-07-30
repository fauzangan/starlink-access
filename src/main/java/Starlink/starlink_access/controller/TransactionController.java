package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.service.TransactionService;
import Starlink.starlink_access.util.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction API Docs", description = "Transaction API Docs")
public class TransactionController {
    private final TransactionService transactionService;


    @Operation(summary = "Create Transaction", description = "Create transaction and return Http.created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Create Transaction", content = @Content(schema = @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request")
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionDTO request) throws Exception {
        return Response.renderJSON(
                transactionService.create(request),
                "Transaction Created",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get All Transactions", description = "Get All transactions and return Http.OK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Get All Transactions", content = @Content(schema = @Schema(implementation = TransactionDTO.class))),
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        return Response.renderJSON(
                transactionService.getAll(),
                "Success Get Transaction",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Fetch Transaction to Midtrans API", description = "Fetch Transaction to Midtrans API and return Http.OK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Fetch Transaction", content = @Content(schema = @Schema(implementation = TransactionDTO.class))),
    })
    @GetMapping("/fetch-status/{id}")
    public ResponseEntity<?> fetchTransactionStatus(@PathVariable Long id) throws Exception{
        return Response.renderJSON(
                transactionService.fetchTransactionStatus(id),
                "Success Fetch Transaction",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update Transaction Status to Midtrans API", description = "Update Transaction to Midtrans API and return Http.OK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Update Transaction", content = @Content(schema = @Schema(implementation = TransactionDTO.class))),
    })
    @PostMapping("/update-status/{id}/{status}")
    public ResponseEntity<?> updateTransactionStatus(@PathVariable Long id, @PathVariable String status) throws Exception{
        return Response.renderJSON(
                transactionService.updateTransaction(id, status),
                "Success Update Transaction",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete Transaction", description = "Delete Transaction by Transaction ID's")
    @DeleteMapping("{id}")
    public void deleteTransaction(@PathVariable Long id) throws Exception {
        transactionService.delete(id);
    }
}
