/*
 * Copyright 2021 Lemonico Co.,Ltd. AllRights Reserved.
 */
package api.lemonico.user.service;

import static java.util.stream.Collectors.toList;

import api.lemonico.auth.config.LoginUser;
import api.lemonico.core.attribute.ID;
import api.lemonico.core.attribute.LcPagination;
import api.lemonico.core.attribute.LcResultSet;
import api.lemonico.core.exception.LcResourceAlreadyExistsException;
import api.lemonico.core.exception.LcResourceNotFoundException;
import api.lemonico.core.exception.LcUnexpectedPhantomReadException;
import api.lemonico.core.utils.BCryptEncoder;
import api.lemonico.user.entity.UserEntity;
import api.lemonico.user.repository.UserAuthorityRepository;
import api.lemonico.user.repository.UserRepository;
import api.lemonico.user.resource.UserResource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * ユーザーサービス
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({
    @Autowired, @Lazy
}))
public class UserService
{
    /**
     * ユーザーリポジトリ
     */
    private final UserRepository userRepository;

    /**
     * ユーザー権限リポジトリ
     */
    private final UserAuthorityRepository userAuthorityRepository;

    /**
     * 権限サービス
     */
    private final UserAuthorityService userAuthorityService;

    /**
     * 検索条件・ページングパラメータ・ソート条件を指定して、ユーザリソースの一覧を取得します。
     *
     * @param condition 検索条件
     * @param pagination ページングパラメータ
     * @param sort ソートパラメータ
     * @return ユーザリソースの結果セットが返されます。
     */
    @Transactional(readOnly = true)
    public LcResultSet<UserResource> getResourceList(
        UserRepository.Condition condition,
        LcPagination pagination,
        UserRepository.Sort sort) {
        // ユーザの一覧と全体件数を取得します。
        var resultSet = userRepository.findAll(condition, pagination, sort);

        // ユーザエンティティのリストをユーザリソースのリストに変換します。
        var resources = convertEntitiesToResources(resultSet.getData());

        return new LcResultSet<>(resources, resultSet.getCount());
    }

    /**
     * ユーザIDを指定して、ユーザを取得します。
     *
     * @param id ユーザID
     * @return ユーザリソース
     */
    @Transactional(readOnly = true)
    public Optional<UserResource> getResource(ID<UserEntity> id) {
        // ユーザ情報取得
        var userEntity = userRepository.findById(id);
        var userResource = userEntity.map(this::convertEntityToResource);
        var uuid = userResource.map(UserResource::getUuid)
            .orElseThrow(() -> new LcResourceNotFoundException(UserResource.class, id));

        return Optional.of(userResource.get()
            .withPassword(""));
    }

    /**
     * ユーザを作成します。
     *
     * @param resource ユーザリソース
     * @return 作成されたユーザリソース
     */
    @Transactional
    public UserResource createResource(UserResource resource) {
        // ユーザ重複性チェック（username と email）
        if (isUserExisted(resource)) {
            throw new LcResourceAlreadyExistsException(UserEntity.class,
                resource.getEmail() + " or " + resource.getUsername());
        }

        // ユーザ登録
        var id = userRepository.create(
            resource.withPassword(BCryptEncoder.getInstance().encode(resource.getPassword()))
                // .withType(UserType.PREMIUM.getValue())
                .toEntity());

        // ユーザ権限付与
        userAuthorityRepository.grantAuthorization(resource.getUuid(), resource.getAuthorities());

        // ユーザを取得します。
        return getResource(id).orElseThrow(LcUnexpectedPhantomReadException::new);
    }

    /**
     * ユーザ重複性チェック
     *
     * @param resource ユーザリソース
     * @return true：重複した、false：重複しない
     */
    private boolean isUserExisted(UserResource resource) {
        var client = getResourceList(
            UserRepository.Condition.builder()
                .email(resource.getEmail())
                .build(),
            LcPagination.DEFAULT,
            UserRepository.Sort.DEFAULT);
        if (client.getCount() > 0) {
            return true;
        }

        client = getResourceList(
            UserRepository.Condition.builder()
                .username(resource.getUsername())
                .build(),
            LcPagination.DEFAULT,
            UserRepository.Sort.DEFAULT);
        return client.getCount() > 0;
    }

