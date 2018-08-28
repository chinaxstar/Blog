package cn.xstar.samplespringboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.xstar.samplespringboot.pojo.LoginTicket;

public interface LoginTicketRepository extends JpaRepository<LoginTicket, Long>{

	@Query("select t from LoginTicket t where ticket = ?1")
	LoginTicket seletByTicket(String ticket);
	
	

}
