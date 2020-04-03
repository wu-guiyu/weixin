package com.bj.scb.weixin;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bj.scb.dao.impl.CustomerInfoDaoImpl;
import com.bj.scb.dao.impl.UserCouponDao;
import com.bj.scb.pojo.AdminCoupon;
import com.bj.scb.pojo.Share;
import com.bj.scb.pojo.UserCoupon;
import com.bj.scb.service.AdminCouponService;
import com.bj.scb.service.CustomerInfoService;
import com.bj.scb.service.ShareService;

import net.sf.json.JSONObject;

@Service
@Transactional
public class WxServiceImpl implements WxService {

	@Resource
	private ShareService shareService;
	@Resource
	private CustomerInfoService customerInfoService;
	@Resource
	private  AdminCouponService adminCouponService;
	@Resource
    private  UserCouponDao userCouponDao;
	@Resource
	private CustomerInfoDaoImpl customerInfoDaoImpl;

	// 获取用户的基本信息
	@Override
	public void getQRcode(Map<String, String> requestMap) {
		String openid = requestMap.get("FromUserName");
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("ACCESS_TOKEN", Util.getAccessToken()).replace("OPENID", openid);
		String result = Util.get(url);
		JSONObject jObject = JSONObject.fromObject(result);
		String gender = null;

		// subscribe值为0，表明用户未关注，拉取不到信息
		if (jObject.get("subscribe").toString().equals("1")) {
			if (jObject.get("sex").toString().equals("1")) {
				gender = "男";
			} else {
				gender = "女";
			}
			try {
				if (customerInfoService.selectNickName(jObject.get("openid").toString()) == null) {
					// 保存用户信息事件
					customerInfoDaoImpl.TBinsertUser(uuid, jObject.get("openid").toString(),
							jObject.get("nickname").toString(), jObject.get("headimgurl").toString(),
							jObject.get("city").toString(), gender);

					// 获取二维码的参数值
					String evenkey = requestMap.get("EventKey").substring(8);
					System.out.println(evenkey);
					if (jObject.get("subscribe_scene").toString().equals("ADD_SCENE_QR_CODE")) {
						if (evenkey.equals("WenXinBJKJSCB")) {
							// 扫描的是永久二维码
							// 给用户发放优惠劵
							// 给用户发放模板消息
							String opind = jObject.get("openid").toString();
							saveOrUpdate(opind);
							TemplateMessageManager.hbTemplateMessageToCustomer(opind);
						} else {
							// 扫描的是带logo的二维码
							Share sh = shareService.getByOpind(evenkey);
							if (sh.getShareCount() == 9) {
								// 给二维码的主人发送模板消息
								// 给二维码的主人发送优惠劵
								String opind = evenkey;
								saveOrUpdate(opind);
								TemplateMessageManager.hbTemplateMessageToCustomer(opind);
							}
							// 给二维码的主人发送模板消息
							// 人气加1保存
							sh.setShareCount(sh.getShareCount() + 1);
							shareService.save(sh);
						}

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 发放优惠劵的方法
	public void saveOrUpdate(String opind) {
		//查询当前有无已发布的优惠券
		AdminCoupon adminCoupon = adminCouponService.findIsPublishCoupon();
		if(adminCoupon == null){
		    return;
		}
		//将字符串解析成对象
		UserCoupon userCoupon = new UserCoupon();
		userCoupon.setCp_owner(opind);
		userCoupon.setCp_amount(adminCoupon.getCp_amount());
		userCoupon.setCp_name(adminCoupon.getCp_name());
		userCoupon.setCp_start_time(adminCoupon.getCp_start_time());
		userCoupon.setCp_end_time(adminCoupon.getCp_end_time());
		userCoupon.setCp_status(1); //有效

		userCouponDao.saveOrUpdate(userCoupon);
	}
}
