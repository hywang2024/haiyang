package com.haiyang.tomcat.service;


import com.haiyang.tomcat.http.HYRequest;
import com.haiyang.tomcat.http.HYResponse;
import com.haiyang.tomcat.http.HYServlet;

public class SecondServlet extends HYServlet {

	@Override
	public void doGet(HYRequest request, HYResponse response) throws Exception {
		this.doPost(request, response);
	}

	@Override
	public void doPost(HYRequest request, HYResponse response) throws Exception {
		response.write("This is Second Serlvet");
	}

}
