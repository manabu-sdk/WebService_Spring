package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.TreeMenu;


@Mapper
@Repository
public interface UserMenuRepository {
	
	List<TreeMenu> selectMenuByUserID(@Param("userID") String userID);

}