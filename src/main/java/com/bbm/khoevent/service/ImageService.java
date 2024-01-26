package com.bbm.khoevent.service;

import com.bbm.khoevent.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String upload(MultipartFile file, Long eventId);
    Image download(Long eventId);
    void delete(Long eventId);
}
