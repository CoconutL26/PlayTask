package com.jnu.student.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jnu.student.Award;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataSaver {
    public void Save(Context context, ArrayList<Award> data)
    {
        try {

            FileOutputStream dataStream=context.openFileOutput("mydata.dat",Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(dataStream);
            out.writeObject(data);
            out.close();
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @NonNull
    public ArrayList<Award> Load(Context context)
    {
        ArrayList<Award> data=new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput("mydata.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<Award>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
