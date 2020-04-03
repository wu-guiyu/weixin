package com.bj.scb.controller;
/**
 * 海报与二维码controller
 * @author wuguiyu
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bj.scb.pojo.CustomerInfo;
import com.bj.scb.pojo.Share;
import com.bj.scb.service.CustomerInfoService;
import com.bj.scb.service.ShareService;
import com.bj.scb.utils.QRcodeUtils;
import com.bj.scb.weixin.AuthUtil;
import com.bj.scb.weixin.Util;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@Controller
@RequestMapping(value = "share")
public class ShareController {
	
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private ShareService shareService;

	/**
	 * 永久二维码
	 */
	@RequestMapping(value = "/selectImg")
	@ResponseBody
	public String selectImg() {
		JSONObject jsonObject = new JSONObject();
		String st = QRcodeUtils.selectImg(Util.getAccessToken());
		jsonObject.put("message", 200);
		jsonObject.put("code", "https://www.kjscb.com/" + st);
		return jsonObject.toString();
	}

	/**
	 * 分享海报的二维码
	 */
	@RequestMapping(value = "/selectImgLogo")
	@ResponseBody
	public String selectImgLogo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramter) {
		String opind=paramter.get("opind");
		JSONObject jsonObject = new JSONObject();

		Share s = shareService.getByOpind(opind);
		if (s != null) {
			jsonObject.put("message", 200);
			jsonObject.put("logoPath", s.getCustomerPic());
			jsonObject.put("hb", "https://www.kjscb.com/QRcode/hb.png");
			jsonObject.put("name", s.getCustomerNickName());
			jsonObject.put("cont", s.getShareCount());
			jsonObject.put("QRcode", s.getCustomerQRcode());
		}else {
			// 根据opind查询用户信息
			CustomerInfo customerInfo = customerInfoService.selectNickName(opind);
			String logoFilePath = customerInfo.getCustomerPic();
			Share share=new Share();
			String st = QRcodeUtils.selectImgLogn(Util.getAccessToken(), opind, logoFilePath);
			share.setCustomerNickName(customerInfo.getCustomerNickName());
			share.setCustomerPic(logoFilePath);
			share.setCustomerId(opind);
			share.setShareCount(0);
			jsonObject.put("hb", "https://www.kjscb.com/QRcode/hb.png");
			share.setCustomerQRcode("https://www.kjscb.com/" + st);
			share.setInsertTime((new Date()).getTime());
			
			shareService.save(share);
			
			jsonObject.put("message", 200);
			jsonObject.put("cont", 0);
			jsonObject.put("logoPath", logoFilePath);
			jsonObject.put("name", customerInfo.getCustomerNickName());
			jsonObject.put("QRcode", "https://www.kjscb.com/" + st);
		}
		
		return jsonObject.toString();
	}

	/**
	 * 用户授权
	 */
	@RequestMapping(value = "/haibaoShouQuan", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String shouQuan(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> parameter) throws Exception {

		String backUrl = "https://www.kjscb.com/wx/share/callBack";
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
			return "my/myfenxiang";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/*
	 * 上传海报
	 */
	@RequestMapping(value = "/upimg", produces = "application/json; charset=utf-8")
	public @ResponseBody String uploadImg2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		File imageFileParentPath = getFile(request);
		File imageFile = new File(imageFileParentPath.getAbsolutePath() + "\\hb.png");
		String resultStr = request.getParameter("image").toString();// 前端传来的是压缩后的并用base64编码后的字符串
		resultStr = resultStr.substring(resultStr.indexOf(",") + 1);// 需要去掉头部信息，这很重要
		FileOutputStream outputStream = new FileOutputStream(imageFile);// 创建输出流
		BASE64Decoder base64Decoder = new BASE64Decoder();// 获得一个图片文件流，我这里是从flex中传过来的
		byte[] result = base64Decoder.decodeBuffer(resultStr);// 解码
		for (int i = 0; i < result.length; ++i) {
			if (result[i] < 0) {// 调整异常数据
				result[i] += 256;
			}
		}
		outputStream.write(result);
		outputStream.flush();
		outputStream.close();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mse", "success");
		
		return jsonObject.toString();
	}
	
	/**
	 * @category 获取上传图片的路径及文件对象，图片统一放在QRcode
	 * @param request
	 * @return
	 */
	public File getFile(HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath("");
		String contextPath = request.getContextPath();
		String contextPathReplace = contextPath.replaceAll("/", "");
		String rootPathNew = rootPath.replace("\\" + contextPathReplace, "");

		File imageFileParentPath = new File(rootPathNew + "QRcode\\");
		if (!imageFileParentPath.exists()) {
			imageFileParentPath.mkdirs();
		}
		return imageFileParentPath;
	}

}
