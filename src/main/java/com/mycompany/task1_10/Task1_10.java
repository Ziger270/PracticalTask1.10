package com.mycompany.task1_10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author aleksandrbisirov
 */
public class Task1_10 {
    public static byte[] hexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length () ;
        if (len % 2 == 1) {
            throw new IllegalArgumentException ("Error hexstring");
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ( (Character.digit (s.charAt (i), 16) << 4)
                + Character.digit (s.charAt (i+1), 16)) ;
        }
        return data;
    }
    
    public static byte[] readBytesFromFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] file_bytes = new byte[fis.available()];
            fis.read(file_bytes);
            fis.close();
            return file_bytes;
        }
        catch (IOException ex) {
            System.out.println("Error! " + ex.toString());
            return null;
        }
    }
    
    public static byte[] encrypt(byte[] message, byte[] key) {
        for (int i = 0, key_it = 0; i < message.length; i++) {
            message[i] ^= key[key_it];
            if (key_it+1 == key.length) key_it = 0;
            else key_it++;
        }
        return message;
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Вариант 3. Бисиров Александр Васильевич.");
        File file = null;
        byte[] fileBytes = null;
        do {
            System.out.print("Укажите путь к файлу: ");
            var path = scan.nextLine();
            file = new File(path);
            if (!file.exists()){
                System.out.println("ОШИБКА!!!1! Данного файла не существует!");
            }
            fileBytes = readBytesFromFile(file);
        } while(!file.exists() || !file.isFile() || fileBytes == null);

        byte[] gammaBytes = null;
        do {
            System.out.print("Введите байты гаммы: ");
            var strBytes = scan.nextLine();
            try {
                gammaBytes = hexStringToByteArray(strBytes);
            }
            catch(IllegalArgumentException ex){
                gammaBytes = null;
                System.out.println("АБШИБКА!!1!! Неверный формат ввода!");
            }
        } while(gammaBytes == null);
        
        try {
            FileOutputStream fos = new FileOutputStream(file.getParent()+"\\encoded_file.txt");
            var temp = encrypt(fileBytes, gammaBytes);
            fos.write(temp);
            System.out.println("Файл успешно сохранен");
        }
        catch (IOException ex) {
            System.out.println("Ошибка записи в файл.");
        }
    }
}
