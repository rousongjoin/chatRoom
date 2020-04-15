package test;

import Impl.LogServiceImpl;
import entity.Log;
import service.LogService;

public class testAddLog {
	public static void main(String[] args) throws Exception {
		Log log =new Log();
		log.setName("123");
		log.setLogid("4");
		log.setDetail("ÐÞ¸ÄÃÜÂë");
		log.setLogtype("¸ü¸Ä");
		log.setTime("1970-1-1");
		log.setIp(null);
		LogService ls=new LogServiceImpl();
		ls.add(log);
		
		System.out.println(1);
		
}
}