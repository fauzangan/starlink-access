package Starlink.starlink_access.utils;

import Starlink.starlink_access.model.Transaction;
import Starlink.starlink_access.util.response.MidtransResponse;

public class MidtransResponseInjector {
    public static void injectToTransaction(MidtransResponse midtransResponse, Transaction transaction){
        transaction.setTransaction_status(midtransResponse.getTransactionStatus());
        transaction.setVirtualNumber(midtransResponse.getVaNumbers().get(0).getVaNumber());
        transaction.setBank_transfer(midtransResponse.getVaNumbers().get(0).getBank());
        transaction.setPayment_type(midtransResponse.getPaymentType());
        transaction.setTransaction_details(midtransResponse.getOrderId());
    }
}
