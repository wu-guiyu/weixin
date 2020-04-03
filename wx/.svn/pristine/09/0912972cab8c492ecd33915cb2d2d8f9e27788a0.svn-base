package com.bj.scb.dao.impl;

import com.bj.scb.dao.base.BaseDaoImpl;
import com.bj.scb.pojo.AdminCoupon;
import com.bj.scb.utils.PageList;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class AdminCouponDao extends BaseDaoImpl {

	/**
	 * 保存或者更新
	 * @param adminCoupon
	 */
	public void saveOrUpdate(AdminCoupon adminCoupon) {
		this.saveOrUpdateObject(adminCoupon);
	}

	/**
	 * 根据传入的id删除单条记录
	 * @param id
	 * @return
	 */
    public int deleteById(String id) {
    	String sql = "delete from t_admin_coupon where id='" + id +"'";
		return this.executeSql(sql);
	}

	/**
	 * 根据传入的ids数组删除数组指定的记录数
	 * @param ids
	 */
	public int deleteByIds(String[] ids) {
//		String str= Arrays.toString(ids);
//		str = str.substring(1, str.length()-1);
        String idSting = "";
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            if(i < len - 1){
                idSting += "'" + ids[i] + "'" + ",";
            }else if(i == len - 1){
                idSting += "'" + ids[i] + "'";
            }
        }
		String sql = "delete from t_admin_coupon where id in(" + idSting + ")";
		return this.executeSql(sql);
	}

    /**
     * @category 后台管理页面分页查询
     * @return
     */
    public PageList<AdminCoupon> queryByPage(String hql, int currentPage, int showCount, Map<String,Object> map) {
		return this.findPageList(hql, currentPage, showCount, map);
	}

	/**
	 * 根据传入的id查询单条记录
	 * @param id
	 * @return
	 */
	public AdminCoupon findById(String id) {
		String hql = "from " + AdminCoupon.class.getName() +" where id='" + id +"'";
		return (AdminCoupon)this.findObject(hql);
	}

	/**
	 * 查询当前已经发布了的优惠券信息
	 * 当前只能保证数据库中只有一条是已经发布的信息（业务根据甲方要求先设置一条）
	 * @return
	 */
	public AdminCoupon findIsPublishCoupon() {
		String hql = "from " + AdminCoupon.class.getName() +" where cp_is_publish=1";
		return (AdminCoupon)this.findObject(hql);
	}


	/**
	 * 根据id进行发布优惠券
	 * @param id
	 * @return
	 */
	public int publishCoupon(String id) {
		String sql = "UPDATE t_admin_coupon SET cp_is_publish=1,cp_publish_status='已发布',cp_update_time=CURRENT_TIMESTAMP WHERE id='"+id+"'";
		return this.executeSql(sql);
	}

	/**
	 * 根据id进行撤销优惠券
	 * @param id
	 * @return
	 */
	public int repealCoupon(String id) {
		String sql = "UPDATE t_admin_coupon SET cp_is_publish=2,cp_publish_status='已撤销',cp_update_time=CURRENT_TIMESTAMP WHERE id='"+id+"'";
		return this.executeSql(sql);
	}
}
