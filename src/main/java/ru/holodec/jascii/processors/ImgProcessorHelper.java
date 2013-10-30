package ru.holodec.jascii.processors;

import java.io.File;
import java.util.List;

import ru.holodec.App;
import ru.holodec.jascii.writer.ResultWriter;

public class ImgProcessorHelper {
	public static final String PROCESSOR_BEAN = "ProcessorHelper";
	
	private List<String> processors;
	
	public ImgProcessorHelper() {
	}
	
	public void processImage(File fc, String chosenProcessor, String chosenWriter) {
		ImgProcessor p = (ImgProcessor)App.context.getBean(chosenProcessor);
		ResultWriter w = (ResultWriter)App.context.getBean(chosenWriter);
		p.processImage(fc, w);
	}

	public List<String> getProcessors() {
		return processors;
	}

	public void setProcessors(List<String> processors) {
		this.processors = processors;
	}
	
}
