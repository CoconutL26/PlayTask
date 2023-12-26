package com.jnu.student.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jnu.student.Task;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class TaskSaver {
    public void Save(Context context, ArrayList<Task> data)
    {
        try {

            FileOutputStream dataStream=context.openFileOutput("taskData.dat",Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(dataStream);
            out.writeObject(data);
            out.close();
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @NonNull
    public ArrayList<Task> Load(Context context)
    {
        ArrayList<Task> data=new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput("taskData.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<Task>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
