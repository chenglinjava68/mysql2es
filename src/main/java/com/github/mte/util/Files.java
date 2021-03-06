package com.github.mte.util;

import com.github.mte.model.Const;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Files {

    private static String getFileName(String index, String type) {
        return U.addSuffix(Const.TMP) + index + "-" + type;
    }

    public static String read(String index, String type) {
        String fileName = getFileName(index, type);
        try {
            return com.google.common.io.Files.toString(new File(fileName), U.UTF8);
        } catch (FileNotFoundException e) {
            if (Logs.ROOT_LOG.isDebugEnabled())
                Logs.ROOT_LOG.debug("no file ({})", fileName);
        } catch (IOException e) {
            if (Logs.ROOT_LOG.isInfoEnabled())
                Logs.ROOT_LOG.info(String.format("read from file(%s) exception", fileName), e);
        }
        return U.EMPTY;
    }

    public static boolean write(String index, String type, String content) {
        String fileName = getFileName(index, type);
        try {
            File file = new File(fileName);
            com.google.common.io.Files.write(content, file, U.UTF8);
            return true;
        } catch (IOException e) {
            if (Logs.ROOT_LOG.isInfoEnabled())
                Logs.ROOT_LOG.info(String.format("write to file(%s) exception", fileName), e);
            return false;
        }
    }

    public static boolean delete(String index, String type) {
        String fileName = getFileName(index, type);
        boolean flag = new File(fileName).delete();
        if (Logs.ROOT_LOG.isInfoEnabled())
            Logs.ROOT_LOG.info("delete ({}) {}", fileName, flag);

        return flag;
    }
}
