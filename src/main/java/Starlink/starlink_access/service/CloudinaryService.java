package Starlink.starlink_access.service;

import Starlink.starlink_access.util.response.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    CloudinaryResponse uploadFile(MultipartFile file, String fileName);
}
