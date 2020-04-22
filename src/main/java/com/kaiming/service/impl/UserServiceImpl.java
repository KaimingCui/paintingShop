package com.kaiming.service.impl;

import com.kaiming.dao.UserDao;
import com.kaiming.dao.impl.UserDaoImpl;
import com.kaiming.domain.User;
import com.kaiming.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public User login(User user) {
		//调用DAO来查询用户是否存在在数据库中
		UserDao userDao = new UserDaoImpl();
		return userDao.login(user);
	}

}
