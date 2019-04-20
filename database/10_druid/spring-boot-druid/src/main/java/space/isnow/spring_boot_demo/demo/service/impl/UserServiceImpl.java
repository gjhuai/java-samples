package space.isnow.spring_boot_demo.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.isnow.spring_boot_demo.demo.dao.UserMapper;
import space.isnow.spring_boot_demo.demo.domain.User;
import space.isnow.spring_boot_demo.demo.service.UserService;

import java.util.List;

/**
 * Created by lxq on 15-11-19.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(long id) {

        return userMapper.getUserById(id);
    }

    @Override
    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUserById(user);
    }

    @Override
    public void deleteUserById(long id) {
        userMapper.deleteUserById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }
}
