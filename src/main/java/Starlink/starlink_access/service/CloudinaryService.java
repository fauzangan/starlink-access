package Starlink.starlink_access.service;

import Starlink.starlink_access.util.Response.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;


public interface CloudinaryService {
    CloudinaryResponse uploadFile(MultipartFile file, String fileName);
}
