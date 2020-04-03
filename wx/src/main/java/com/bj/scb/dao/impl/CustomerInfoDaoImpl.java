package com.bj.scb.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bj.scb.dao.base.BaseDaoImpl;
import com.bj.scb.pojo.CustomerInfo;
import com.bj.scb.utils.PageList;

@Repository
public class CustomerInfoDaoImpl extends BaseDaoImpl {


	// 查询所有已关注的用户
	public PageList<CustomerInfo> selectAll(Map<String, Object> parameter) {
		String hql = "from " + CustomerInfo.class.getName();
		String where = " where 1=1";
		String whereCase = " a " + where;
		Map<String, Object> map = new HashMap<String, Object>();
		int currentPage = Integer.parseInt(parameter.get("currentPage").toString());
		int showCount = Integer.parseInt(parameter.get("showCount").toString());
		String search = parameter.get("search") == null ? "" : parameter.get("search").toString();
		if (!search.equals("")) {
			whereCase += " and a.customerNickName like:username" + " or a.customerNickName like:customerNickName "
					+ " or a.customerMobile like:customerMobile ";
			map.put("username", "%" + search + "%");
			map.put("customerNickName", "%" + search + "%");
			map.put("customerMobile", "%" + search + "%");
		}
		hql += whereCase;

		return this.findPageList(hql, currentPage, showCount, map);
	}

	public CustomerInfo getCustomerInfo(String id) {
		return this.findObject("from " + CustomerInfo.class.getName() + " where id='" + id + "'");
	}

	public void saveCustomerInfo(CustomerInfo customerInfo) {
		this.saveOrUpdateObject(customerInfo);
	}

	public void deleteCustomerInfo(String id) {
		String[] ids = id.split(",");
		Session session = this.getSession();
		for (int i = 0; i < ids.length; i++) {
			CustomerInfo customerInfo = this.getCustomerInfo(ids[i]);
			if (customerInfo != null) {
				session.delete(customerInfo);
			}
		}
	}

	public CustomerInfo selectNicName(String orderUserId) {
		String hql = " from " + CustomerInfo.class.getName() + " where customerId='" + orderUserId + "'";
		return this.findObject(hql);
	}
	
	
	// 保存用户关注信息的方法
	public void TBinsertUser(String uuid, String customerId, String customerNickName, String customerPic,
			String customerAddress, String gender) {
		String sql = "insert into t_customer_info (id,customerId,customerNickName,customerPic,customerAddress,customerGender,isInnerPeople) value"
				+ " ('" + uuid + "','" + customerId + "','" + customerNickName + "','" + customerPic + "','"
				+ customerAddress + "','" + gender + "','否')";
		Session session = this.getSession();
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
		

}
