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
		//��ͼ
		//1,��������
			BufferedImage image=
					new BufferedImage(80, 30, 
							BufferedImage.TYPE_INT_RGB);
		//2,��û���
			Graphics g=image.getGraphics();
		//3,�����������ɫ
			g.setColor(new Color(225,225,225));
		//4,���������ñ�����ɫ
			g.fillRect(0, 0, 80, 30);
		//5,�ٴθ����������ɫ,Ҫ�ͻ�����һ��
			Random r=new Random();
			g.setColor(new Color(r.nextInt(225),
					r.nextInt(225),r.nextInt(225)));
			//��������Ĵ�С
			g.setFont(new Font(null, Font.BOLD, 20));
		//6,������֤��
			String number=getNumber(5);
			//����֤��󶨵�session������
			HttpSession session=request.getSession();
			session.setAttribute("number", number);
		//7,����֤�뻭��ͼƬ��
			g.drawString(number, 5, 25);
		//8,��һЩ������
		//9,��ѹ��ͼƬ
		//���������,���������ص�����
			response.setContentType("image/jpeg");
		//��Ϊ��������ֽ�����,ʹ���ֽ������
			OutputStream os=response.getOutputStream();
		//�����ݰ���ָ����ͼƬ�������
			ImageIO.write(image, "jpeg", os);
			os.close();
	}
	//������֤��ķ���,A-Z,0-9
	public String getNumber(int size) {
		String number="";
		String chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789";
		Random ran=new Random();
		for(int i=0;i<size;i++) {
			//charAT()�����±���ַ������ҳ���Ӧ�ַ�
			number+=chars.charAt(ran.nextInt(chars.length()));
		}
		return number;
	}

}
