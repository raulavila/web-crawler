package com.raulavila.webcrawler

import com.raulavila.webcrawler.links.Link
import com.raulavila.webcrawler.links.LinkParser
import com.raulavila.webcrawler.links.LinkType
import com.raulavila.webcrawler.load.PageLoader
import spock.lang.Specification
import spock.lang.Subject

class CrawlerSpec extends Specification {

    @Subject
    Crawler crawler

    PageLoader pageLoader = Mock()
    LinkParser linkParser = Mock()
    
    Object rootPage = Mock()
    Object page1 = Mock()
    
    def setup() {
        crawler = new Crawler(pageLoader, linkParser)

        pageLoader.load("http://root") >> rootPage
        pageLoader.load("http://root/page1") >> page1
    }

    def "test"() {
        given: "root contains a link to page1"
        Set<Link> linksRoot = [new Link(type: LinkType.NORMAL, url: "/page1")]
        linkParser.parse(rootPage) >> linksRoot
        and: "page1 contains a link to root"
        Set<Link> linksPage1 = [new Link(type: LinkType.NORMAL, url: "http://root")]
        linkParser.parse(page1) >> linksPage1
        
        when: "we crawl root"
        def siteMap = crawler.crawl("http://root")
        
        then: "the siteMap of page1 contains a list with one link to page2"
        siteMap ==
        """<html>
  <title>Map of the site http://root</title>
  <body>
    <h1>Map of the site http://root</h1>
    <ul>
      <li>
        <span>normal: </span>
        <a href='http://root/page1'>http://root/page1</a>
      </li>
      <ul>
        <li>
          <span>normal: </span>
          <a href='http://root'>http://root</a>
        </li>
      </ul>
    </ul>
  </body>
</html>"""
    
    }
    
    
}