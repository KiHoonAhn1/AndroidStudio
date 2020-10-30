package com.msg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Msg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// serialVersionUID를 줘야된다
	// 반드시 Serializable을 implements해야 tcp/ip 통과할 수 있다
	private HashMap<String, Msg> maps;
	private ArrayList<String> ips; // 객체의 직렬화(serializable 이 되어있지 않으면 stream 통과 불가능)
//	private String ip;
	private String id;
	private String msg;
	
	
	
	public Msg() {
	}
	
	public Msg(String msg) {
		this.msg = msg;
	}

	public Msg(String id, String msg) {
		this.id = id;
		this.msg = msg;
	}

	public Msg(HashMap<String, Msg> maps, ArrayList<String> ips, String id, String msg) {
		this.maps = maps;
		this.ips = ips;
		this.id = id;
		this.msg = msg;
	}

	public Msg(ArrayList<String> ips, String id, String msg) {
		this.ips = ips;
		this.id = id;
		this.msg = msg;
	}

	public HashMap<String, Msg> getMaps() {
		return maps;
	}

	public void setMaps(HashMap<String, Msg> maps) {
		this.maps = maps;
	}

	public ArrayList<String> getIps() {
		return ips;
	}

	public void setIps(ArrayList<String> ips) {
		this.ips = ips;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
}
