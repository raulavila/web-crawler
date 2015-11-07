package com.raulavila.webcrawler.links

import spock.lang.Specification

class LinkConfigSpec extends Specification {

    def "Extract normal link from page"() {
        given: "We instantiate a LinkParser for normal links"
        ConcreteLinkParser linkParser = new ConcreteLinkParser(LinkType.NORMAL)
        and: "we have an HTML page with one normal link and one anchor"
        def html = new XmlSlurper().parseText(
            """
            <html>
                <body>
                    <a href='http://www.google.com'>Google site</a>
                    <a href='#anchor'>Anchor</a>
                </body>
            </html>
            """
        )

        when: "We try to parse links from this page"
        List<Link> links = linkParser.parse(html)

        then: "a list with one link is returned"
        links.size() == 1
        and: "the link contains the information extracted from the page"
        links.contains(new Link(type: LinkType.NORMAL, url: "http://www.google.com"))
    }

    def "Extract image links from page"() {
        given: "We instantiate a LinkParser for image links"
        ConcreteLinkParser linkParser = new ConcreteLinkParser(LinkType.IMAGE)
        and: "we have an HTML page with one image"
        def html = new XmlSlurper().parseText(
            """
            <html>
                <body>
                    <img src="image.jpg"/>
                </body>
            </html>
            """
        )

        when: "We try to parse links from this page"
        List<Link> links = linkParser.parse(html)

        then: "a list with one link is returned"
        links.size() == 1
        and: "the link contains the information extracted from the page"
        links.contains(new Link(type: LinkType.IMAGE, url: "image.jpg"))
    }

    def "Extract CSS links from page"() {
        given: "We instantiate a LinkParser for CSS links"
        ConcreteLinkParser linkParser = new ConcreteLinkParser(LinkType.CSS)
        and: "we have an HTML page with one stylesheet link"
        def html = new XmlSlurper().parseText(
                """
            <html>
                <link rel='stylesheet' href='stylesheet.css'/>
            </html>
            """
        )

        when: "We try to parse links from this page"
        List<Link> links = linkParser.parse(html)

        then: "a list with one link is returned"
        links.size() == 1
        and: "the link contains the information extracted from the page"
        links.contains(new Link(type: LinkType.CSS, url: "stylesheet.css"))
    }
}
