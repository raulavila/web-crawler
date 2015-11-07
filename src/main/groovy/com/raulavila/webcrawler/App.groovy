package com.raulavila.webcrawler

import com.raulavila.webcrawler.links.CompositeLinkParser
import com.raulavila.webcrawler.links.ConcreteLinkParser
import com.raulavila.webcrawler.links.LinkParser
import com.raulavila.webcrawler.links.LinkType
import com.raulavila.webcrawler.load.PageLoader

class App {
    public static void main(String[] args) {
        if (args.length != 1) {
            println "Incorrect arguments! Usage: gradlew run <rootUri>"
            System.exit(0)
        }

        String rootUrl = args[0]
        
        Crawler crawler = getCrawler()
        String crawl = crawler.crawl(rootUrl)

        new File("siteMap.html").write(crawl)
        
        println "SiteMap generated in file siteMap.html"
    }

    private static Crawler getCrawler() {
        PageLoader pageLoader = new PageLoader()

        List<LinkParser> linkParsers = new ArrayList<>()
        for (LinkType linkType : LinkType.values()) {
            LinkParser linkParser = new ConcreteLinkParser(linkType)
            linkParsers << linkParser
        }
        CompositeLinkParser compositeLinkParser = new CompositeLinkParser(linkParsers)

        Crawler crawler = new Crawler(pageLoader, compositeLinkParser)
        crawler
    }
}
