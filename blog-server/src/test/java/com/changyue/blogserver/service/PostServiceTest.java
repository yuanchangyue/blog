package com.changyue.blogserver.service;

import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.params.PostParam;
import com.changyue.blogserver.model.vo.PostVO;
import com.changyue.blogserver.serivce.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 文章业务测试
 * @date : 2020-03-04 14:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    public void testCreatePost() {
        PostParam postParam = new PostParam();
        postParam.setTitle("测试文章");
        postParam.setStatus(0);
        postParam.setFormatContent("<h1><a id=\"md_0\"></a>测试md</h1>\n" +
                "<h2><a id=\"1_1\"></a>1</h2>\n" +
                "<div class=\"hljs-center\">\n" +
                "<p><s>title</s></p>\n" +
                "</div>\n" +
                "<h2><a id=\"2_8\"></a>2</h2>\n" +
                "<p>测试#### 四级标题</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-java\">\n" +
                "<span class=\"hljs-meta\">@Data</span>\n" +
                "<span class=\"hljs-keyword\">public</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">PhotoParam</span> <span class=\"hljs-keyword\">implements</span> <span class=\"hljs-title\">InputConvert</span>&lt;<span class=\"hljs-title\">Photo</span>&gt; </span>{\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@NotBlank</span>(message = <span class=\"hljs-string\">\"照片名称不能为空\"</span>)\n" +
                "    <span class=\"hljs-keyword\">private</span> String name;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> String description;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> Date takeTime;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> String location;\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@NotBlank</span>(message = <span class=\"hljs-string\">\"照片缩略图链接地址不能为空\"</span>)\n" +
                "    <span class=\"hljs-keyword\">private</span> String thumbnail;\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@NotBlank</span>(message = <span class=\"hljs-string\">\"照片链接地址不能为空\"</span>)\n" +
                "    <span class=\"hljs-keyword\">private</span> String url;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> String team;\n" +
                "}\n" +
                "\n" +
                "</code></div></pre>");
        postParam.setOriginalContent("<h1><a id=\"md_0\"></a>测试md</h1>\n" +
                "<h2><a id=\"1_1\"></a>1</h2>\n" +
                "<div class=\"hljs-center\">\n" +
                "<p><s>title</s></p>\n" +
                "</div>\n" +
                "<h2><a id=\"2_8\"></a>2</h2>\n" +
                "<p>测试#### 四级标题</p>\n" +
                "<pre><div class=\"hljs\"><code class=\"lang-java\">\n" +
                "<span class=\"hljs-meta\">@Data</span>\n" +
                "<span class=\"hljs-keyword\">public</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">PhotoParam</span> <span class=\"hljs-keyword\">implements</span> <span class=\"hljs-title\">InputConvert</span>&lt;<span class=\"hljs-title\">Photo</span>&gt; </span>{\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@NotBlank</span>(message = <span class=\"hljs-string\">\"照片名称不能为空\"</span>)\n" +
                "    <span class=\"hljs-keyword\">private</span> String name;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> String description;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> Date takeTime;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> String location;\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@NotBlank</span>(message = <span class=\"hljs-string\">\"照片缩略图链接地址不能为空\"</span>)\n" +
                "    <span class=\"hljs-keyword\">private</span> String thumbnail;\n" +
                "\n" +
                "    <span class=\"hljs-meta\">@NotBlank</span>(message = <span class=\"hljs-string\">\"照片链接地址不能为空\"</span>)\n" +
                "    <span class=\"hljs-keyword\">private</span> String url;\n" +
                "\n" +
                "    <span class=\"hljs-keyword\">private</span> String team;\n" +
                "}\n" +
                "\n" +
                "</code></div></pre>");

        Post post = postParam.convertTo();

        PostVO by = postService.createBy(post, null, null);

        System.out.println("by = " + by);
    }

    @Test
    public void test() {
        List<PostVO> postVOS = postService.latestPost();
        postVOS.forEach(System.out::println);
    }

}
