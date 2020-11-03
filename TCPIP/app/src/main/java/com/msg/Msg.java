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
//	 serialVersionUID�� ��ߵȴ�
	// �ݵ�� Serializable�� implements�ؾ� tcp/ip ����� �� �ִ�
	private HashMap<String, Msg> maps;
	private ArrayList<String> ips; // ��ü�� ����ȭ(serializable �� �Ǿ����� ������ stream ��� �Ұ���)
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
