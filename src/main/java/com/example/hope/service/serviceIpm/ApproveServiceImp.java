package com.example.hope.service.serviceIpm;

import com.example.hope.common.logger.LoggerHelper;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.*;
import com.example.hope.model.mapper.ApproveMapper;
import com.example.hope.model.vo.StoreVO;
import com.example.hope.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 审批服务实现类
 * @author: DHY
 * @created: 2021/05/02 16:09
 */
@Service
@Log4j2
public class ApproveServiceImp implements ApproveService {

    @Resource
    private ApproveMapper approveMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private StoreService storeService;

    @Resource
    private ProductService productService;

    @Resource
    private MailService mailService;

    @Resource
    private UserService userService;

    @Override
    @Transactional
    @CacheEvict(value = "approve", allEntries = true)
    public void insertStore(ApproveStore approveStore, String token) {
        if (JwtUtils.getUserId(token) != approveStore.getUid()) throw new BusinessException(1, "只能当前用户才能提交商店审批");
        approveStore.setStatus(false);
        int res = approveMapper.insertStore(approveStore);
        log.info(LoggerHelper.logger(approveStore, res));
        BusinessException.check(res, "添加失败");
        notify(token, String.format("%s，已提交到后台审批", approveStore.toString()));
    }

    @Override
    @Transactional
    @CacheEvict(value = "approve", allEntries = true)
    public void insertProduct(ApproveProduct approveProduct, String token) {
        if (storeService.findByName(approveProduct.getStoreName()) == null) throw new BusinessException(1, "商店不存在");
        if (JwtUtils.getUserId(token) != approveProduct.getUid()) throw new BusinessException(1, "只能当前用户才能提交产品审批");
        approveProduct.setStatus(false);
        int res = approveMapper.insertProduct(approveProduct);
        log.info(LoggerHelper.logger(approveProduct, res));
        BusinessException.check(res, "添加失败");
        notify(token, String.format("%s，已提交到后台审批", approveProduct.toString()));
    }

    @Override
    @Transactional
    @CacheEvict(value = "approve", allEntries = true)
    public void approveStore(ApproveStore approveStore) {
        boolean allow = approveStore.isStatus();
        int res = approveMapper.approveStore(approveStore.getId(), allow ? 1 : 0);
        log.info(LoggerHelper.logger(ApproveStore.class, res));
        BusinessException.check(res, "操作失败");
        String content;
        if (allow) {
            // 如果categoryId不存在，添加category
            if (approveStore.getCategoryId() == 0 || !categoryService.exist(approveStore.getCategoryId())) {
                Category category = new Category();
                category.setName(approveStore.getCategoryName());
                category.setBusinessId(approveStore.getServiceId());
                categoryService.insert(category);
            }
            Store store = new Store();
            store.setName(approveStore.getName());
            store.setImage(approveStore.getImage());
            store.setBusinessId(approveStore.getServiceId());
            store.setCategoryId(approveStore.getCategoryId());
            storeService.insert(store);
            content = String.format("%s，已通过后台审批", approveStore.toString());
        } else content = String.format("%s，未通过后台审批", approveStore.toString());
        notify(approveStore.getUid(), content);

    }

    @Override
    @Transactional
    @CacheEvict(value = "approve", allEntries = true)
    public void approveProduct(ApproveProduct approveProduct) {
        boolean allow = approveProduct.isStatus();
        int res = approveMapper.approveProduct(approveProduct.getId(), allow ? 1 : 0);
        log.info(LoggerHelper.logger(ApproveProduct.class, res));
        BusinessException.check(res, "操作失败");
        String content;
        if (allow) {
            StoreVO store = storeService.findByName(approveProduct.getName());
            if (store == null) throw new BusinessException(1, "商店不存在");
            Product product = new Product();
            product.setName(approveProduct.getName());
            product.setImage(approveProduct.getImage());
            product.setStoreId(store.getId());
            productService.insert(product);
            content = String.format("%s，已通过后台审批", approveProduct.toString());
        } else content = String.format("%s，未通过后台审批", approveProduct.toString());
        notify(approveProduct.getUid(), content);
    }

    private void notify(long uid, String content) {
        User user = userService.findById(uid);
        BusinessException.check(user == null, "用户不存在");
        mailService.sendTokenMail(user.getEmail(), content, "shuttle审批结果通知");
    }

    private void notify(String token, String content) {
        String userEmail = JwtUtils.getUserEmail(token);
        mailService.sendTokenMail(userEmail, content, "shuttle审批结果通知");
    }

    @Override
    @Cacheable(value = "approve", key = "methodName + #option.toString()")
    public PageInfo<ApproveStore> findAllStore(Map<String, String> option) {
        Utils.checkOption(option, ApproveStore.class);
        String orderBy = String.format("approve_store.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(approveMapper.findAllStore());
    }

    @Override
    @Cacheable(value = "approve", key = "methodName + #option.toString()")
    public PageInfo<ApproveProduct> findAllProduct(Map<String, String> option) {
        Utils.checkOption(option, ApproveProduct.class);
        String orderBy = String.format("approve_product.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(approveMapper.findAllProduct());
    }

    @Override
    @Cacheable(value = "approve", key = "methodName + #userId")
    public List<String> findStoreByUserId(long userId, String token) {
        if (userId != JwtUtils.getUserId(token)) throw new BusinessException(1, "只能查询当前用户拥有的商店");
        return approveMapper.findStoreByUserId(userId);
    }
}
