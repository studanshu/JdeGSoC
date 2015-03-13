package com.sud.jde;

public class ListObject{
	private String objectName;
	private double objectPrice;
	private String objectCurrency;
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public double getObjectPrice() {
		return objectPrice;
	}
	public void setObjectPrice(double objectPrice) {
		this.objectPrice = objectPrice;
	}
	public String getObjectCurrency() {
		return objectCurrency;
	}
	public void setObjectCurrency(String objectCurrency) {
		this.objectCurrency = objectCurrency;
	}
	public String getToString() {
		// TODO Auto-generated method stub
		return objectName+", "+objectPrice+", "+objectCurrency;
	}
}