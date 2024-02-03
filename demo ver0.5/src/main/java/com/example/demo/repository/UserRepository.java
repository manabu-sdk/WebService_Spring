package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.User;

@Mapper
@Repository
public interface UserRepository {

	int insert(@Param("user") User user);
	
	int deleteByPKLogical(@Param("userID") String userID);
	
	int deleteByPKPhysical(@Param("userID") String userID);
	
	int updateByPK(@Param("user") User user);
	
	int updateLastLoginDateAngLangByPK(@Param("userID") String userID, @Param("lang") String lang);
	
	List<User> selectByCondition(@Param("user") User user);

	User selectByPK(@Param("userID") String userID);

}