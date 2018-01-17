package cn.xstar.samplespringboot.dao;

import cn.xstar.samplespringboot.pojo.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Component
@Repository
@Mapper
public interface LoginTicketDao {
    String TABLE_NAEM = " login_ticket ";
    String INSERT_FIELDS = " userId, ticket, expired, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAEM, "(", INSERT_FIELDS, ") values (#{userId},#{ticket},#{expired},#{status})"})
    void insertLoginTicket(LoginTicket loginTicket);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAEM, "where id=#{id}"})
    LoginTicket seletById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAEM, "where userId=#{userId}"})
    LoginTicket seletByUserId(@Param("userId") int userId);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAEM, "where ticket=#{ticket}"})
    LoginTicket seletByTicket(@Param("ticket") String ticket);

    @Update({"update", TABLE_NAEM, "set status = #{status},expired=#{expired} where ticket = #{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("expired") Date expired, @Param("status") int status);

    @Delete({"delete from", TABLE_NAEM, "where id=#{id}"})
    void deleteById(int id);
}
