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
@TableName("catalog_group")
public class CatalogGroup implements Serializable {

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public Integer getRegisterOrganizationId() {
        return registerOrganizationId;
    }

    public void setRegisterOrganizationId(Integer registerOrganizationId) {
        this.registerOrganizationId = registerOrganizationId;
    }

    public String getRegisterOrganizationName() {
        return registerOrganizationName;
    }

    public void setRegisterOrganizationName(String registerOrganizationName) {
        this.registerOrganizationName = registerOrganizationName;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(Integer updatedId) {
        this.updatedId = updatedId;
    }

    public Integer getUpdatedName() {
        return updatedName;
    }

    public void setUpdatedName(Integer updatedName) {
        this.updatedName = updatedName;
    }

    public Boolean getFreezed() {
        return freezed;
    }

    public void setFreezed(Boolean freezed) {
        this.freezed = freezed;
    }

    @Override
    public String toString() {
        return "CatalogGroup{" +
            "id = " + id +
            ", name = " + name +
            ", code = " + code +
            ", parentId = " + parentId +
            ", parentCode = " + parentCode +
            ", createdTime = " + createdTime +
            ", createdId = " + createdId +
            ", createdName = " + createdName +
            ", registerOrganizationId = " + registerOrganizationId +
            ", registerOrganizationName = " + registerOrganizationName +
            ", updatedTime = " + updatedTime +
            ", updatedId = " + updatedId +
            ", updatedName = " + updatedName +
            ", freezed = " + freezed +
        "}";
    }
}
