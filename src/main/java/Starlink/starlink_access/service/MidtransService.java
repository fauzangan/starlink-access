package Starlink.starlink_access.service;

import Starlink.starlink_access.util.request.MidtransRequest;
import Starlink.starlink_access.util.response.MidtransResponse;

public interface MidtransService {
    MidtransResponse chargeTransaction(MidtransRequest midtransRequest);
    MidtransResponse fetchTransaction(Long id);
    MidtransResponse updateTransactionStatus(Long id, String status);
}
