package tgis.common.util.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

public class EncryptSDK {

    private static byte [] enckey = null;
    private final static String checkHeadBlock   = "--START CIPHER--";
    private final static String checkFooterBlock = "--E N D CIPHER--";

    private static ARIAEngine engine ;
    static{
        try{
            engine = new ARIAEngine(128);
        }catch(Exception e){
//            System.out.println("aria engine init error");
            e.printStackTrace();
        }
    }

    private EncryptSDK() throws InvalidKeyException{
    }

    private static void setKey(String key) throws InvalidKeyException{

        enckey = key.getBytes();
        ByteBuffer buffer = new ByteBuffer();
        buffer.append(key);
        for(int i = enckey.length ; i < 128 ; i++){
            buffer.append(0);
        }
        engine.setKey(buffer.toBytes());
        engine.setupRoundKeys();
    }


    private static byte[] makeHeader( int orgLength) throws InvalidKeyException{
        ByteBuffer rtn = new ByteBuffer();
        /* 1st 16-byte block ==> header string: */
        rtn.append(checkHeadBlock.getBytes());

        /* 2st 16-byte block ==> length : */
        rtn.append(  int2byte(orgLength) );
        Date date = new Date();
        Random rand = new Random(date.getTime());

        for (int i = 4; i < 16; ++i)
            rtn.append( rand.nextInt() );
        return rtn.toBytes();
    }
    private static final byte[] int2byte(int i) {
        byte dest[] = new byte[4];
        dest[3] = (byte) (i & 0xff);
        dest[2] = (byte) (i >>> 8 & 0xff);
        dest[1] = (byte) (i >>> 16 & 0xff);
        dest[0] = (byte) (i >>> 24 & 0xff);
        return dest;
    }
    private static final int getint(byte src[], int offset) {
        return (src[offset] & 0xff) << 24 | (src[offset + 1] & 0xff) << 16 | (src[offset + 2] & 0xff) << 8 | src[offset + 3] & 0xff;
    }
    protected static byte [] encrypt(byte[] plain) throws InvalidKeyException{
        ByteBuffer orgBuffer = new ByteBuffer();
        orgBuffer.append(plain);
        ByteBuffer outBuffer = new ByteBuffer();
        byte [] header =  makeHeader( plain.length);
        outBuffer.append(  header );// 헤더는 암호화 안함.
        int nPlainBytes = plain.length;


        int nBlockNum = (nPlainBytes % 16 == 0) ? (nPlainBytes / 16) : (nPlainBytes / 16 + 1);
        for (int i = 0; i < (16-nPlainBytes % 16); i++) {
            orgBuffer.append(0);
        }
        plain = orgBuffer.toBytes();
        for (int i = 0; i < nBlockNum ; i++) {
            outBuffer.append( engine.encrypt( plain , (  i * 16 ) ) );
        }
        outBuffer.append(  checkFooterBlock.getBytes() );// 헤더는 암호화 안함.
        return outBuffer.toBytes();
    }

    protected final static byte[] decrypt(byte[] encdataorg) throws InvalidKeyException ,SecurityException {

        byte [] encdata = checkSum(encdataorg);
        int dataLength =  getint(encdataorg,16); // 데이터 length

        ByteBuffer orgBuffer = new ByteBuffer();
        orgBuffer.append(encdata);
        ByteBuffer outBuffer = new ByteBuffer();
        int nPlainBytes = encdata.length;
        int nBlockNum = (nPlainBytes % 16 == 0) ? (nPlainBytes / 16) : (nPlainBytes / 16 + 1);
        for (int i = 0; i < (16-nPlainBytes % 16); i++) {
            orgBuffer.append(0);
        }
        encdata = orgBuffer.toBytes();
        for (int i = 0; i < nBlockNum ; i++) {
            outBuffer.append( engine.decrypt(encdata, (i*16)) );
        }

        byte [] rtn = new byte[dataLength];
        outBuffer.getBytes(0,  dataLength, rtn, 0);
        return rtn;
    }


    private static byte[] checkSum(byte[] org) throws SecurityException{
    	// 헤더 check
        byte [] encCallHeader = new byte[16];
        System.arraycopy(org, 0, encCallHeader, 0, 16);
        if( ! checkHeadBlock.equals( new String(encCallHeader) )){
            throw new SecurityException("This Data is not Encrypted");
        }
     // 푸터 check
        encCallHeader = new byte[16];
        System.arraycopy(org, org.length - 16 , encCallHeader, 0, 16);
        if( ! checkFooterBlock.equals( new String(encCallHeader) )){
            throw new SecurityException("This Data is not Encrypted");
        }

        byte [] encdata = new byte [org.length-32-16];
        System.arraycopy(org, 32, encdata, 0, encdata.length);
        return encdata;

    }






    public static String encData(String str, String key) throws InvalidKeyException, UnsupportedEncodingException{
        setKey(key);
        byte [] data = encrypt(str.getBytes());
//        return new String(data,"8859_1").replace("--START CIPHER--","").replace("--E N D CIPHER--","");
        return new String(Base64.encodeBase64String(data));
    }

    public static String decrypt(String str, String key) throws InvalidKeyException, UnsupportedEncodingException{
        setKey(key);
//        byte [] data = decrypt(str.getBytes("8859_1"));
        byte [] data = decrypt(Base64.decodeBase64(str));
        return new String(data);
    }


    public static void memToFileEnc(byte [] dataorg, String targetPath, String targetFile , String key) throws InvalidKeyException, SecurityException, IOException{
        setKey(key);
        byte [] data = encrypt(dataorg);
        File f = new File(targetPath);
        if(!f.exists()) f.mkdirs();
        FileOutputStream fout = new FileOutputStream(targetPath+File.separator+targetFile);
        fout.write(data);
        fout.close();

    }
    public static byte[] FileToMemDec(String targetPath, String targetFile, String key) throws IOException, InvalidKeyException{
        FileInputStream fin = new FileInputStream(targetPath+File.separator+targetFile);
        ByteBuffer rtn = new ByteBuffer();
        int i = -1;
        while( (i = fin.read()) != -1 ){
             rtn.append((byte)i);
        }
        setKey(key);
        byte [] data = decrypt(rtn.toBytes());
        return data;
    }
    public static void FileToFileEnc(String srcPath, String srcFile, String targetPath, String targetFile, String key) throws IOException, InvalidKeyException, SecurityException{
        FileInputStream fin = new FileInputStream(srcPath+File.separator+srcFile);
        ByteBuffer src = new ByteBuffer();
        int i = -1;
        while( (i = fin.read()) != -1 ){
             src.append((byte)i);
        }
        memToFileEnc(src.toBytes(), targetPath, targetFile, key);
    }
    public static void FileToFileDec(String srcPath, String srcFile, String targetPath, String targetFile, String key) throws InvalidKeyException, IOException{
        byte[] desc = FileToMemDec(srcPath, srcFile, key);
        FileOutputStream fout = new FileOutputStream(targetPath+File.separator+targetFile);
        fout.write(desc);
        fout.close();

    }

}
