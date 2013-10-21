package ru.holodec.jascii.writer;

public class ResultWriterHelper {
	public static final String WRITER_BEAN = "WriterHelper";
	
	private ResultWriter writer;
	
	public ResultWriter getWriter() {
		return writer;
	}

	public void setWriter(ResultWriter writer) {
		this.writer = writer;
	}

	public ResultWriterHelper() {}
}
