package com.raulavila.webcrawler.links

import spock.lang.Specification
import spock.lang.Subject

class CompositeLinkParserSpec extends Specification {

    @Subject
    CompositeLinkParser compositeLinkParser
    
    def setup() {
        List<LinkParser> linkParsers = new ArrayList<>()
        
        for (LinkType linkType : LinkType.values()) {
            LinkParser linkParser = new ConcreteLinkParser(linkType)
            linkParsers << linkParser
        }
        
        compositeLinkParser = new CompositeLinkParser(linkParsers)
    }

    def "Parse different types of links from page"() {
        given: "We have an HTML page with several types of links"
        def html = new XmlSlurper().parseText(
            """
            <html>
                <link rel='stylesheet' href='stylesheet.css'/>
                <body>
                    <h1>Hello</h1>
                    <a href='http://www.google.com'>Google site</a>
                    <img src="image.jpg"/>
                </body>
            </html>
            """
        )

        when: "We try to parse links from this page"
        Set<Link> links = compositeLinkParser.parse(html)

        then: "a list with 3 links is returned"
        links.size() == 3
        and: "the links contain the information extracted from the page"
        links.contains(new Link(type: LinkType.CSS, url: "stylesheet.css"))
        links.contains(new Link(type: LinkType.NORMAL, url: "http://www.google.com"))
        links.contains(new Link(type: LinkType.IMAGE, url: "image.jpg"))
    }

}