package ru.holodec.jascii.processors;

public class AcceptedSymbols {
	public static final String BEAN_NAME = "AcceptedSymbols";
	
	//setup from spring
	private String symbols;
	
	
	public String getSymbols() {
		return symbols;
	}


	public void setSymbols(String symbols) {
		this.symbols = symbols;
	}


	public AcceptedSymbols() {}

}
