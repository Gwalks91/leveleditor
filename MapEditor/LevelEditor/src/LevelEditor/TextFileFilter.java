package LevelEditor;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TextFileFilter extends FileFilter {
    protected String description;
    protected String[] extensions;


    public TextFileFilter(String description, String extension) {
        this(description, new String[] {extension});
    }

    public TextFileFilter(String description, String[] extensions) {
        if ((description == null) || (description.equals(""))) {
            this.description = extensions[0] + " {" + extensions.length + "}";
        } else {
            this.description = description;
        }
        this.extensions = (String[]) extensions.clone();
        toLower(this.extensions);
    }

    private void toLower(String[] extensions) {
        for (int i = 0, n = extensions.length; i < n; i++) {
            extensions[i].toLowerCase();
        }
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        } else {
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0, n = extensions.length; i < n; i++) {
                String extension = extensions[i];
                if (path.endsWith(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return description;
    }

}