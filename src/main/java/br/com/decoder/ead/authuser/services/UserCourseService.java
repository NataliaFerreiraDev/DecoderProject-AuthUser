package br.com.decoder.ead.authuser.services;

import br.com.decoder.ead.authuser.models.UserCourseModel;
import br.com.decoder.ead.authuser.models.UserModel;

import java.util.UUID;

public interface UserCourseService {

    boolean existsByUserAndCouserId(UserModel userModel, UUID courseId);

    UserCourseModel save(UserCourseModel userCourseModel);
}
