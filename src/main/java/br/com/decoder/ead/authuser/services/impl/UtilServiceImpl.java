package br.com.decoder.ead.authuser.services.impl;

import br.com.decoder.ead.authuser.services.UtilService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {

    String REQUEST_URI = "http://localhost:8082";

    public String createUrlGetAllCoursesByUser(UUID userId, Pageable pageable) {
        return REQUEST_URI + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size=" +
                pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }

}
