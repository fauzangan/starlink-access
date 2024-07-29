package Starlink.starlink_access.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransferDTO {
    @NotBlank(message = "Bank is required")
    private String bank;
}
