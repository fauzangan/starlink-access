package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.config.MidtransConfig;
import Starlink.starlink_access.service.MidtransService;
import Starlink.starlink_access.util.request.MidtransRequest;
import Starlink.starlink_access.util.response.MidtransResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MidtransServiceImplement implements MidtransService {

    private static final String MIDTRANS_ENDPOINT = "https://api.sandbox.midtrans.com/v2";
    private final MidtransConfig midtransConfig;
    private final RestTemplate restTemplate;

    @Override
    public MidtransResponse chargeTransaction(MidtransRequest midtransRequest) {
        HttpEntity<MidtransRequest> request = new HttpEntity<>(midtransRequest, midtransConfig.getServerHeader());

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                MIDTRANS_ENDPOINT+"/charge",
                HttpMethod.POST,
                request,
                MidtransResponse.class
        );

        return response.getBody();
    }

    @Override
    public MidtransResponse fetchTransaction(Long id) {
        HttpEntity<?> request = new HttpEntity<>(null, midtransConfig.getServerHeader());

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                MIDTRANS_ENDPOINT + "/" + id + "/status",
                HttpMethod.GET,
                request,
                MidtransResponse.class
        );

        return response.getBody();
    }

    @Override
    public MidtransResponse updateTransactionStatus(Long id, String status) {
        HttpEntity<?> request = new HttpEntity<>(null, midtransConfig.getServerHeader());

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                MIDTRANS_ENDPOINT + "/" + id + "/" + status,
                HttpMethod.POST,
                request,
                MidtransResponse.class
        );

        return response.getBody();
    }
}
