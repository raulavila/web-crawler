package com.raulavila.webcrawler.links

class CompositeLinkParser implements LinkParser {
    
    List<LinkParser> linkParsers

    CompositeLinkParser(List<LinkParser> linkParsers) {
        this.linkParsers = linkParsers
    }

    @Override
    Set<Link> parse(def html) {
        Set<Link> links = new LinkedHashSet<>()
        
        for (linkParser in linkParsers) {
            links.addAll(linkParser.parse(html))
        }
        
        links
    }
}
