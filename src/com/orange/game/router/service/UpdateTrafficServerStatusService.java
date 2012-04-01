package com.orange.game.router.service;

import javax.servlet.http.HttpServletRequest;

import com.orange.game.constants.ErrorCode;
import com.orange.game.constants.ServiceConstant;
import com.orange.game.model.manager.TrafficServerManager;

public class UpdateTrafficServerStatusService extends CommonGameService {

	String serverAddress;
	int serverPort;
	int usage;
	int language;
	int capacity;
	
	@Override
	public void handleData() {
		TrafficServerManager.getInstance().createOrUpdateTrafficServer(serverAddress, serverPort, usage, language, capacity);
	}

	@Override
	public boolean needSecurityCheck() {
		return false;
	}

	@Override
	public boolean setDataFromRequest(HttpServletRequest request) {
	
		serverAddress = request.getParameter(ServiceConstant.PARA_SERVER_ADDRESS);
		String serverPortStr = request.getParameter(ServiceConstant.PARA_SERVER_PORT);
		String usageStr = request.getParameter(ServiceConstant.PARA_SERVER_USAGE);
		String languageStr = request.getParameter(ServiceConstant.PARA_LANGUAGE);
		String capacityStr = request.getParameter(ServiceConstant.PARA_SERVER_CAPACITY);
		
		if (serverPortStr != null && !serverPortStr.isEmpty()){
			serverPort = Integer.parseInt(serverPortStr);
		}		
		else{
			log.warn("<setDataFromRequest> but port not found");
			resultCode = ErrorCode.ERROR_PARAMETER;
			return false;
		}
		
		if (serverAddress == null || serverAddress.isEmpty()){
			log.warn("<setDataFromRequest> but address not found");
			resultCode = ErrorCode.ERROR_PARAMETER;
			return false;
		}
		
		if (usageStr != null && !usageStr.isEmpty()){
			usage = Integer.parseInt(usageStr);
		}		
		else{
			log.warn("<setDataFromRequest> but usage not found");
			resultCode = ErrorCode.ERROR_PARAMETER;
			return false;
		}
		
		if (languageStr != null && !languageStr.isEmpty()){
			language = Integer.parseInt(languageStr);
		}		
		else{
			log.warn("<setDataFromRequest> but language not found");
			resultCode = ErrorCode.ERROR_PARAMETER;
			return false;
		}
		
		if (capacityStr != null && !capacityStr.isEmpty()){
			capacity = Integer.parseInt(capacityStr);
		}		
		else{
			log.warn("<setDataFromRequest> but capacity not found");
			return false;
		}
		
		return true;	
	}

}
