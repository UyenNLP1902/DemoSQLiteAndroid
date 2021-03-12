package uyennlp.demo.demosqlite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;

public class DAO implements Serializable {

    public void copyFile(String src, String des) throws Exception {
        File srcF = new File(src);
        File desF = new File(des);
        FileChannel srcChannel = new FileInputStream(srcF).getChannel();
        FileChannel desChannel = new FileOutputStream(desF).getChannel();

        desChannel.transferFrom(srcChannel, 0, srcChannel.size());
    }

}
