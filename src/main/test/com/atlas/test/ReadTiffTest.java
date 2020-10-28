package com.atlas.test;

import com.jfinal.kit.Kv;
import com.soli.server.utils.IssueTiffUtils;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class ReadTiffTest {

    @Test
    public void test(){
        Kv aaaa = IssueTiffUtils.createSld(new File("C:\\Users\\xiaoxu\\Desktop\\trwd\\GST_7E3790.tif"), "aaaa");

    }

}
