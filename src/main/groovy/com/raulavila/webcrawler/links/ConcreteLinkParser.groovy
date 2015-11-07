package com.raulavila.webcrawler.links

class ConcreteLinkParser implements LinkParser {

    private final LinkType linkConfig

    ConcreteLinkParser(LinkType linkConfig) {
        this.linkConfig = linkConfig
    }

    Set<Link> parse(def html) {
        html."**"
            .findAll(
                    linkConfig.filterClosure)
            .collect(
                    linkConfig.extractClosure) as LinkedHashSet

    }
}
