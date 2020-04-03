package com.bj.scb.utils;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;
import com.bj.scb.weixin.Util;

public class QRcodeUtils {
	// 微信后台生成永久的二维码
	public static String selectImg(String accessToken) {
		String scene_str = "WenXinBJKJSCB";
		String qrPath = "C:\\Program Files/Apache Software Foundation/Tomcat 8.5/webapps/QRcode/wenxin.jpg";
		String result = createTempTicket(accessToken, scene_str);
		JSONObject jObject = JSONObject.parseObject(result);
		String ticket = jObject.getString("ticket");
		try {
			createQRCode(ticket, qrPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 返回图片的相对地址
		return "QRcode/wenxin.jpg";
	}

	// 微信用户点击获取带用户oppid的二维码
	public static String selectImgLogn(String accessToken, String openid, String logoFilePath) {
		String uuid = UUID.randomUUID().toString();
		String qrPath = "C:\\Program Files/Apache Software Foundation/Tomcat 8.5/webapps/QRcode/" + uuid + ".jpg";

		String result = createTempTicket(accessToken, openid);
		JSONObject jObject = JSONObject.parseObject(result);
		String ticket = jObject.getString("ticket");
		try {
			createQRCode(ticket, logoFilePath, qrPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 返回图片的相对地址
		return "QRcode/" + uuid + ".jpg";
	}

	// 生成不需要传用户信息的二维码图片
	private static void createQRCode(String ticket, String qrPath) throws Exception {
		// 1. 生成二维码图片
		String imgPath = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
		URL url = new URL(imgPath);
		BufferedImage image = ImageIO.read(url.openStream());
		ImageIO.write(image, "png", new File(qrPath));

	}

	// 生成包含用户头像的二维码
	private static void createQRCode(String ticket, String logoFilePath, String qrPath) throws Exception {
		// 1. 生成二维码图片
		String imgPath = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
		URL url = new URL(imgPath);
		BufferedImage image = ImageIO.read(url.openStream());
		// 2. 二维码图片中嵌入logo
		insertImage(image, logoFilePath);
		// 3. 图片写入磁盘
		ImageIO.write(image, "png", new File(qrPath));
	}

	/**
	 * 图片中嵌入图片（二维码中插入logo）
	 * 
	 * @param qrBi     二维码BufferedImage
	 * @param imgPath  logo,用户的头像
	 * @param logoSize
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage qrBi, String imgPath) throws Exception {
		// 读取嵌入的图片
		URL url = new URL(imgPath);
		Image imgSource = ImageIO.read(url.openStream());
		int width = imgSource.getWidth(null);
		int height = imgSource.getHeight(null);

		// 压缩LOGO
		if (width > 200) {
			width = 200;
		}
		if (height > 200) {
			height = 200;
		}
		Image image = imgSource.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		imgSource = image;

		// 插入LOGO
		Graphics2D graph = qrBi.createGraphics();
		int x = (qrBi.getWidth() - width) / 2;
		int y = (qrBi.getHeight() - height) / 2;
		graph.drawImage(imgSource, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	/**
	 * 获取微信永久二维码字符串
	 * 
	 * @param accessToken
	 * @param scene_str   获取人的openid
	 * @return
	 */
	private static String createTempTicket(String accessToken, String scene_str) {
		Map<String, String> map = new HashMap<>();
		map.put("scene_str", scene_str);
		Map<String, Map<String, String>> mapMap = new HashMap<>();
		mapMap.put("scene", map);
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("action_name", "QR_LIMIT_STR_SCENE");
		paramsMap.put("action_info", mapMap);
		String jsonObject = JSONObject.toJSONString(paramsMap);
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
		String result = Util.post(requestUrl, jsonObject);
		return result;
	}
}
