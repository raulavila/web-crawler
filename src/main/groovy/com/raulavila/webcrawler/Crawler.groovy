package com.raulavila.webcrawler

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

    Crawler(PageLoader pageLoader, LinkParser linkParser) {
        this.linkParser = linkParser
        this.pageLoader = pageLoader
    }

    String crawl(String rootUrl) {
        def writer = new StringWriter()
        def markup = new MarkupBuilder(writer)

        Set<Link> visitedLinks = new HashSet<>()
        visitedLinks.add(new Link(type: LinkType.NORMAL, url: rootUrl))

        String pageTitle = "Map of the site $rootUrl"
        
        markup.html {
            title(pageTitle)
            body {
                h1(pageTitle)
                doCrawl(rootUrl, rootUrl, markup, visitedLinks)
            }
        }

        writer.toString()
    }

    String doCrawl(String url, String rootUrl, def markup, Set<Link> visitedLinks) {
        def html = pageLoader.load(url)
        
        Set<Link> links = linkParser.parse(html)

        markup.ul {
            for(link in links) {
                li {
                    span("$link.type.description: ")
                    a(href:"$link.url","$link.url" )
                }

                if (shouldVisitLink(link, rootUrl, visitedLinks)) {
                    visitedLinks.add(link)
                    
                    try {
                        doCrawl(link.url, rootUrl, markup, visitedLinks)
                    } catch(Exception e){
                        log.warn("Problems visiting page " + link.url)
                    }
                }
            }
        }
    }

    private boolean shouldVisitLink(Link link, String rootUrl, Set<Link> visitedLinks) {
        link.type == LinkType.NORMAL &&
                link.url.contains(rootUrl) &&
                !visitedLinks.contains(link)
    }
}
