package com.raulavila.webcrawler

import com.raulavila.webcrawler.links.CompositeLinkParser
import com.raulavila.webcrawler.links.ConcreteLinkParser
import com.raulavila.webcrawler.links.LinkParser
import com.raulavila.webcrawler.links.LinkType
import com.raulavila.webcrawler.load.PageLoader

class Main {

    public static void main(String[] args) {
        PageLoader pageLoader1 = new PageLoader()

        List<LinkParser> linkParsers = new ArrayList<>()

        for (LinkType linkType : LinkType.values()) {
            LinkParser linkParser = new ConcreteLinkParser(linkType)
            linkParsers << linkParser
        }

        CompositeLinkParser compositeLinkParser = new CompositeLinkParser(linkParsers)

        def crawler = new Crawler(pageLoader1, compositeLinkParser)
        def crawl = crawler.crawl("http://wiprodigital.com")

        new File('output.html').write(crawl)
    }
}
