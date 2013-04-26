package com.firstblick.elasticsearch.plugin.export;

import java.util.Collection;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.rest.RestModule;

import com.firstblick.elasticsearch.module.export.ExportModule;
import com.firstblick.elasticsearch.module.import_.ImportModule;
import com.firstblick.elasticsearch.rest.action.admin.export.RestExportAction;
import com.firstblick.elasticsearch.rest.action.admin.import_.RestImportAction;


public class InOutPlugin extends AbstractPlugin {
    public String name() {
        return "inout";
    }

    public String description() {
        return "InOut plugin";
    }

    public void onModule(RestModule restModule) {
        restModule.addRestAction(RestExportAction.class);
        restModule.addRestAction(RestImportAction.class);
    }

    @Override
    public Collection<Class<? extends Module>> modules() {
        Collection<Class<? extends Module>> modules = Lists.newArrayList();
        modules.add(ExportModule.class);
        modules.add(ImportModule.class);
        return modules;
    }

}