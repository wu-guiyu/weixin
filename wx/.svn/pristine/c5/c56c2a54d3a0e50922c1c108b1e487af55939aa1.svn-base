package com.bj.scb.dao.impl;


import org.springframework.stereotype.Repository;

import com.bj.scb.dao.base.BaseDaoImpl;
import com.bj.scb.pojo.Share;

@Repository
public class ShareDaoImpl extends BaseDaoImpl{

	public Share getByOpind(String opind) {
		return this.findObject("from "+Share.class.getName()+" where customerId='"+opind+"'");
	}

	public void save(Share s) {
		this.saveOrUpdateObject(s);
	}

}
