package com.zimu.service.impl;

import com.zimu.bo.VlogBO;
import com.zimu.enums.YesOrNo;
import com.zimu.mapper.VlogMapper;
import com.zimu.pojo.Vlog;
import com.zimu.service.VlogService;
import com.zimu.utils.PagedGridResult;
import com.zimu.vo.IndexVlogVO;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class VlogServiceImpl implements VlogService {

    @Autowired
    private VlogMapper vlogMapper;

    @Autowired
    private Sid sid;

    @Transactional
    @Override
    public void createVlog(VlogBO vlogBO) {
        String vid = sid.nextShort();

        Vlog vlog = new Vlog();
        BeanUtils.copyProperties(vlogBO, vlog);

        vlog.setId(vid);

        vlog.setLikeCounts(0);
        vlog.setCommentsCounts(0);
        vlog.setIsPrivate(YesOrNo.NO.type);

        vlog.setCreatedTime(new Date());
        vlog.setUpdatedTime(new Date());

        vlogMapper.insert(vlog);
    }

    @Override
    public PagedGridResult getIndexVlogList(String userId, String search, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public IndexVlogVO getVlogDetailById(String userId, String vlogId) {
        return null;
    }

    @Override
    public void changeToPrivateOrPublic(String userId, String vlogId, Integer yesOrNo) {

    }

    @Override
    public PagedGridResult queryMyVlogList(String userId, Integer page, Integer pageSize, Integer yesOrNo) {
        return null;
    }

    @Override
    public void userLikeVlog(String userId, String vlogId) {

    }

    @Override
    public void userUnLikeVlog(String userId, String vlogId) {

    }

    @Override
    public Integer getVlogBeLikedCounts(String vlogId) {
        return 0;
    }

    @Override
    public PagedGridResult getMyLikedVlogList(String userId, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public PagedGridResult getMyFollowVlogList(String myId, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public PagedGridResult getMyFriendVlogList(String myId, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public Vlog getVlog(String id) {
        return null;
    }

    @Override
    public void flushCounts(String vlogId, Integer counts) {

    }
}
