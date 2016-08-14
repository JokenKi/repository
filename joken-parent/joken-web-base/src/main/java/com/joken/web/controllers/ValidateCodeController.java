package com.joken.web.controllers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 验证码视图控制类
 * 
 * @author 欧阳增高
 */
@Path("ValidateCode")
public class ValidateCodeController {

	/**
	 * rose上下文
	 */
	@Autowired
	private Invocation inv;

	/**
	 * 设置字体
	 */
	private final Font mFont = new Font("Arial Black", Font.PLAIN, 15);
	/**
	 * 干扰线的长度=1.414*lineWidth
	 */
	private final int lineWidth = 1;
	/**
	 * 定义图形大小--宽度
	 */
	private final int width = 58;
	/**
	 * 定义图形大小--高度
	 */
	private final int height = 28;
	/**
	 * 随机产生干扰线数量
	 */
	private final int count = 200;

	/**
	 * 生产验证码
	 * 
	 * @author 欧阳增高
	 * @date 2016-3-15 上午11:04:57
	 */
	@Get
	public void execute() {
		HttpServletRequest request = inv.getRequest();
		HttpServletResponse response = inv.getResponse();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/gif");

		// 在内存中创建图象
		final BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		final Graphics2D g = (Graphics2D) image.getGraphics();
		// 生成随机类
		final Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(200, 250)); // ---1
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(mFont);
		// 画边框
		g.setColor(getRandColor(0, 20)); // ---2
		g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生干扰线，使图象中的认证码不易被其它程序探测到
		for (int i = 0; i < count; i++) {
			g.setColor(getRandColor(150, 200)); // ---3
			final int x = random.nextInt(width - lineWidth - 1) + 1; // 保证画在边框之内
			final int y = random.nextInt(height - lineWidth - 1) + 1;
			final int xl = random.nextInt(lineWidth);
			final int yl = random.nextInt(lineWidth);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 取随机产生的认证码(4位数字)
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			final String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 将认证码显示到图象中,调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.setColor(new Color(20 + random.nextInt(130), 20 + random
					.nextInt(130), 20 + random.nextInt(130))); // --4--50-100
			g.drawString(rand, (13 * i) + 6, 16);
		}
		// 将认证码存入SESSION
		request.getSession().setAttribute("validateCode", sRand);
		// 图象生效
		g.dispose();
		OutputStream os = null;
		try {
			os = response.getOutputStream();// 输出图象到页面
			ImageIO.write(image, "PNG", os);
		} catch (IOException e) {
			// e.printStackTrace(); //对该异常暂时不做处理，异常的原因是客户端访问验证码的时候次数太多，没有大的影响
		} finally {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 取得给定范围随机颜色
	 * 
	 * @param fc
	 *            前景色值
	 * @param bc
	 *            背景色值
	 * @return Color
	 * @author 欧阳增高
	 * @date 2016-3-15 上午11:05:17
	 */
	private Color getRandColor(int fc, int bc) {
		final Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		final int r = fc + random.nextInt(bc - fc);
		final int g = fc + random.nextInt(bc - fc);
		final int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
