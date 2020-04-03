package com.bj.scb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bj.scb.dao.impl.ShareDaoImpl;
import com.bj.scb.pojo.Share;
import com.bj.scb.service.ShareService;

@Service
@Transactional
public class ShareServiceImpl implements ShareService{
	@Autowired
	private ShareDaoImpl shareDaoImpl;

	@Override
	public Share getByOpind(String opind) {
		return shareDaoImpl.getByOpind(opind);
	}

	@Override
	public void save(Share s) {
		shareDaoImpl.save(s);
	}


}
