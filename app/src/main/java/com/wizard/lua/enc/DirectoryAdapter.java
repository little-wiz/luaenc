package com.wizard.lua.enc;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryAdapter extends BaseAdapter {
    private File directory;
    private FileItem[] items;
    public DirectoryAdapter(File initialDirectory) {
        setDirectory(initialDirectory);
    }

    public void setDirectory(File directory){
        if(!directory.exists())
            return;
        this.directory = directory;
        File[] f = directory.listFiles();
        items = new FileItem[f.length + 1];
        items[1] = FileItem.PARENT;
        for (int i = 0; i < f.length; i++) {
            items[i + 1] = new FileItem(f[i].isFile(), f[i].getName());
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, null);
        }
        ((TextView) convertView.findViewById(R.id.file_name)).setText(items[position].getFileName());
        ((ImageView) convertView.findViewById(R.id.file_type)).setImageResource(
                items[position].isFile() ? R.drawable.file : R.drawable.folder
        );
        return convertView;
    }

    private static class FileItem{
        public static final FileItem PARENT = new FileItem(false, "../");
        private final boolean isFile;
        private final String fileName;
        public FileItem(boolean isFile, String fileName){
            this.isFile = isFile;
            this.fileName = fileName;
        }

        public boolean isFile() {
            return isFile;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
