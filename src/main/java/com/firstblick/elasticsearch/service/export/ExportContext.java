package com.firstblick.elasticsearch.service.export;

import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.service.IndexService;
import org.elasticsearch.index.shard.service.IndexShard;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.search.SearchShardTarget;
import org.elasticsearch.search.internal.SearchContext;
import org.elasticsearch.search.internal.ShardSearchRequest;

import java.util.ArrayList;
import java.util.List;


public class ExportContext extends SearchContext {

    private static final String VAR_SHARD = "${shard}";
    private static final String VAR_INDEX = "${index}";
    private static final String VAR_CLUSTER = "${cluster}";

    private List<String> outputCmdArray;
    private String outputCmd;
    private String outputFile;

    public ExportContext(long id, ShardSearchRequest request, SearchShardTarget shardTarget, Engine.Searcher engineSearcher, IndexService indexService, IndexShard indexShard, ScriptService scriptService) {
        super(id, request, shardTarget, engineSearcher, indexService, indexShard, scriptService);

    }

    public void outputCmdArray(List<String> outputCmdArray) {
        this.outputCmdArray = applyVars(outputCmdArray);
    }

    public void outputCmd(String outputCmd) {
        this.outputCmd = applyVars(outputCmd);
    }

    public void outputFile(String outputFile) {
        this.outputFile = applyVars(outputFile);
    }

    public List<String> outputCmdArray() {
        return outputCmdArray;
    }

    public String outputCmd() {
        return outputCmd;
    }

    public String outputFile() {
        return outputFile;
    }

    private List<String> applyVars(List<String> templateArray) {
        List<String> ret = new ArrayList<String>();
        for (String part : templateArray) {
            ret.add(applyVars(part));
        }
        return ret;
    }

    private String applyVars(String template) {
        template = template.replace(VAR_SHARD, String.valueOf(indexShard().shardId().getId()));
        template = template.replace(VAR_INDEX, indexShard().shardId().getIndex());
        template = template.replace(VAR_CLUSTER, clusterName());
        return template;
    }

    private String clusterName() {
        return ClusterName.clusterNameFromSettings(this.indexShard().indexSettings()).value();
    }
}
