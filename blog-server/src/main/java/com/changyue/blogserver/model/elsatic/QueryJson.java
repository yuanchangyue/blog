package com.changyue.blogserver.model.elsatic;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-23 14:35
 */
public class QueryJson {

    /**
     * query : {"match_phrase":{"originalContent":"心情"}}
     * highlight : {"fields":{"originalContent":{}}}
     */
    private QueryBean query;
    private HighlightBean highlight;

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public HighlightBean getHighlight() {
        return highlight;
    }

    public void setHighlight(HighlightBean highlight) {
        this.highlight = highlight;
    }

    public static class QueryBean {
        /**
         * match_phrase : {"originalContent":"心情"}
         */

        private MatchPhraseBean match_phrase;

        public MatchPhraseBean getMatch_phrase() {
            return match_phrase;
        }

        public void setMatch_phrase(MatchPhraseBean match_phrase) {
            this.match_phrase = match_phrase;
        }

        public static class MatchPhraseBean {
            /**
             * title : 标题
             */
            private String title;
            /**
             * originalContent : 心情
             */
            private String originalContent;

            public String getOriginalContent() {
                return originalContent;
            }

            public void setOriginalContent(String originalContent) {
                this.originalContent = originalContent;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }

    public static class HighlightBean {

        /**
         * fields : {"originalContent":{}}
         */
        private FieldsBean fields;

        public FieldsBean getFields() {
            return fields;
        }

        public void setFields(FieldsBean fields) {
            this.fields = fields;
        }

        public static class FieldsBean {
            /**
             * originalContent : {}
             */
            private OriginalContentBean originalContent;
            /**
             * title : {}
             */
            private TitleBean title;

            public TitleBean getTitleBean() {
                return title;
            }

            public void setTitleBean(TitleBean title) {
                this.title = title;
            }

            public OriginalContentBean getOriginalContent() {
                return originalContent;
            }

            public void setOriginalContent(OriginalContentBean originalContent) {
                this.originalContent = originalContent;
            }

            public static class OriginalContentBean {
            }

            public static class TitleBean {
            }
        }
    }
}
