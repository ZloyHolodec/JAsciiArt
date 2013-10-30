package ru.holodec.jascii.writer;

import java.util.List;

public class ResultWriterHelper {
	public static final String WRITER_BEAN = "WriterHelper";
	
	private  List<String>  writers;
	
	public List<String> getWriters() {
		return writers;
	}

	public void setWriters(List<String> writers) {
		this.writers = writers;
	}
	
	public ResultWriterHelper() {}
}
