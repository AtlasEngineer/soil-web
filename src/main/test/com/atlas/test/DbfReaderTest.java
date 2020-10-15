package com.atlas.test;

import com.jfinal.kit.Kv;
import com.linuxense.javadbf.DBFReader;
import com.soli.server.utils.IssueShpUtils;
import com.soli.server.utils.IssueTiffUtils;

import java.io.File;

public class DbfReaderTest {

    public static void main(String[] args) {
        Kv aaaaaa = IssueTiffUtils.createSldByDbf(new File("C:\\Users\\xiaoxu\\Desktop\\trwd\\ntsz\\ntszwgs84.tif.vat.dbf"), "aaaaaa");

    }
}