package com.changyue.blogserver.ulits;

/**
 * @author : 袁阊越
 * @description : 其他测试
 * @date : 2020-03-15 14:04
 */
public class OtherTest {
    public static void main(String[] args) {

//        System.out.println(BlogProperties.URL_SEPARATOR + BlogProperties.PREFIX + BlogProperties.URL_SEPARATOR + "**");
//        System.out.println(BlogProperties.FILE_PROTOCOL + BlogProperties.WORK_DIR + BlogProperties.PREFIX + BlogProperties.URL_SEPARATOR);
//
//        Path path = Paths.get(BlogProperties.WORK_DIR,"upload/2020/MARCH/test.jpg" );
//        Path fileName = path.getFileName();
//        System.out.println("fileName = " + fileName);
//
//        LocalImgUtils.delete("upload/2020/MARCH/test.jpg");

//        String test = "最后发布于2020-03-23 00:24:13 03-1509-0410-2411-1405-2612-1411-2204-1412-16";
//        int of = test.indexOf("于");
//        String substring = test.substring(test.indexOf("于") + 1, of + 20);
//        System.out.println(substring);
//
//        System.out.println("DateUtils.stringToDate(substring,\"yyyy-MM-dd HH:mm:ss\") = " + DateUtils.stringToDate(substring, "yyyy-MM-dd HH:mm:ss"));


        String str = "https://blog.csdn.net/sinat_33921105/article/details/104031977?depth_1-utm_source=distribute.pc_feed.none-task&request_id=&utm_source=distribute.pc_feed.none-task";

        String baseUrlSize = "https://blog.csdn.net/";

        int startIndex = str.indexOf("/", 21);
        int endIndex = str.indexOf("/", 22);

        System.out.println("endIndex = " + endIndex);
        System.out.println("startIndex = " + startIndex);

        String userName = str.substring(startIndex + 1, endIndex);

        System.out.println(userName);


        /*  System.out.println("str.substring(i + 1, str.length()) = " + str.substring(i, length));
         */



    }


}
