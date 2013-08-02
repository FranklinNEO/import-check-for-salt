package com.redinfo.importcheck.datamodel;

public class AreaInfo {
	private String Name;
	private String Code;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.Name;
	}
}