    /**
     * ユーザIDを指定して、ユーザを更新します。
     *
     * @param id ユーザID
     * @param resource ユーザリソース
     * @return 更新後のユーザリソース
     */
    @Transactional
    public UserResource updateResource(ID<UserEntity> id, UserResource resource) {
        // ユーザIDにおいて重複したデータが存在していることを示す。
        if (!userRepository.exists(id)) {
            throw new LcResourceNotFoundException(UserEntity.class, id);
        }

        // ユーザを更新します。
        userRepository.update(id, resource.toEntity());

        // ユーザを取得します。
        return getResource(id).orElseThrow(LcUnexpectedPhantomReadException::new);
    }

    /**
     * ユーザーIDを指定して、ユーザーを削除します。
     *
     * @param id ユーザーID
     */
    @Transactional
    public void deleteResource(ID<UserEntity> id) {
        // TODO Waiting for finalization of basic design according to Q&A
        // ユーザIDにおいて重複したデータが存在していることを示す。
        if (!userRepository.exists(id)) {
            throw new LcResourceNotFoundException(UserEntity.class, id);
        }

        // ユーザを削除します。
        userRepository.deleteLogicById(id);
    }

    /**
     * メールアドレスを指定して、LoginUserを取得する。
     *
     * @param subject JWTサブジェクト
     * @return LoginUser
     */
    @Transactional(readOnly = true)
    public LoginUser getLoginUserBySubject(String subject) {
        Pattern uuidPattern = Pattern.compile("([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})");
        Pattern mailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        UserRepository.Condition condition;
        if (uuidPattern.matcher(subject).matches()) {
            condition = UserRepository.Condition.builder().uuid(subject).build();
        } else if (mailPattern.matcher(subject).matches()) {
            condition = UserRepository.Condition.builder().email(subject).build();
        } else {
            condition = UserRepository.Condition.DEFAULT;
        }
        final var userResourceLcResultSet = getResourceList(
            condition,
            LcPagination.DEFAULT,
            UserRepository.Sort.DEFAULT);
        if (userResourceLcResultSet.getCount() < 1) {
            throw new LcResourceNotFoundException(LoginUser.class, subject);
        }
        var loginUser = userResourceLcResultSet.getData().get(0);
        // ユーザ権限取得
        var authorities = userAuthorityService.getResourceList(
            UserAuthorityRepository.Condition.builder().uuid(loginUser.getUuid()).build(),
            LcPagination.DEFAULT,
            UserAuthorityRepository.Sort.DEFAULT).stream()
            .map((item) -> new SimpleGrantedAuthority(item.getAuthorityCode()))
            .collect(Collectors.toList());
        return LoginUser.builder()
            .id(loginUser.getId().getValue())
            .uuid(loginUser.getUuid())
            .email(loginUser.getEmail())
            .username(loginUser.getUsername())
            .password(loginUser.getPassword())
            .enabled(true)
            .authorities(authorities)
            .build();
    }

    /**
     * ユーザーエンティティをユーザーリソースに変換します。
     *
     * @param entity エンティティ
     * @return リソース
     */
    @Transactional(readOnly = true)
    public UserResource convertEntityToResource(UserEntity entity) {
        return convertEntitiesToResources(Collections.singletonList(entity)).get(0);
    }

    /**
     * ユーザーエンティティのリストをユーザーリソースのリストに変換します。
     *
     * @param entities エンティティのリスト
     * @return リソースのリスト
     */
    @Transactional(readOnly = true)
    public List<UserResource> convertEntitiesToResources(List<UserEntity> entities) {
        return entities.stream()
            .map(UserResource::new)
            .collect(toList());
    }

}