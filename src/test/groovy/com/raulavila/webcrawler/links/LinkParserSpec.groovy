package com.raulavila.webcrawler.links

import spock.lang.Specification
import spock.lang.Subject

class LinkParserSpec extends Specification {

    @Subject
    ConcreteLinkParser linkParser = new ConcreteLinkParser(LinkType.NORMAL)
    
    def "Try parsing links from page without links"() {
        given: "We have an HTML page with no links"
        def html = new XmlSlurper().parseText(
            """
            <html>
                <body>
                    <h1>Hello</h1>
                </body>
            </html>
            """
        )
        
        when: "We try to parse links from this page"
        Set<Link> links = linkParser.parse(html)
        
        then: "an empty list is returned"
        links.isEmpty()
    }

    def "Parse links from page with one link"() {
        given: "We have an HTML page with one link"
        def html = new XmlSlurper().parseText(
            """
            <html>
                <body>
                    <h1>Hello</h1>
                    <a href='http://www.google.com'>Google site</a>
                </body>
            </html>
            """
        )

        when: "We try to parse links from this page"
        Set<Link> links = linkParser.parse(html)

        then: "a list with one link is returned"
        links.size() == 1
        and: "the link contains the information extracted from the page"
        links.contains(new Link(type: LinkType.NORMAL, url: "http://www.google.com"))
    }

    def "Parse normal links from page with more than one link"() {
        given: "We have an HTML page with more than one link"
        def html = new XmlSlurper().parseText(
            """
            <html>
                <body>
                    <h1>Hello</h1>
                    <a href='http://www.google.com'>Google site</a>
                    <a href='http://www.raulavila.com'>Personal site</a>
                    <a href='http://wiprodigital.com'>Wipro Digital site</a>
                </body>
            </html>
            """
        )

        when: "We try to parse links from this page"
        Set<Link> links = linkParser.parse(html)

        then: "a list with 3 links is returned"
        links.size() == 3
        and: "the links contain the information extracted from the page"
        links.contains(new Link(type: LinkType.NORMAL, url: "http://www.google.com"))
        links.contains(new Link(type: LinkType.NORMAL, url: "http://www.raulavila.com"))
        links.contains(new Link(type: LinkType.NORMAL, url: "http://wiprodigital.com"))
    }
}