package com.zimu.mapper;

import com.zimu.my.mapper.MyMapper;
import com.zimu.pojo.Fans;
import com.zimu.vo.FansVO;
import com.zimu.vo.VlogerVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FansMapperCustom extends MyMapper<Fans> {

    public List<VlogerVO> queryMyFollows(@Param("paramMap") Map<String, Object> map);

    public List<FansVO> queryMyFans(@Param("paramMap") Map<String, Object> map);

}