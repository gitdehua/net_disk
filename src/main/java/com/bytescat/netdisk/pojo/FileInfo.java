package com.bytescat.netdisk.pojo;

import java.util.Date;

public class FileInfo {
    private String name;
    private long size;
    private long lastModified;
    private boolean isDirectory;

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getSizeStr() {
        char[] units = {'\0', 'K', 'M', 'G'};
        double _size = this.size;
        int i = 0;
        while (i < units.length && _size > 1024) {
            _size /= 1024;
            i++;
        }
        return String.format("%.2f%cB", _size, units[i]);
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", lastModified=" + lastModified +
                ", isDirectory=" + isDirectory +
                '}';
    }
}
