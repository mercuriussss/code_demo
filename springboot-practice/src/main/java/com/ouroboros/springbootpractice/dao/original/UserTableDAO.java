package com.ouroboros.springbootpractice.dao.original;

import com.ouroboros.springbootpractice.pojo.UserTableDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTableDAO {
    List<UserTableDO> selectAll();
}
