package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.service.MidtransService;
import Starlink.starlink_access.util.request.MidtransRequest;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MidtransServiceImplement implements MidtransService {

    private static final String MIDTRANS_ENDPOINT = "https://api.sandbox.midtrans.com/v2/charge";
    @Value("${midtrans.serverKey}")
    private static String SERVER_KEY;


    private final RestTemplate restTemplate;

    @Override
    public String chargeTransaction(MidtransRequest midtransRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(SERVER_KEY, "");
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<MidtransRequest> request = new HttpEntity<>(midtransRequest, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                MIDTRANS_ENDPOINT,
                HttpMethod.POST,
                request,
                String.class
        );

        return response.getBody();
    }
}
