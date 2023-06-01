package io.github.lagom130.nydus.service.impl;

import io.github.lagom130.nydus.entity.CatalogGroupEntity;
import io.github.lagom130.nydus.mapper.CatalogGroupMapper;
import io.github.lagom130.nydus.service.ICatalogGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 目录分类 服务实现类
 * </p>
 *
 * @author lagom
 * @since 2023-05-31
 */
@Service
public class CatalogGroupServiceImpl extends ServiceImpl<CatalogGroupMapper, CatalogGroupEntity> implements ICatalogGroupService {

}
