package br.com.decoder.ead.authuser.controllers;

import br.com.decoder.ead.authuser.clients.UserClient;
import br.com.decoder.ead.authuser.dto.CourseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    UserClient userClient;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC)
                                                               Pageable pageable,
                                                               @PathVariable(value = "userId") UUID userId) {


        return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCoursesByUser(userId, pageable));
    }

}
