package com.bj.scb.controller;

import com.alibaba.fastjson.JSON;
import com.bj.scb.pojo.AdminCoupon;
import com.bj.scb.service.AdminCouponService;
import com.bj.scb.utils.PageList;
import com.bj.scb.weixin.AuthUtil;
import com.bj.scb.weixin.Util;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.Addressing;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "adminCoupon", produces = "application/json; charset=utf-8")
public class AdminCouponController {

    @Resource
    AdminCouponService adminCouponService;

    /**
     * 后台管理-分页查询
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "queryByPage")
    @ResponseBody
    public String queryByPage(HttpServletRequest request) throws Exception {
        int currentPage = 1;
        if (!StringUtils.isEmpty(request.getParameter("offset"))){
            currentPage = Integer.parseInt(request.getParameter("offset"));// 每页行数
        }
        int showCount = 10;
        if (!StringUtils.isEmpty(request.getParameter("limit"))){
            showCount = Integer.parseInt(request.getParameter("limit"));// 每页行数
        }
        String search = request.getParameter("search");// 每页行数;
        currentPage = currentPage / showCount; // 获取页数
        currentPage += 1;
        PageList<AdminCoupon> list = adminCouponService.queryByPage(currentPage,showCount,search);
        // 成功返回的结果
        return "{\"total\":" + list.getTotal() + ",\"rows\":" + JSON.toJSONString(list.getList()) + "}";

    }

    @RequestMapping(value = "deleteById")
    @ResponseBody
    public String deleteById(HttpServletRequest request) {
        int ret = adminCouponService.deleteById(request.getParameter("id"));
        Map<String, Object> result = new HashMap<String, Object>();
        if (ret > 0) {
            result.put("status", "1");
            result.put("message", "删除成功");
        } else {
            result.put("status", "0");
            result.put("message", "删除失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 根据ids批量删除
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteByIds")
    @ResponseBody
    public String deleteByIds(HttpServletRequest request, @RequestParam Map<String,String> map) {
        String ids = map.get("ids");
        int ret = adminCouponService.deleteByIds(ids.split(","));
        Map<String, Object> result = new HashMap<String, Object>();
        if (ret > 0) {
            result.put("status", "1");
            result.put("message", "删除成功");
        } else {
            result.put("status", "0");
            result.put("message", "删除失败");
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(HttpServletRequest request,@RequestParam Map<String,String> parameter) {
        String id = parameter.get("id");
        String creator_id = parameter.get("creator_id");
        String creator_name = parameter.get("creator_name");
        String cp_name = parameter.get("cp_name");
        String cp_amount = parameter.get("cp_amount");
        long cp_start_time = Long.valueOf(parameter.get("cp_start_time"));
        long cp_end_time = Long.valueOf(parameter.get("cp_end_time"));

        AdminCoupon adminCoupon = adminCouponService.findById(id);
        if(adminCoupon == null){
            adminCoupon = new AdminCoupon();
        }
        adminCoupon.setCreator_id(creator_id);
        adminCoupon.setCreator_name(creator_name);
        adminCoupon.setCp_name(cp_name);
        adminCoupon.setCp_amount(Integer.valueOf(cp_amount));
        adminCoupon.setCp_start_time(new Timestamp(cp_start_time));
        adminCoupon.setCp_end_time(new Timestamp(cp_end_time));
        adminCoupon.setCp_create_time(new Timestamp(new Date().getTime()));
        adminCoupon.setCp_publish_status("未发布");

        Map<String, String> result = new HashMap<>();
        try {
            adminCouponService.saveOrUpdate(adminCoupon);
            result.put("status", "1");
            result.put("message", "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "0");
            result.put("message", "保存失败");
        }finally {
            return JSON.toJSONString(result);
        }
    }

    /**
     * 发布优惠券
     * @param request
     * @return
     */
    @RequestMapping(value = "publish")
    @ResponseBody
    public String publish(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        AdminCoupon publishCoupon = adminCouponService.findIsPublishCoupon();
        if (publishCoupon != null){
            //说明已经有发布的优惠券了，不能再发布，如需发布新的优惠券，需要将原来发布了的删除掉
            result.put("status", "0");
            result.put("message", "存在已发布优惠券，如需发布新的优惠券，请先删除旧的");
            return JSON.toJSONString(result);
        }
        int ret = adminCouponService.publishCoupon(request.getParameter("id"));
        if (ret > 0){
            result.put("status", "1");
            result.put("message", "发布成功");
            return JSON.toJSONString(result);
        }
        result.put("status", "0");
        result.put("message", "发布失败");
        return JSON.toJSONString(result);
    }

    /**
     * 撤销优惠券
     * @param request
     * @return
     */
    @RequestMapping(value = "repeal")
    @ResponseBody
    public String repeal(HttpServletRequest request) {
        int ret = adminCouponService.repealCoupon(request.getParameter("id"));
        Map<String, String> result = new HashMap<>();
        if (ret > 0){
            result.put("status", "1");
            result.put("message", "撤销成功");
            return JSON.toJSONString(result);
        }
        result.put("status", "0");
        result.put("message", "撤销失败");
        return JSON.toJSONString(result);
    }
    
    /**
	 * 用户授权
	 */
	@RequestMapping(value = "/ShouQuan", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String shouQuan(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> parameter) throws Exception {

		String backUrl = "https://www.kjscb.com/wx/adminCoupon/callBack";
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Util.APPID + "&redirect_uri="
				+ URLEncoder.encode(backUrl, "utf-8") + "&response_type=code" + "&scope=snsapi_base"
				+ "&state=1#wechat_redirect";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "success");
		jsonObject.put("url", url);

		return jsonObject.toString();
	}

	@RequestMapping(value = "/callBack")
	public String Callback(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String code = request.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Util.APPID + "&secret="
				+ Util.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
		try {
			JSONObject json = AuthUtil.doGetJson(url);
			if (json != null && json.containsKey("openid")) {
				String orderUserId = json.getString("openid");
				session.setAttribute("oppind", orderUserId);
			}
			return "weixinweb/coupon";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
