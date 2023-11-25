package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.User;
import com.example.demo.bean.TreeMenu;


@Mapper
@Repository
public interface UserRepository {

	int insert(@Param("user") User user);
	
	int deleteByPKLogical(@Param("userID") String userID);
	
	int deleteByPKPhysical(@Param("userID") String userID);
	
	int updateByPK(@Param("user") User user);

	List<User> selectByCondition(@Param("user") User user);

	User selectByPK(@Param("userID") String userID);

}