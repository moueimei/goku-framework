package com.gkframework.qos.server.agent.utils;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ObjectToFileUtils {


	public static void writeObject(Object object, String filePath) {
		try {
            FileOutputStream outStream = new FileOutputStream(filePath);
			@Cleanup ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
            objectOutputStream.writeObject(object);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
    }

    public static Object readObject(String filePath){
    	FileInputStream freader;
    	try {
    		freader = new FileInputStream(filePath);
			@Cleanup ObjectInputStream objectInputStream = new ObjectInputStream(freader);
            return objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		}
    	return null;
    }  
      
}
