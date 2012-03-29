package com.orange.game.router.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.orange.game.constants.ErrorCode;
import com.orange.game.constants.ServiceConstant;
import com.orange.game.model.dao.TrafficServer;
import com.orange.game.model.manager.TrafficServerManager;

public class GetTrafficServerService extends CommonGameService {

	int language = TrafficServer.LANG_UNKNOWN;	// optional, 1 Chinese, 2 English	

	// test example : http://192.168.1.198:8600/api/?m=gt
	
	@Override
	public boolean setDataFromRequest(HttpServletRequest request) {
		String languageStr = request.getParameter(ServiceConstant.PARA_LANGUAGE);
		if (languageStr != null && !languageStr.isEmpty()){
			language = Integer.parseInt(languageStr);
		}		
		return true;
	}

	@Override
	public boolean needSecurityCheck() {
		return false;
	}

	@Override
	public void handleData() {				
		List<TrafficServer> serverList = TrafficServerManager.getInstance().findAllServers();
		resultCode = ErrorCode.ERROR_SUCCESS;
		resultData = CommonServiceUtils.serverListToJSON(serverList);
	}

}
