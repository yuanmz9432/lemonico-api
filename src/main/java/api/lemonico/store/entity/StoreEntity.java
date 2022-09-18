/*
 * Copyright 2021 Lemonico Co.,Ltd. AllRights Reserved.
 */
package api.lemonico.store.entity;



import api.lemonico.core.attribute.ID;
import api.lemonico.core.entity.LcEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;
import org.seasar.doma.*;

/**
 * ストア情報エンティティ
 *
 * @since 1.0.0
 */
@Entity(immutable = true)
@Value
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@With
@Table(name = "store")
public class StoreEntity extends LcEntity
{
    /** 自動採番ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    ID<StoreEntity> id;
    /** ストアコード */
    @Column(name = "store_code")
    String storeCode;
    /** ストア名称 */
    @Column(name = "store_name")
    String storeName;
    /** 契約タイプ */
    @Column(name = "contract_type")
    String contractType;
    /** 作成者 */
    @Column(name = "created_by")
    String createdBy;
    /** 作成日時 */
    @Column(name = "created_at")
    LocalDateTime createdAt;
    /** 更新者 */
    @Column(name = "modified_by")
    String modifiedBy;
    /** 更新日時 */
    @Column(name = "modified_at")
    LocalDateTime modifiedAt;
    /** 削除フラグ（0: 未削除 1: 削除済） */
    @Column(name = "is_deleted")
    Integer isDeleted;
}