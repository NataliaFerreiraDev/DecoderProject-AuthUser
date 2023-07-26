package br.com.decoder.ead.authuser.services.impl;

import br.com.decoder.ead.authuser.models.UserCourseModel;
import br.com.decoder.ead.authuser.models.UserModel;
import br.com.decoder.ead.authuser.repository.UserCourseRepository;
import br.com.decoder.ead.authuser.repository.UserRepository;
import br.com.decoder.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCourseRepository userCourseRepository;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void delete(UserModel userModel) {
        List<UserCourseModel> userCourseModelList = userCourseRepository.findAllUserCourseIntoCourse(userModel.getUserId());
        if(!userCourseModelList.isEmpty()){
            userCourseRepository.deleteAll(userCourseModelList);
        }
        userRepository.delete(userModel);
    }

    @Override
    public void save(UserModel userModel) {
        userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

}
