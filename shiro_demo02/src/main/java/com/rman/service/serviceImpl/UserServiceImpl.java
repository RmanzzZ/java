package com.rman.service.serviceImpl;


import com.rman.entity.UserBean;
import com.rman.mapper.UserDAO;
import com.rman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ziming
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserBean getUserBean(String username) {
        return userDAO.getUserBean(username);
    }
}

