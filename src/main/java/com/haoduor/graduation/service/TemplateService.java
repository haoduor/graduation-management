package com.haoduor.graduation.service;

import com.haoduor.graduation.model.Template;

import java.io.File;
import java.util.List;

public interface TemplateService {
    public boolean addTemplate(File file);
    public Template getTemplateBySha256(String sha256);
    public List<Template> getTemplate();
}
