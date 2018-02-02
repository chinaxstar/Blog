package cn.xstar.samplespringboot.dao;

import cn.xstar.samplespringboot.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
@Mapper
public interface UserDao {
    String TABLE_NAEM = " user ";
    String INSERT_FIELDS = " name, password, salt, headUrl ,role ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAEM, "(", INSERT_FIELDS, ") values (#{name},#{password},#{salt},#{headUrl},#{role})"})
    public void insertUser(User user);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAEM, "where id=#{id}"})
    public User seletById(int id);

    /**
     * 查询可user的开放数据
     *
     * @param id
     * @return
     */
    @Select({"select", "id,name,headUrl,role", "from", TABLE_NAEM, "where id=#{id}"})
    public User seletOpenUserById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAEM, "where name=#{name}"})
    public User seletByName(@Param("name") String name);

    @Delete({"delete from", TABLE_NAEM, "where id=#{id}"})
    public void deleteById(int id);
}
