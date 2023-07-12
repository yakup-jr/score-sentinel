package com.ss.portal.user.service;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.roles.entity.RoleEntity;
import com.ss.portal.user.entity.UserEntity;
import com.ss.portal.user.exception.UserAlreadyExistException;
import com.ss.portal.user.exception.UserNotFoundException;
import com.ss.portal.user.model.UserModel;
import com.ss.portal.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel add(UserEntity userEntity) throws
        UserAlreadyExistException {
        if (findByUsername(userEntity.getUsername()) == null)
            return UserModel.toModel(userRepository.save(userEntity));
        throw new UserAlreadyExistException(userEntity.getUsername());
    }

    public UserModel findById(Long id) {
        return UserModel.toModel(userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id)));
    }

    public UserModel findByUsername(String username) {
        return UserModel.toModel(userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username, null)));
    }

    public UserModel findByEmail(String email) {
        return UserModel.toModel(userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(null, email)));
    }

    public List<UserModel> findByFilters(List<RoleEntity> roleEntities,
                                         List<GameEntity> gameEntities,
                                         boolean isActive) {
        UserEntity user = new UserEntity();
        user.setRoles(roleEntities);
        user.setGames(gameEntities);
        user.setActive(isActive);
        return UserModel.toModel(userRepository.findAll(Example.of(user)));
    }

    public List<UserModel> findAll() {
        return UserModel.toModel(userRepository.findAll());
    }

    public UserModel updateById(Long id, UserEntity updates) {
        userRepository.updateById(id, updates);

        return findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
