import java.util.*;
import java.lang.*;
import java.math.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.*;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.JSpinner.ListEditor;
import java.nio.file.attribute.FileTime;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.lang.System.in;
import static java.lang.System.out;
import static java.util.Arrays.sort;


 class  Sınıf {
	 private static int REC_SIZE=48;
	 private static int AD_SIZE=7;
	 private static int SOY_SIZE=10;
	 private static int TEL_SIZE=5;
	 private static long kNo=0;
	 private static String Ad,Soyad,Telefon;	 
	 static  RandomAccessFile dosya;
	 static Scanner input=new Scanner(System.in);
    public static void main(String[] args) throws IOException {
    	dosya=new RandomAccessFile("D:\\Deniz.dat","rw");
    	char ch; 
    	do {
    		System.out.printf("[1]-Kayıt Ekle \n");
        	System.out.printf("[2]-Kayıt Listele \n");
        	System.out.printf("[3]-Çıkış \n");
        	ch=input.next().charAt(0);
        	switch(ch) {
        	case '1':kayıtEkle(); break;
        	case '2':kayıtListele(dosya); break;
        	case '3':break;
        	}
    	}
    	while(ch!='3');
    	dosya.close();
    }
    	public static void kayıtEkle()throws IOException{ //Kayıt Ekle
    		dosya=new RandomAccessFile("D:\\Deniz.dat","rw");
    		String cevap= "e";
    	do {
    		kNo++;
    		System.out.print(kNo+".kayıt\n");
    		input.nextLine();
    		System.out.print("Ad:\n");
    		Ad=input.nextLine();
    		System.out.print("Soyad\n");
    		Soyad=input.nextLine();
    		System.out.print("Telefon:\n");
    		Telefon=input.nextLine();   
    		writeRecord(dosya);
    		System.out.print("\nDevam Etmek istiyor musunuz? (e/h)? ");
    		cevap=input.next();
    	}
    	while (cevap.equals("e")||cevap.equals("E"));
    	System.out.println();
    }
    	public static void writeRecord(RandomAccessFile dosy) throws IOException{ //Kaydın Başlangıç Adresi
    		long dosyPos=(kNo-1)* REC_SIZE;
    		dosy.seek(dosyPos); //Dosya Göstericisi
    		dosy.writeLong(kNo);
    		writeString(dosy,Ad,AD_SIZE);
    		writeString(dosy,Soyad,SOY_SIZE);
    		writeString(dosy,Telefon,TEL_SIZE);
    	}
    	public static void writeString(RandomAccessFile dosy, String text, int fixedSize) throws IOException{ //Kayıt Yaz
    		int size=text.length();
    		if(size<=fixedSize) {
    			dosy.writeChars(text);
    			for(int i=size; i<fixedSize; i++)
    				dosy.writeChar(' ');
    		}
    		else //String çok uzunsa
    			dosy.writeChars(text.substring(0,fixedSize));
    	}
    	public static void kayıtListele(RandomAccessFile dosy) throws IOException { //Kayıt Listele
    		long numRecords=dosy.length()/REC_SIZE;
    		dosy.seek(0);  //Dosya imleci başlangıç yeri
    		System.out.printf("------------------------------\n");
    		for(int i=0; i<numRecords; i++) {
    			kNo=dosy.readLong();
    			Ad=readString(dosy, AD_SIZE);
    			Soyad=readString(dosy, SOY_SIZE);
    			Telefon=readString(dosy, TEL_SIZE);
    			System.out.println(kNo+" "+Ad+"\t"+Soyad+"\t"+Telefon);
    		}
    		System.out.print("------------------------------\n");
    	}
    	public static String readString(RandomAccessFile dosy, int fixedSize) throws IOException {	//Kayıt oku
    		String value="";
    		for(int i=0; i<fixedSize; i++)
    			value+=dosy.readChar();
    		return value;
    	}
}
