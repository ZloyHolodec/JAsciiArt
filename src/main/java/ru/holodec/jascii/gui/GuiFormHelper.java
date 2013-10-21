package ru.holodec.jascii.gui;

public class GuiFormHelper {
	
	public static final String PRESENTER_BEAN = "GuiPresenter";
	
	StatusPresenter presenter;
	
	public GuiFormHelper() {
	}

	public StatusPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(StatusPresenter presenter) {
		this.presenter = presenter;
	}

	
}
