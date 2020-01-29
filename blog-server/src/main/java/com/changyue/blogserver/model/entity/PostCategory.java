package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @program: blog-server
 * @description: 文章类别
 * @author: 袁阊越
 * @create: 2020-01-22 21:09
 */
@Data
@Entity
@Table(name = "post_categories")
@EqualsAndHashCode(callSuper = true)
public class PostCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类别id
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 文章id
     */
    @Column(name = "post_id")
    private Integer postId;


    @Override
    public void prePersist() {
        super.prePersist();
        id = null;
    }

}
