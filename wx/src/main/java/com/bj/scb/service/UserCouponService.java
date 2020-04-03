package com.bj.scb.service;

import com.bj.scb.pojo.UserCoupon;
import com.bj.scb.utils.PageList;

import java.util.List;
import java.util.Map;

public interface UserCouponService {
    /**
     * 保存或者更新
     */
    public void saveOrUpdate(String userId) ;

    /**
     * 更新优惠券结束时间小于当前时间的记录，即处理时效的优惠券
     */
    public int updateStatus() ;

    /**
            * 根据传入的id删除单条记录
     * @param id
     * @return
     */
    public int deleteById(String id) ;
    
    /**
     *  根据传入的id和有效时间   删除单条记录
     * @param id
     * @return
     */
    public int deleteById(String id,long startTime,long endTime) ;

    /**
     * 根据传入的ids数组删除数组指定的记录数
     * @param ids
     */
    public int deleteByIds(String[] ids);

    /**
     * @category 前端用户查看历史的优惠券
     * @return
     */
    public List<UserCoupon> queryAllRecordByUserId(String userId);

}
