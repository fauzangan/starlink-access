package Starlink.starlink_access.service;

//import com.midtrans.proxy.models.Response;

import Starlink.starlink_access.util.Response.Response;
import Starlink.starlink_access.util.request.MidtransRequest;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import okhttp3.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface MidtransService {
    String chargeTransaction(MidtransRequest midtransRequest);
}
