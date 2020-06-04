package com.ices.simulation.cyf.utils;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class generateUtils {
    /*
      包含数字抽取 单词抽取
     */
    public static List<Integer> extract(String str) {
        String[] numbers = str.split(",");
        List<Integer> ret = new ArrayList<Integer>();
        for (String number : numbers) {
            int aNumber = Integer.parseInt(number);
            ret.add(aNumber);
        }
        return ret;
    }

    public static List<String> extractString(String str) {
        String[] stringList = str.split(",");
        List<String> ret = new ArrayList<>();
        for (String s : stringList) {
            ret.add(s);
        }
        return ret;
    }

    //替换字符串中的'？'
    public static String replaceChar(String source, List<String> newStr) {
        String s = source;
        for (String str : newStr) {
            s = s.replaceFirst("\\?", str);
        }
        return s;
    }

    public static List<String> getTwoWord(String str) {
        String[] stringList = str.split("_");
        List<String> ret = new ArrayList<>();
        for (String s : stringList) {
            ret.add(s);
        }
        return ret;

    }

    public static List<Integer> getTwoNum(String str) {
        String[] stringList = str.split("_");
        List<Integer> ret = new ArrayList<>();
        for (String s : stringList) {
            ret.add(Integer.parseInt(s));
        }
        return ret;

    }


    public static String getWordsFromName(String interactionName) {
        String[] words = interactionName.split("_");
        StringBuilder ret = new StringBuilder(words[0].toLowerCase());
        int length = words.length;
        if (length > 0) {
            for (int i = 1; i < length; ++i) {
                ret.append(generateUtils.InitialCapital(words[i].toLowerCase()));
            }
        }
        return ret.toString();
    }

    //生成handle名称
    public static String interactionNameConvert(String interactionName) {
        String ret = getWordsFromName(interactionName);
        return ret + "InteractionHandler";
    }

    //生成send交互类名称
    public static String getSendInteractionName(String interactionName) {
        String ret = generateUtils.InitialCapital(getWordsFromName(interactionName));
        return "send" + ret + "Interaction";
    }

    public static String getSendParameterName(String interactionName) {
        return generateUtils.InitialCapital(getWordsFromName(interactionName));
    }

    /*
        联邦正式名称生成
     */
    public static String InitialCapital(String federateName) {
        return federateName.substring(0, 1).toUpperCase().concat(federateName.substring(1));
    }

    public static String InitialLowercase(String federateName) {
        return federateName.substring(0, 1).toLowerCase().concat(federateName.substring(1));
    }

    public static String generateStartingPoint(String federateName) {
        return InitialCapital(federateName) + "StartingPoint";
    }

    public static String generateBIGAmbassadorName(String federateName) {
        return federateName.toUpperCase() + "_AMBASSADOR_NAME";
    }

    public static String generateBIGFederateName(String federateName) {
        return federateName.toUpperCase() + "_FEDERATE_NAME";
    }

    public static String generateSmallAmbassadorName(String federateName) {
        return InitialCapital(federateName) + "FederateAmbassador";
    }

    public static String generateSmallFederateName(String federateName) {
        return InitialCapital(federateName) + "Federate";
    }

    //创建路径
    public static String creteFedPath(String fileName) {
        String str = "src\\main\\java\\com\\example\\elema\\cyf\\generate\\" + fileName + ".txt";
        return str;
    }

    public static String creteCommonsPath(String fileName) {
        String str = "src\\main\\java\\com\\example\\elema\\cyf\\generate\\Commons\\" + fileName + ".txt";
        return str;
    }

    public static String creteFederatePath(String fileName) {
        String str = "src\\main\\java\\com\\example\\elema\\cyf\\generate\\Federates\\" + fileName + ".txt";
        return str;
    }

    public static String creteAbstractPath(String fileName) {
        String str = "src\\main\\java\\com\\example\\elema\\cyf\\generate\\Abstract\\" + fileName + ".txt";
        return str;
    }

    //创建文件
    public static void createNewFile(String filePath) throws IOException {
        //String str=cretePath(fileName);
        File f = new File(filePath);
        if (f.exists()) {
            System.out.print("文件存在");
        } else {
            boolean bool = f.createNewFile();
            System.out.println(bool);
        }
    }

    //追加写
    public static void writeTxt(String filePath, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath, true)));
            out.write(content + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //清空文件
    public static void clearFile(String filePath) {
        File file = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //更改文件后缀名
    public static void changeSuffixName(String path) {
        File file = new File(path);
        //得到文件夹下的所有文件和文件夹
        String[] list = file.list();

        if (list != null && list.length > 0) {
            for (String oldName : list) {
                File oldFile = new File(path, oldName);
                //判断出文件和文件夹
                if (!oldFile.isDirectory()) {
                    //文件则判断是不是要修改的
                    if (oldName.contains(".txt")) {
                        System.out.println(oldName);
                        String newoldName = oldName.substring(0, oldName.lastIndexOf(".")) + ".java";
                        System.out.println(newoldName);
                        File newFile = new File(path, newoldName);
                        boolean flag = oldFile.renameTo(newFile);
                        System.out.println(flag);
                    }
                } else {
                    //文件夹则迭代
                    String newpath = path + "/" + oldName;
                    changeSuffixName(newpath);
                }
            }
        }
    }

    public static void changeAll() {
        changeSuffixName("src\\main\\java\\com\\example\\elema\\cyf\\generate\\Commons\\");
        changeSuffixName("src\\main\\java\\com\\example\\elema\\cyf\\generate\\Federates\\");
        changeSuffixName("src\\main\\java\\com\\example\\elema\\cyf\\generate\\Abstract\\");
    }


    //文件拷贝
    public static void copyDir(String oldPath, String newPath) throws IOException {
        File file = new File(oldPath);
        //文件名称列表
        String[] filePath = file.list();

        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            if ((new File(oldPath + file.separator + filePath[i])).isDirectory()) {
                copyDir(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

            if (new File(oldPath + file.separator + filePath[i]).isFile()) {
                copyFile(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

        }
    }

    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);
        ;

        byte[] buffer = new byte[2097152];

        while ((in.read(buffer)) != -1) {
            out.write(buffer);
        }
    }


}



