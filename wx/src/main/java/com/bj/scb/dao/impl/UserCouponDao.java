package com.bj.scb.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.bj.scb.dao.base.BaseDaoImpl;
import com.bj.scb.pojo.UserCoupon;
import com.bj.scb.utils.PageList;
import org.springframework.stereotype.Repository;

@Repository
public class UserCouponDao extends BaseDaoImpl {

	/**
	 * 新增或者修改
	 * @param userCoupon
	 */
	public void saveOrUpdate(UserCoupon userCoupon) {
		this.saveOrUpdateObject(userCoupon);
	}

	/**
	 * 更新优惠券结束时间小于当前时间的记录，即处理时效的优惠券
	 */
	public int updateStatus() {
		String sql = "UPDATE t_user_coupon SET cp_status=0 WHERE UNIX_TIMESTAMP(cp_end_time) < UNIX_TIMESTAMP(CURRENT_TIME)";
		return this.executeSql(sql);
	}

	/**
	 * 根据传入的id删除单条记录
	 * @param id
	 * @return
	 */
    public int deleteById(String id) {
    	String sql = "delete from t_user_coupon where id='" + id +"'";
		return this.executeSql(sql);
	}

    public int deleteById(String userId,String startTime,String endTime) {
    	//2020-03-30 16:47:03 startTime format
    	//2020-04-04 16:47:08 endTime format
		String sql = "DELETE FROM t_user_coupon WHERE cp_owner='"+userId+"' AND cp_start_time='"+startTime+"' AND cp_end_time='"+endTime+"' AND cp_status=1";
		return this.executeSql(sql);
	}
    
	/**
	 * 根据传入的ids数组删除数组指定的记录数
	 * @param ids
	 */
	public int deleteByIds(String[] ids) {
		String str= Arrays.toString(ids);
		str = str.substring(1, str.length()-1);
		String sql = "delete from t_user_coupon where id in(" + str + ")";
		return this.executeSql(sql);
	}

    /**
     * @category 前端用户查看历史的优惠券
     * @return
     */
    public List<UserCoupon> queryAllRecordByUserId(String userId) {
		String hql = "from " + UserCoupon.class.getName() + " a where cp_owner='" + userId + "'" + " order by a.cp_start_time desc";
		return this.findList(hql);
	}
}
