package Starlink.starlink_access.util.request;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidtransRequest {
    private String payment_type;
    private Map<String, Object> transaction_details;
    private Map<String, Object> item_details;
    private Map<String, Object> customer_details;
}
