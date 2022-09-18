/*
 * Copyright 2021 Lemonico Co.,Ltd. AllRights Reserved.
 */
package api.lemonico.user.resource;



import api.lemonico.core.attribute.ID;
import api.lemonico.user.entity.UserDepartmentEntity;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ユーザ部署リソース
 *
 * @since 1.0.0
 */
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Getter
@Builder(toBuilder = true)
@With
@ToString
public class UserDepartmentResource
{

    /** 自動採番ID */
    private final ID<UserDepartmentEntity> id;

    /** UUID */
    private final String uuid;

    /** 部署コード */
    private final String departmentCode;

    /** 部署タイプ */
    private final Integer departmentType;

    /** ロールタイプ（１：管理者、２：スタッフ） */
    private final Integer roleType;

    /** 作成者 */
    private final String createdBy;

    /** 作成日時 */
    private final LocalDateTime createdAt;

    /** 更新者 */
    private final String modifiedBy;

    /** 更新日時 */
    private final LocalDateTime modifiedAt;

    /** 削除フラグ（0: 未削除 1: 削除済） */
    private final Integer isDeleted;

    /**
     * 指定したエンティティを使用して、リソースを構築します。
     *
     * @param entity ユーザ部署エンティティ
     */
    public UserDepartmentResource(UserDepartmentEntity entity) {
        this.id = entity.getId();
        this.uuid = entity.getUuid();
        this.departmentCode = entity.getDepartmentCode();
        this.departmentType = entity.getDepartmentType();
        this.roleType = entity.getRoleType();
        this.createdBy = entity.getCreatedBy();
        this.createdAt = entity.getCreatedAt();
        this.modifiedBy = entity.getModifiedBy();
        this.modifiedAt = entity.getModifiedAt();
        this.isDeleted = entity.getIsDeleted();
    }

    /**
     * リソースをエンティティに変換します。
     *
     * @return ユーザ部署エンティティ
     */
    public UserDepartmentEntity toEntity() {
        return UserDepartmentEntity.builder()
            .id(id)
            .uuid(uuid)
            .departmentCode(departmentCode)
            .departmentType(departmentType)
            .roleType(roleType)
            .createdBy(createdBy)
            .createdAt(createdAt)
            .modifiedBy(modifiedBy)
            .modifiedAt(modifiedAt)
            .isDeleted(isDeleted)
            .build();
    }
}
