package com.haoduor.graduation.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.crypto.digest.DigestUtil;
import com.haoduor.graduation.dao.TemplateMapper;
import com.haoduor.graduation.model.Template;
import com.haoduor.graduation.model.TemplateExample;
import com.haoduor.graduation.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public boolean addTemplate(File file) {
        String sha256 = DigestUtil.sha256Hex(file);
        Template template = new Template();

        template.setId(snowflake.nextId());
        template.setFileName(file.getName());
        template.setSha256(sha256);
        template.setIsDelete(false);
        template.setUploadTime(DateUtil.date());

        int res = templateMapper.insert(template);
        return res == 0;
    }

    @Override
    public Template getTemplateBySha256(String sha256) {
        TemplateExample te = new TemplateExample();
        te.createCriteria().andSha256EqualTo(sha256)
          .andIsDeleteEqualTo(false);

        List<Template> res = templateMapper.selectByExample(te);
        if (res.size() == 1) {
            return res.get(0);
        }

        return null;
    }

    @Override
    public List<Template> getTemplate() {
        TemplateExample te = new TemplateExample();
        te.createCriteria().andIsDeleteEqualTo(false);
        return templateMapper.selectByExample(te);
    }

    @Override
    public boolean deleteTemplate(String sha256) {
        TemplateExample te = new TemplateExample();
        te.createCriteria().andSha256EqualTo(sha256);

        int res = templateMapper.deleteByExample(te);

        return res == 1;
    }
}
