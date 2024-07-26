package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.service.MidtransService;
import Starlink.starlink_access.util.request.MidtransRequest;
import Starlink.starlink_access.util.response.MidtransResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class MidtransServiceImplement implements MidtransService {

    private static final String MIDTRANS_ENDPOINT = "https://api.sandbox.midtrans.com/v2/charge";
    private static final String SERVER_KEY = "SB-Mid-server-30iQHtDfOsjD5qz2TDuKt9Iv";


    private final RestTemplate restTemplate;

    @Override
    public MidtransResponse chargeTransaction(MidtransRequest midtransRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = SERVER_KEY + ":";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);

        httpHeaders.set("Authorization", authHeader);
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<MidtransRequest> request = new HttpEntity<>(midtransRequest, httpHeaders);

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                MIDTRANS_ENDPOINT,
                HttpMethod.POST,
                request,
                MidtransResponse.class
        );

        return response.getBody();
    }

    @Override
    public MidtransResponse getTransactionStatus(Long id) {
        String getStatusEndpoint = "https://api.sandbox.midtrans.com/v2/"+ id +"/status";

        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = SERVER_KEY + ":";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);

        httpHeaders.set("Authorization", authHeader);
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<?> request = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                getStatusEndpoint,
                HttpMethod.GET,
                request,
                MidtransResponse.class
        );

        return response.getBody();
    }
}
