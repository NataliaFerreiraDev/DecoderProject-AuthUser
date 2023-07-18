package br.com.decoder.ead.authuser.services.impl;

import br.com.decoder.ead.authuser.models.UserCourseModel;
import br.com.decoder.ead.authuser.models.UserModel;
import br.com.decoder.ead.authuser.repository.UserCourseRepository;
import br.com.decoder.ead.authuser.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    UserCourseRepository userCourseRepository;

    @Override
    public boolean existsByUserAndCouserId(UserModel userModel, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(userModel, courseId);
    }

    @Override
    public UserCourseModel save(UserCourseModel userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }

}
