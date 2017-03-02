package net.platform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * 描述   文件夹打包压缩成zip格式
 * @author 
 * @created 2015-4-14 下午02:39:03
 */
public class ZipUtils {
    /**
     * 
     * 描述
     * @author 
     * @created 2015-4-14 下午02:39:19
     */
    private ZipUtils(){};
    /**
     * 
     * 描述  创建ZIP文件
     * @author 
     * @created 2015-4-14 下午02:39:39
     * @param sourcePath
     * @param zipPath
     */
    public static void createZip(String sourcePath, String zipPath) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            writeZip(new File(sourcePath), "", zos);
        } catch (FileNotFoundException e) {
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 
     * 描述 写zip文件
     * @author 
     * @created 2015-4-14 下午02:41:25
     * @param file
     * @param parentPath
     * @param zos
     */
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if(file.exists()){
            if(file.isDirectory()){//处理文件夹
                parentPath+=file.getName()+File.separator;
                File [] files=file.listFiles();
                for(File f:files){
                    writeZip(f, parentPath, zos);
                }
            }else{
                FileInputStream fis=null;
                try {
                    fis=new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte [] content=new byte[1024];
                    int len;
                    while((len=fis.read(content))!=-1){
                        zos.write(content,0,len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }finally{
                    try {
                        if(fis!=null){
                            fis.close();
                        }
                    }catch(IOException e){
                    }
                }
            }
        }
    } 
    
    /**
     * 
     * 描述  指定文件创建ZIP文件
     * @author 
     * @created 2015-4-14 下午02:39:39
     * @param sourcePath
     * @param zipPath
     */
    public static void createZipByName(String sourcePath, String zipPath,String [] names) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            writeZipByName(sourcePath, sourcePath.substring(sourcePath.lastIndexOf(File.separator)+1), zos,names);
        } catch (FileNotFoundException e) {
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 
     * 描述 目录下指定文件写zip文件
     * @author 
     * @created 2015-4-14 下午02:41:25
     * @param file
     * @param parentPath
     * @param zos
     */
    private static void writeZipByName(String sourcePath, String parentPath, ZipOutputStream zos,String [] names) {
        
        for(int i=0;i<names.length;i++){
            File file=new File(sourcePath+File.separator+names[i]);
            writeZip(file, parentPath, zos);
        }        
    } 
    
    
    
    public static void main(String[] args) {
        long begintime=System.currentTimeMillis();
//        ZipUtils.createZip("E:\\temp\\5b2e97da3ee1455cb5544d3edeb96038", "E:\\temp\\123.zip");
        
        String[] names={"SATE_L2_MTR2_VISSR_MWB_LBT_SEC_LCN-IR3-20150414-0532.AWX","SATE_L2_MTR2_VISSR_MWB_LBT_SEC_LCN-VIS-20150414-0532.AWX"};
        ZipUtils.createZipByName("E:\\temp\\5b2e97da3ee1455cb5544d3edeb96038", "E:\\temp\\2222.zip",names);
        
        System.out.println("打包时间"+(System.currentTimeMillis()-begintime));
    }
}
