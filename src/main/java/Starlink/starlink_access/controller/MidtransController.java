package Starlink.starlink_access.controller;

import Starlink.starlink_access.service.MidtransService;
import Starlink.starlink_access.util.request.MidtransRequest;
import Starlink.starlink_access.util.response.MidtransResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/payment")
public class MidtransController {

    @Autowired
    private MidtransService midtransService;

    @Operation(summary = "Rest Template API Testing Midtrans", description = "Create Charge Payment API for Testing Purpose")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Create Charge Payment", content = @Content(schema = @Schema(implementation = MidtransResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request")
    })
    @PostMapping("/charge")
    public MidtransResponse getTransactionToken(@RequestBody MidtransRequest midtransRequest) {
        return midtransService.chargeTransaction(midtransRequest);
    }

    @Operation(summary = "Rest Template API Testing Midtrans", description = "Fetch Payment API for Testing Purpose")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Fetch Payment", content = @Content(schema = @Schema(implementation = MidtransResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input request")
    })
    @GetMapping("/status/{id}")
    public MidtransResponse getTransactionToken(@PathVariable Long id) {
        return midtransService.fetchTransaction(id);
    }
}
