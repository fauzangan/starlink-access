package Starlink.starlink_access.DTO;

import java.util.List;

public class UserDTO {

    private Long id;

    private String username;


    private String password;

    private String role;

    private String email;

    private String name;

    private String birthDate;

    private List<ServiceDetailDTO> serviceDetails;
}
