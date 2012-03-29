package com.orange.game.model.dao;

public class TrafficServer implements Comparable {

	public static final int LANG_CHINESE = 1;
	public static final int LANG_ENGLISH = 2;
	public static final int LANG_UNKNOWN = 0;
	
	
	String serverAddress;
	int serverPort;
	int userCount;
	int language;
	int usage;
	int capacity;
	long lastModified;
	
	public TrafficServer(String serverAddress, int port, int language, int usage, int capacity){
		super();
		this.serverAddress = serverAddress;
		this.serverPort = port;
		this.language = language;
		this.usage = usage;
		this.capacity = capacity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + language;
		result = prime * result
				+ ((serverAddress == null) ? 0 : serverAddress.hashCode());
		result = prime * result + serverPort;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrafficServer other = (TrafficServer) obj;
		if (language != other.language)
			return false;
		if (serverAddress == null) {
			if (other.serverAddress != null)
				return false;
		} else if (!serverAddress.equals(other.serverAddress))
			return false;
		if (serverPort != other.serverPort)
			return false;
		return true;
	}
	
	public String getKey(){
		return createKey(serverAddress, serverPort);
	}
	
	public static String createKey(String addr, int port) {
		return addr + ":" + port;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public int getUsage() {
		return usage;
	}

	public void setUsage(int usage) {
		this.usage = usage;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public int compareTo(Object arg0) {
		TrafficServer obj = (TrafficServer)arg0;
		return this.usage - obj.usage;
	}
	
	public void setLastModified(long modified){
		this.lastModified = modified;
	}

	public long getLastModified() {
		return this.lastModified;
	}

	@Override
	public String toString() {
		return "TrafficServer [capacity=" + capacity + ", language=" + language
				+ ", lastModified=" + lastModified + ", serverAddress="
				+ serverAddress + ", serverPort=" + serverPort + ", usage="
				+ usage + ", userCount=" + userCount + "]";
	}

	
}
