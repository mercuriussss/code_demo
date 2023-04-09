package com.ouroboros.springbootpractice.dao.sharding;

import com.ouroboros.springbootpractice.pojo.UserTableDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTableShardingDAO {
    List<UserTableDO> selectByUserId(@Param("userId") String userId);
}
