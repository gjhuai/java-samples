package space.isnow.spring_boot_demo.demo.dao;


import space.isnow.spring_boot_demo.demo.domain.User;

import java.util.List;

/**
 * Created by zl on 2015/8/27.
 */
public interface UserMapper {

    public User getUserById(long id);

    public void saveUser(User user);

    public void updateUserById(User user);

    public void deleteUserById(long id);

    public List<User> getAllUser();
}
