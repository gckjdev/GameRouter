package com.orange.game.router.service;

import javax.servlet.http.HttpServletRequest;

public class UpdateTrafficServerStatusService extends CommonGameService {

	String serverAddress;
	int serverPort;
	int language;
	int userCount;	
	
	@Override
	public void handleData() {
	}

	@Override
	public boolean needSecurityCheck() {
		return false;
	}

	@Override
	public boolean setDataFromRequest(HttpServletRequest arg0) {
		return false;
	}

}
