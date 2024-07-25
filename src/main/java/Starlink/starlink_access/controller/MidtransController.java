package Starlink.starlink_access.controller;

import Starlink.starlink_access.service.MidtransService;
import Starlink.starlink_access.util.request.MidtransRequest;
import Starlink.starlink_access.util.response.MidtransResponse;
import com.midtrans.httpclient.error.MidtransError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class MidtransController {

    @Autowired
    private MidtransService midtransService;

    @PostMapping("/charge")
    public MidtransResponse getTransactionToken(@RequestBody MidtransRequest midtransRequest) {
        return midtransService.chargeTransaction(midtransRequest);
    }

    @GetMapping("/status/{id}")
    public MidtransResponse getTransactionToken(@PathVariable Integer id) {
        return midtransService.checkTransaction(id);
    }
}
