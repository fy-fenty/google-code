package org.fengyao.filters;

import java.io.File;
import java.io.FileFilter;

public class FileExtensionFileFilter implements FileFilter {

	private String extension;

	public FileExtensionFileFilter(String extension) {
		this.extension = extension;
	}

	@Override
	public boolean accept(File file) {
		String lCaseFilename = file.getName().toLowerCase();
		return (file.isFile() && (lCaseFilename.indexOf(extension) > 0)) ? true : false;
	}
}
