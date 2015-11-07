package com.raulavila.webcrawler.links

class CompositeLinkParser implements LinkParser{
    
    List<LinkParser> linkParsers

    CompositeLinkParser(List<LinkParser> linkParsers) {
        this.linkParsers = linkParsers
    }

    @Override
    List<Link> parse(def html) {
        List<Link> links = new ArrayList<>()
        
        for (linkParser in linkParsers) {
            links.addAll(linkParser.parse(html))
        }
        
        links
    }
}
