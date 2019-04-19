package space.isnow.spring_boot_demo.demo.service;

import space.isnow.spring_boot_demo.demo.domain.User;

import java.util.List;

/**
 * Created by lxq on 15-11-19.
 */
public interface UserService {

    User getUserById(long id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(long id);

    List<User> getAllUser();


}
