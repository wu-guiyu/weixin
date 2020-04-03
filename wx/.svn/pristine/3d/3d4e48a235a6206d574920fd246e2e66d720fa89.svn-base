package com.bj.scb.service.impl;

import com.bj.scb.dao.impl.AdminCouponDao;
import com.bj.scb.pojo.AdminCoupon;
import com.bj.scb.service.AdminCouponService;
import com.bj.scb.utils.PageList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminCouponServiceImpl implements AdminCouponService {

    @Resource
    AdminCouponDao adminCouponDao;

    @Override
    public void saveOrUpdate(AdminCoupon adminCoupon) {
        adminCouponDao.saveOrUpdate(adminCoupon);
    }

    @Override
    public int deleteById(String id) {
        if (StringUtils.isEmpty(id)) return 0;
        return adminCouponDao.deleteById(id);
    }

    @Override
    public int deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return 0;
        return adminCouponDao.deleteByIds(ids);
    }

    @Override
    public PageList<AdminCoupon> queryByPage(int offset,int limit,String search) {
        String hql = "from " + AdminCoupon.class.getName() + " a ";
        Map<String, Object> map = new HashMap<>();
        String whereCase = "";
        if (!StringUtils.isEmpty(search)){
            whereCase += " where  a.cp_name like:username";
            map.put("username", "%" + search + "%");
        }
        hql += whereCase + " order by a.cp_start_time desc";

        return adminCouponDao.queryByPage(hql,offset,limit,map);
    }

    @Override
    public AdminCoupon findById(String id) {
        if (StringUtils.isEmpty(id)) return null;
        return adminCouponDao.findById(id);
    }

    @Override
    public AdminCoupon findIsPublishCoupon() {
        return adminCouponDao.findIsPublishCoupon();
    }

    @Override
    public int publishCoupon(String id) {
        if (StringUtils.isEmpty(id)) return 0;
        return adminCouponDao.publishCoupon(id);
    }

    @Override
    public int repealCoupon(String id) {
        if (StringUtils.isEmpty(id)) return 0;
        return adminCouponDao.repealCoupon(id);
    }
}
