package Starlink.starlink_access.controller;

import Starlink.starlink_access.service.MidtransService;
import Starlink.starlink_access.util.request.MidtransRequest;
import com.midtrans.httpclient.error.MidtransError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class MidtransController {

    @Autowired
    private MidtransService midtransService;

    @GetMapping("/charge")
    public String getTransactionToken(@RequestBody MidtransRequest midtransRequest) {
        return midtransService.chargeTransaction(midtransRequest);
    }
}
