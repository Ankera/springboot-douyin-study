package com.zimu.service.impl;

import com.zimu.bo.UpdatedUserBO;
import com.zimu.enums.Sex;
import com.zimu.enums.YesOrNo;
import com.zimu.mapper.UsersMapper;
import com.zimu.pojo.Users;
import com.zimu.service.UserService;
import org.n3r.idworker.Sid;
import org.n3r.idworker.utils.DateUtil;
import org.n3r.idworker.utils.DesensitizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    private static final String USER_FACE1 = "https://t9.baidu.com/it/u=1284304438,887034125&fm=3031&app=3031&size=r130,100&q=100&n=0&g=6n&f=JPEG&fmt=auto&maxorilen2heic=2000000?s=18912CD4495245CC10CAE66803001076";

    @Override
    public Users queryMobileIsExist(String mobile) {
        Example example = new Example(Users.class);

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", mobile);

        usersMapper.selectByExample(example);
        return null;
    }

    @Transactional
    @Override
    public Users createUser(String mobile) {
        // 获得全局唯一主键
        String userId = sid.nextShort();
        Users user = new Users();
        user.setId(userId);

        user.setMobile(mobile);
        user.setNickname("用户：" + DesensitizationUtil.commonDisplay(mobile));
        user.setImoocNum("用户：" + DesensitizationUtil.commonDisplay(mobile));
        user.setFace(USER_FACE1);

        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        user.setSex(Sex.secret.type);

        user.setCountry("中国");
        user.setProvince("");
        user.setCity("");
        user.setDistrict("");
        user.setDescription("这家伙很懒，什么都没留下~");
        user.setCanImoocNumBeUpdated(YesOrNo.YES.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        usersMapper.insert(user);

        return user;
    }

    @Override
    public Users getUser(String userId) {
        return null;
    }

    @Override
    public Users updateUserInfo(UpdatedUserBO updatedUserBO) {
        return null;
    }

    @Override
    public Users updateUserInfo(UpdatedUserBO updatedUserBO, Integer type) {
        return null;
    }
}
