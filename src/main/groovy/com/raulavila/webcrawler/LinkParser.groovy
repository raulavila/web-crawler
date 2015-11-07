package com.raulavila.webcrawler

class LinkParser {

    private final LinkType linkConfig

    LinkParser(LinkType linkConfig) {
        this.linkConfig = linkConfig
    }

    List<String> parse(def html) {
        html."**"
            .findAll(
                    linkConfig.filterClosure)
            .collect(
                    linkConfig.extractClosure)
        
    }
}
