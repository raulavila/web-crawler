package com.raulavila.webcrawler

class ConcreteLinkParser implements LinkParser {

    private final LinkType linkConfig

    ConcreteLinkParser(LinkType linkConfig) {
        this.linkConfig = linkConfig
    }

    List<Link> parse(def html) {
        html."**"
            .findAll(
                    linkConfig.filterClosure)
            .collect(
                    linkConfig.extractClosure)
        
    }
}
