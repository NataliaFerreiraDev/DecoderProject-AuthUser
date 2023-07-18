package br.com.decoder.ead.authuser.repository;

import br.com.decoder.ead.authuser.models.UserCourseModel;
import br.com.decoder.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {

    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

}
