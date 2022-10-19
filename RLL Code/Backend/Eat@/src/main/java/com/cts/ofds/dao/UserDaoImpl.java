package com.cts.ofds.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cts.ofds.model.User;

@Component
public class UserDaoImpl implements UserDao{
	
	JdbcTemplate jdbc;
	
	@Autowired
	public UserDaoImpl(JdbcTemplate jdbc) {
		this.jdbc=jdbc;
	}

	@Override
	public List<User> list() throws DataAccessException
	{
		String sql = "select * from user";
		List<User> userList = jdbc.query(sql, UserRowMapper.rowMapper);
		return userList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Optional<User> get(String id) throws DataAccessException
	{
		String sql = "select * from  user where userId=?";
		User user = null;
		try {
			user = jdbc.queryForObject(sql, new Object[] {id}, UserRowMapper.rowMapper);
		}
		catch(DataAccessException ex) {
			System.out.println("User Not Found");
		}
		return Optional.ofNullable(user);
	}

	@Override
	public int update(User t, String id)  throws DataAccessException
	{
		String sql = "update user set name = ? , password = ? , role = ? ,contactNumber = ? where userId = ?";
		int response = jdbc.update(sql, t.getName(),t.getPassword(),t.getRole(),t.getContactNumber(),id);
		return response;
	}

	@Override
	public int add(User t)  throws DataAccessException
	{
		String sql = "insert into user(userId,name,password,role,contactNumber) values ( ?,?,?,?,?)";
		int response = jdbc.update(sql, t.getUserId(),t.getName(),t.getPassword(),t.getRole(),t.getContactNumber());
		return response;
	}

	@Override
	public int delete(String id)  throws DataAccessException
	{
		String sql = "delete from user where userId = ?";
		int response = jdbc.update(sql,id);
		return response;
	}
}
