package org.tender.qz;

import java.io.File;
import java.io.FileFilter;

public class FileExtensionFileFilter implements  FileFilter {       
    
    private  String extension;       
    
    public  FileExtensionFileFilter(String extension) {       
         this .extension = extension;       
   }       
    
    /*     
    * Pass the File if it has the extension.     
    */       
    public   boolean  accept(File file) {       
         // Lowercase the filename for easier comparison       
        String lCaseFilename = file.getName().toLowerCase();       
    
         return  (file.isFile() &&       
                     (lCaseFilename.indexOf(extension) >  0 )) ?  true : false ;       
   }       


}
