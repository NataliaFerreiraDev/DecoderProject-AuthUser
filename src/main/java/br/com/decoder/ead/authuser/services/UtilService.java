package br.com.decoder.ead.authuser.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilService {

    String createUrlGetAllCoursesByUser(UUID userId, Pageable pageable);

}
