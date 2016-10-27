package cn.edu.tju.bigdata.entity;

import cn.edu.tju.bigdata.util.FormMap;

public class RiskEvaluateEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private	String value;
	private String critical_discharge;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCritical_discharge() {
		return critical_discharge;
	}
	public void setCritical_discharge(String critical_discharge) {
		this.critical_discharge = critical_discharge;
	}
	
}
