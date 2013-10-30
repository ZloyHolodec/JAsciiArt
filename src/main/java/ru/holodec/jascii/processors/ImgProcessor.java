package ru.holodec.jascii.processors;

import java.io.File;

import ru.holodec.jascii.writer.ResultWriter;

public interface ImgProcessor {
	public void processImage(File img, ResultWriter writer);
}
