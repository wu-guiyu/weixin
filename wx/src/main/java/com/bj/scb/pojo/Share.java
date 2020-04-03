package com.bj.scb.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 用户分享信息表
 * @author wuguiyu
 *
 */
@Entity
@Table(name="t_share")
public class Share {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Column
	private String id;// 主键
	@Column
	private String customerId;// 微信号
	@Column
	private String customerNickName;// 昵称
	@Column
	private String customerPic;// 头像
	@Column
	private String customerQRcode;// 二维码本地地址
	@Column
	private Integer shareCount;  //分享次数
	@Column
	private Long insertTime;// 插入时间
	@Column
	private Long upTime;// 完成时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerNickName() {
		return customerNickName;
	}
	public void setCustomerNickName(String customerNickName) {
		this.customerNickName = customerNickName;
	}
	public String getCustomerPic() {
		return customerPic;
	}
	public void setCustomerPic(String customerPic) {
		this.customerPic = customerPic;
	}
	public String getCustomerQRcode() {
		return customerQRcode;
	}
	public void setCustomerQRcode(String customerQRcode) {
		this.customerQRcode = customerQRcode;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public Long getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Long insertTime) {
		this.insertTime = insertTime;
	}
	public Long getUpTime() {
		return upTime;
	}
	public void setUpTime(Long upTime) {
		this.upTime = upTime;
	}
	
	

}
