package space.isnow.spring_boot_demo.demo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.isnow.spring_boot_demo.demo.domain.User;
import space.isnow.spring_boot_demo.demo.service.UserService;

import java.util.List;

/**
 * Created by lxq on 15-11-19.
 */
@RestController
//@RequestMapping("/api")
public class UserApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    /**
     * 新增
     * @param user
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void addUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    /**
     * 获取单个用户详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User user(@PathVariable("id") String id) {
        long l_id = Long.parseLong(id);
        return userService.getUserById(l_id);
    }

    /**
     * 编辑
     * @param id
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public void modifyUser(@PathVariable("id") String id,
                                            @RequestBody User user) {
        userService.updateUser(user);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void removeUser(@PathVariable("id") String id) {
        long l_id = Long.parseLong(id);
        userService.deleteUserById(l_id);
    }

    /**
     * 获取所有用户
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> users() {
        return userService.getAllUser();
    }
}
