package com.bleuon.mappers;

import com.bleuon.models.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    Integer addUser(User user);

    @Select("select * from users")
    List<User> queryAll();

    @Select("select * from users where id = #{id}")
    User queryById(Integer id);

    @Delete("delete from users where id = #{id}")
    Integer deleteById(Integer id);
}
