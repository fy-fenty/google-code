package org.fengyao.filters;

import java.io.File;
import java.io.FileFilter;

public class FileExtensionFileFilter implements FileFilter {

	String extension;

	@Override
	public boolean accept(File file) {
		String IClassFileName = file.getName().toLowerCase();
		return (file.isFile() && (IClassFileName.indexOf(extension) > 0)) ? true
				: false;
	}

	public FileExtensionFileFilter(String extension) {
		super();
		this.extension = extension;
	}
}