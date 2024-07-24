package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.service.MidtransService;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MidtransServiceImplement implements MidtransService {

    @Override
    public String createTransactionToken() throws MidtransError {
        Map<String, Object> requestBody = createRequestBody();

        return SnapApi.createTransactionToken(requestBody);
    }

    @Override
    public Map<String, Object> createRequestBody() {
        UUID idRandom = UUID.randomUUID();
        Map<String, Object> params = new HashMap<>();

        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", idRandom.toString());
        transactionDetails.put("gross_amount", "265000");

        Map<String, String> creditCard = new HashMap<>();
        creditCard.put("secure", "true");

        params.put("transaction_details", transactionDetails);
        params.put("credit_card", creditCard);

        return params;
    }




}
