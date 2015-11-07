package com.raulavila.webcrawler

import com.raulavila.webcrawler.links.CompositeLinkParser
import com.raulavila.webcrawler.links.ConcreteLinkParser
import com.raulavila.webcrawler.links.Link
import com.raulavila.webcrawler.links.LinkParser
import com.raulavila.webcrawler.links.LinkType
import com.raulavila.webcrawler.load.PageLoader
import groovy.util.logging.Slf4j
import groovy.xml.MarkupBuilder

@Slf4j
class Crawler {
    LinkParser linkParser
    PageLoader pageLoader

    Set<Link> visitedLinks = new HashSet<>()
    
    Crawler(PageLoader pageLoader, LinkParser linkParser) {
        this.linkParser = linkParser
        this.pageLoader = pageLoader
    }

    String crawl(String rootUrl) {
        def writer = new StringWriter()
        def markup = new MarkupBuilder(writer)
     
        markup.html {
            body {
                doCrawl(rootUrl, rootUrl, markup)
            }
        }

        writer.toString()
    }

    String doCrawl(String url, String rootUrl, def markup) {
        def html = pageLoader.load(url)
        
        List<Link> links = linkParser.parse(html)

        markup.ul {
            for(link in links) {
                li("($link.type.description): $link.url")

                if (shouldVisitLink(link, rootUrl)) {
                    visitedLinks.add(link)
                    
                    try {
                        doCrawl(link.url, rootUrl, markup)
                    } catch(Exception e){
                        log.warn("Problems visiting page " + link.url)
                    }
                }
            }
        }
    }

    boolean shouldVisitLink(Link link, String rootUrl) {
        link.type == LinkType.NORMAL &&
                link.url.contains(rootUrl) &&
                !visitedLinks.contains(link)
    }

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

        File file1 = new File('output.html')
        file1 << crawl
    }
}
