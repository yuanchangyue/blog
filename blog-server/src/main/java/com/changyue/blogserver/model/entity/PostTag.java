package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @program: blog-server
 * @description: 文章标签
 * @author: ChangYue
 * @create: 2020-01-21 08:49
 */
@Entity
@Data
@Table(name = "post_tags")
public class PostTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int not null")
    private Integer id;
    
    @Column(name = "post_id", columnDefinition = "int not null")
    private Integer postId;

    @Override
    protected void prePersist() {
        super.prePersist();
        id = null;
    }

}
