package com.bj.scb.service;

import com.bj.scb.pojo.AdminCoupon;
import com.bj.scb.pojo.UserCoupon;
import com.bj.scb.utils.PageList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface AdminCouponService {
    /**
     * 保存或者更新
     * @param adminCoupon
     */
    public void saveOrUpdate(AdminCoupon adminCoupon);

    /**
     * 根据传入的id删除单条记录
     * @param id
     * @return
     */
    public int deleteById(String id) ;

    /**
     * 根据传入的ids数组删除数组指定的记录数
     * @param ids
     */
    public int deleteByIds(String[] ids);

    /**
     * @category 后台管理页面分页查询
     * @return
     */
    public PageList<AdminCoupon> queryByPage(int offset,int limit,String search);
    /**
     * 根据传入的id查询单条记录
     * @param id
     * @return
     */
    public AdminCoupon findById(String id);

    /**
     * 查询当前已经发布了的优惠券信息
     * 当前只能保证数据库中只有一条是已经发布的信息（业务根据甲方要求先设置一条）
     * @return
     */
    public AdminCoupon findIsPublishCoupon();

    /**
     * 根据id进行发布优惠券
     * @param id
     * @return
     */
    public int publishCoupon(String id);

    /**
     * 根据id进行撤销优惠券
     * @param id
     * @return
     */
    public int repealCoupon(String id);

}
