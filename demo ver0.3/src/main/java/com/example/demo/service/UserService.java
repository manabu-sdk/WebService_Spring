package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.TreeMenu;
import com.example.demo.bean.User;
import com.example.demo.repository.UserMenuRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	UserMenuRepository userMenuRepo;

	/**
	 * ユーザーをInsert後、全てのユーザーを抽出する
	 * @param user　Insertユーザー
	 * @return ユーザー一覧
	 */
	public List<User> createAndGetUserList(User user) {
		if(userRepo.insert(user) == 1) {
			user = new User();
			return userRepo.selectByCondition(user);
		}
		return null;
	}


	/**
	 * ユーザーをDelete後、全てのユーザーを抽出する
	 * @param userID 削除するユーザーID
	 * @return　ユーザー一覧
	 */
	public List<User> delPAndGetUserList(String userID) {
		if(userRepo.deleteByPKPhysical(userID) == 1) {
			User user = new User();
			return userRepo.selectByCondition(user);
		}
		return null;
	}

	/**
	 * ユーザーをDelete後、全てのユーザーを抽出する
	 * @param userID 削除するユーザーID
	 * @return　ユーザー一覧
	 */
	public List<User> delLAndGetUserList(String userID) {
		if(userRepo.deleteByPKLogical(userID) == 1) {
			User user = new User();
			return userRepo.selectByCondition(user);
		}
		return null;
	}
	

	public List<User> updateAndGetUserList(User user) {
		if(userRepo.updateByPK(user) == 1) {
			user = new User();
			return userRepo.selectByCondition(user);
		}
		return null;
	}


	public List<User> selectByCondition(User user) {
		return userRepo.selectByCondition(user);
	}
	
	public int deleteByPKPhysical(String userID) {
		return userRepo.deleteByPKPhysical(userID);
	}

	public int deleteByPKLogical(String userID) {
		return userRepo.deleteByPKLogical(userID);
	}

	public int updateByPK(User user) {
		return userRepo.updateByPK(user);
	}

	public int updateLastLoginDateByPK(String userID) {
		return userRepo.updateLastLoginDateByPK(userID);
	}
	
	public int insertUser(User user) {
		return userRepo.insert(user);
	}

	public User selectUserByPK(String userID) {
		return userRepo.selectByPK(userID);
	}
	
	public List<TreeMenu> selectMenuByUserID(String userID) {
		return userMenuRepo.selectMenuByUserID(userID);
	}
}