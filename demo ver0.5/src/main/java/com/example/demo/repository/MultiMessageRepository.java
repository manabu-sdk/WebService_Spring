package com.example.demo.repository;

import java.util.List;

import com.example.demo.bean.MappingObject;
import com.example.demo.bean.MultiMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface MultiMessageRepository {

	List<MappingObject<String, MultiMessage>> select();

}