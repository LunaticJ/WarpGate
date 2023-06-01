package io.github.lagom130.nydus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 目录分类
 * </p>
 *
 * @author lagom
 * @since 2023-05-31
 */
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@TableName("catalog_group")
public class CatalogGroupEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String code;

    private Integer parentId;

    private String parentCode;

    private LocalDateTime createdTime;

    private Integer createdId;

    private String createdName;

    private Integer registerOrganizationId;

    private String registerOrganizationName;

    private LocalDateTime updatedTime;

    private Integer updatedId;

    private Integer updatedName;

    private Boolean freezed;
}
