package com.zimu.service.impl;

import com.zimu.bo.UpdatedUserBO;
import com.zimu.enums.Sex;
import com.zimu.enums.UserInfoModifyType;
import com.zimu.enums.YesOrNo;
import com.zimu.exceptions.GraceException;
import com.zimu.grace.result.GraceJSONResult;
import com.zimu.grace.result.ResponseStatusEnum;
import com.zimu.mapper.UsersMapper;
import com.zimu.pojo.Users;
import com.zimu.service.UserService;
import org.n3r.idworker.Sid;
import org.n3r.idworker.utils.DateUtil;
import org.n3r.idworker.utils.DesensitizationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Objects;

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
        Users users = usersMapper.selectByPrimaryKey(userId);
        return users;
    }

    @Transactional
    @Override
    public Users updateUserInfo(UpdatedUserBO updatedUserBO) {
        Users pendingUsers = new Users();
        BeanUtils.copyProperties(updatedUserBO, pendingUsers);

        int i = usersMapper.updateByPrimaryKeySelective(pendingUsers);

        if (i != 1) {
            GraceException.display(ResponseStatusEnum.USER_INFO_UPDATED_ERROR);
        }

        return getUser(updatedUserBO.getId());
    }

    @Override
    public Users updateUserInfo(UpdatedUserBO updatedUserBO, Integer type) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.equals(type, UserInfoModifyType.NICKNAME.type)) {
            criteria.andEqualTo("nickname", updatedUserBO.getNickname());
            Users user = usersMapper.selectOneByExample(example);
            if (user != null) {
                GraceException.display(ResponseStatusEnum.USER_INFO_UPDATED_NICKNAME_EXIST_ERROR);
            }
        }

        if (Objects.equals(type, UserInfoModifyType.IMOOCNUM.type)) {
            criteria.andEqualTo("imoocNum", updatedUserBO.getImoocNum());
            Users user = usersMapper.selectOneByExample(example);
            if (user != null) {
                GraceException.display(ResponseStatusEnum.USER_INFO_UPDATED_NICKNAME_EXIST_ERROR);
            }

            Users tempUser =  getUser(updatedUserBO.getId());
            if (tempUser.getCanImoocNumBeUpdated() == YesOrNo.NO.type) {
                GraceException.display(ResponseStatusEnum.USER_INFO_CANT_UPDATED_IMOOCNUM_ERROR);
            }

            updatedUserBO.setCanImoocNumBeUpdated(YesOrNo.NO.type);
        }

        return updateUserInfo(updatedUserBO);
    }
}
