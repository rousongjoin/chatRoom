package servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class CheckcodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, 
			HttpServletResponse response) throws 
			ServletException, IOException {
		//绘图
		//1,创建画布
			BufferedImage image=
					new BufferedImage(80, 30, 
							BufferedImage.TYPE_INT_RGB);
		//2,获得画笔
			Graphics g=image.getGraphics();
		//3,给画笔添加颜色
			g.setColor(new Color(225,225,225));
		//4,给画布设置背景颜色
			g.fillRect(0, 0, 80, 30);
		//5,再次给画笔添加颜色,要和画布不一样
			Random r=new Random();
			g.setColor(new Color(r.nextInt(225),
					r.nextInt(225),r.nextInt(225)));
			//设置字体的大小
			g.setFont(new Font(null, Font.BOLD, 20));
		//6,生成验证码
			String number=getNumber(5);
			//将验证码绑定到session对象上
			HttpSession session=request.getSession();
			session.setAttribute("number", number);
		//7,将验证码画到图片上
			g.drawString(number, 5, 25);
		//8,加一些干扰线
		//9,解压成图片
		//告诉浏览器,服务器返回的类型
			response.setContentType("image/jpeg");
		//因为输出的是字节数据,使用字节输出流
			OutputStream os=response.getOutputStream();
		//将内容按照指定的图片类型输出
			ImageIO.write(image, "jpeg", os);
			os.close();
	}
	//生成验证码的方法,A-Z,0-9
	public String getNumber(int size) {
		String number="";
		String chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789";
		Random ran=new Random();
		for(int i=0;i<size;i++) {
			//charAT()根据下标从字符串中找出对应字符
			number+=chars.charAt(ran.nextInt(chars.length()));
		}
		return number;
	}

}
