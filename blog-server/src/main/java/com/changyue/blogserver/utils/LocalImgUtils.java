package com.changyue.blogserver.utils;

import com.changyue.blogserver.config.properties.BlogProperties;
import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.model.rep.UploadResult;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author : 袁阊越
 * @description : 本地图片工具类
 * @date : 2020-03-12 10:45
 */
@Slf4j
public class LocalImgUtils {

    private final static String UPLOAD_SUB_DIR = "upload/";

    private final static String THUMBNAIL_SUFFIX = "-thumbnail";

    private final static String WORK_PATH = BlogProperties.WORK_DIR;

    private final static int THUMB_WIDTH = 256;

    private final static int THUMB_HEIGHT = 256;

    private static MediaType IMAGE_TYPE = MediaType.valueOf("image/*");

    static ReentrantLock lock = new ReentrantLock();

    public LocalImgUtils() {
        checkDir();
    }

    private void checkDir() {
        Path path = Paths.get(WORK_PATH);
        //检查是否文件夹
        Assert.isTrue(Files.isDirectory(path), WORK_PATH + "不是文件夹");
        //检查是否可读
        Assert.isTrue(Files.isReadable(path), WORK_PATH + "不可读");
        //检查是否可写
        Assert.isTrue(Files.isWritable(path), WORK_PATH + "不可写");
    }

    public static UploadResult upload(MultipartFile file) {
        Assert.notNull(file, "文件不能空");

        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        Month month = now.getMonth();

        //创建文件夹名称
        String dir = UPLOAD_SUB_DIR + year + BlogProperties.FILE_SEPARATOR + month.toString() + BlogProperties.FILE_SEPARATOR;

        //文件原始名称
        String originalFileBaseName = FileNameUtils.getBasename(Objects.requireNonNull(file.getOriginalFilename()));

        //文件基础名
        String fileBaseName = originalFileBaseName + '-' + UUID.randomUUID().toString();

        //拓展名
        String extension = FileNameUtils.getExtension(file.getOriginalFilename());

        //文件的路径
        String filePath = dir + fileBaseName + '.' + extension;

        //完整的上传路径
        Path uploadPath = Paths.get(WORK_PATH, filePath);

        log.debug("上传[{}],完整路径为：[{}]", file.getOriginalFilename(), uploadPath);

        try {
            //创建文件夹
            Files.createDirectories(uploadPath.getParent());
            //创建文件
            Files.createFile(uploadPath);
            //写入磁盘
            file.transferTo(uploadPath);

            UploadResult uploadResult = new UploadResult();
            uploadResult.setFilename(originalFileBaseName);
            uploadResult.setFilePath(filePath);
            uploadResult.setSuffix(extension);
            uploadResult.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));
            uploadResult.setSize(file.getSize());

            if (uploadResult.getMediaType() != null && IMAGE_TYPE.includes(uploadResult.getMediaType())) {
                lock.lock();
                try (InputStream uploadFileInputStream = new FileInputStream(uploadPath.toFile())) {

                    //缩略图名字
                    String thumbnailName = fileBaseName + THUMBNAIL_SUFFIX;
                    //缩略图路径
                    String thumbnailPath = dir + thumbnailName + '.' + extension;
                    //完整的路径
                    Path path = Paths.get(WORK_PATH, thumbnailPath);

                    //转换成BufferedImage(Image实现类)，可以很好对图片进行操作
                    BufferedImage bufferedImage = getImageFormFile(uploadFileInputStream, extension);
                    uploadResult.setWidth(bufferedImage.getWidth());
                    uploadResult.setHeight(bufferedImage.getHeight());

                    //生成缩略图，并设置路径
                    boolean isGenerate = generateThumbnail(bufferedImage, path, extension);
                    if (isGenerate) {
                        uploadResult.setThumbPath(thumbnailPath);
                    } else {
                        uploadResult.setThumbPath(filePath);
                    }

                } finally {
                    lock.unlock();
                }
            }
            log.info("图片[{}]上传成功，位置：[{}]", originalFileBaseName, uploadPath.toString());
            return uploadResult;
        } catch (IOException e) {
            e.printStackTrace();
            throw new CreateException("图片上传失败").setErrData(uploadPath);
        }
    }

    /**
     * 生成缩略图
     *
     * @param bufferedImage 缓冲图片
     * @param path          路径
     * @param extension     后缀
     * @return 是否生成
     */
    private static boolean generateThumbnail(BufferedImage bufferedImage, Path path, String extension) {

        Assert.notNull(bufferedImage, "图片不能空");
        Assert.notNull(path, "图片的路径不能空");

        boolean isGenerate = false;

        try {
            Files.createFile(path);
            log.debug("正在生成缩略图：[{}]", path.toString());
            Thumbnails.of(bufferedImage).size(THUMB_WIDTH, THUMB_HEIGHT).keepAspectRatio(true).toFile(path.toFile());
            log.debug("已经生成缩略图，写入：[{}]", path.toString());
            isGenerate = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isGenerate;

    }

    /**
     * 从文件中找到图片
     *
     * @param is        InputStream
     * @param extension 后缀
     * @return 图片
     */
    public static BufferedImage getImageFormFile(InputStream is, String extension) {
        log.debug("当前的文件类型为：[{}]", extension);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String changeFileSeparator2UrlSeparator(String pathname) {
        Assert.hasText(pathname, "Path name must not be blank");
        return pathname.replace(BlogProperties.FILE_SEPARATOR, "/");
    }


}
