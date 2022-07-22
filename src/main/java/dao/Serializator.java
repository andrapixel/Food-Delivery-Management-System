package dao;
import bll.DeliveryService;

import java.io.*;

public class Serializator<T> implements Serializable {

    public void serializeObject(T objectToSerialize, String filePath) {
        try {
            FileOutputStream outputFile = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputFile);
            objectOutputStream.writeObject(objectToSerialize);

            objectOutputStream.close();
            outputFile.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DeliveryService deserializeObject(String filePath) throws IOException, ClassNotFoundException {
        DeliveryService objectToDeserialize = null;

        try {
            FileInputStream inputFile = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputFile);
            objectToDeserialize = (DeliveryService) objectInputStream.readObject();

            objectInputStream.close();
            inputFile.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return objectToDeserialize;
    }
}
