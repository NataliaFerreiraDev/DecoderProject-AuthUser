package br.com.decoder.ead.authuser.services.impl;

import br.com.decoder.ead.authuser.repository.UserCourseRepository;
import br.com.decoder.ead.authuser.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    UserCourseRepository userCourseRepository;

}
