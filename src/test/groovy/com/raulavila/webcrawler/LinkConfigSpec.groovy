package com.raulavila.webcrawler

import spock.lang.Specification

class LinkConfigSpec extends Specification {

    def "Extract normal link from page"() {
        given: "We instantiate a LinkParser for normal links"
        LinkParser linkParser = new LinkParser(LinkType.NORMAL)
        and: "we have an HTML page with one normal link"
        def html = new XmlSlurper().parseText(
            """
            <html>
                <body>
                    <a href='http://www.google.com'>Google site</a>
                </body>
            </html>
            """
        )

        when: "We try to parse links from this page"
        List<String> links = linkParser.parse(html)

        then: "a list with one link is returned"
        links.size() == 1
        and: "the link contains the information extracted from the page"
        links.contains(new Link(type: "Normal", url: "http://www.google.com"))
    }

    def "Extract image links from page"() {
        given: "We instantiate a LinkParser for image links"
        LinkParser linkParser = new LinkParser(LinkType.IMAGE)
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
        List<String> links = linkParser.parse(html)

        then: "a list with one link is returned"
        links.size() == 1
        and: "the link contains the information extracted from the page"
        links.contains(new Link(type: "Image", url: "image.jpg"))
    }
}
