package cn.xstar.samplespringboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.xstar.samplespringboot.pojo.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
