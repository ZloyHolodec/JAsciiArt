package ru.holodec.jascii.processors;

import java.io.File;

public class ImgProcessorHelper {
	public static final String PROCESSOR_BEAN = "ImgProcessor";
	
	private ImgProcessor processor;
	
	public ImgProcessorHelper() {
	}
	
	public void processImage(File fc) {
		processor.processImage(fc);
	}

	public ImgProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(ImgProcessor processor) {
		this.processor = processor;
	}
	
}
