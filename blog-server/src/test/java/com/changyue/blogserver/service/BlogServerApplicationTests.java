package com.changyue.blogserver.service;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogServerApplicationTests {
/*
    @Autowired
    JestClient jestClient;

    @Test
    void contextLoads() {
        Article article = new Article();
        article.setId(1);
        article.setAuthor("changyue");
        article.setTitle("good msg");
        article.setContent("this is a test msg!!!");
        Index index = new Index.Builder(article).index("changyue").type("news").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void search() {
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"test\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Search search = new Search.Builder(json).addIndex("changyue").addType("news").build();
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/

}
