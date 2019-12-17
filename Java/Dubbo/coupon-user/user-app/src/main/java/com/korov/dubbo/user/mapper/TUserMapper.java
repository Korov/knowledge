package com.korov.dubbo.user.mapper;

import com.korov.dubbo.user.domain.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertOrUpdate(TUser record);

    int insertOrUpdateSelective(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    int updateBatch(List<TUser> list);

    int batchInsert(@Param("list") List<TUser> list);
}
