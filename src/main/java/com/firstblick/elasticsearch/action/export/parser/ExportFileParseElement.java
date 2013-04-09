package com.firstblick.elasticsearch.action.export.parser;

import com.firstblick.elasticsearch.action.export.ExportContext;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.search.SearchParseElement;
import org.elasticsearch.search.internal.SearchContext;

/**
 */
public class ExportFileParseElement implements SearchParseElement {

    private String lastValue;

    @Override
    public void parse(XContentParser parser, SearchContext context) throws Exception {
        XContentParser.Token token = parser.currentToken();
        if (token.isValue()) {
            ((ExportContext)context).outputFile(parser.text());
            lastValue = ((ExportContext)context).outputFile();
        }
    }

    public void reset() {
        lastValue = null;
    }

    public String getLastValue() {
        return lastValue;
    }
}