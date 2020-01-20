package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类别名称
     */
    @Column(name = "name", columnDefinition = "varchar(50) not null")
    private String name;

    /**
     * 类别别名
     */
    @Column(name = "slug_name", columnDefinition = "varchar(50) not null", unique = true)
    private String slugName;

    /**
     * 描述
     */
    @Column(name = "description", columnDefinition = "varchar(100) default ''")
    private String description;

    /**
     * 父类别
     */
    @Column(name = "parent_id", columnDefinition = "int default 0")
    private Integer parentId;

    @Override
    public void prePersist() {
        super.prePersist();
        id = null;

        if (description == null) {
            description = "";
        }

        if (parentId == null || parentId < 0) {
            parentId = 0;
        }
    }

}
