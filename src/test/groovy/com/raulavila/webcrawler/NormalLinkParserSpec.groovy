package com.raulavila.webcrawler

import spock.lang.Specification
import spock.lang.Subject

class NormalLinkParserSpec extends Specification {

    @Subject
    NormalLinkParser linkParser = new NormalLinkParser()
    
    def "Try parsing normal links from page without links"() {
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
        List<String> links = linkParser.parse(html)
        
        then: "an empty list is returned"
        links.isEmpty()
    }

    def "Parse normal links from page with one links"() {
        given: "We have an HTML page with no links"
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
        List<String> links = linkParser.parse(html)

        then: "an empty list is returned"
        links.size() == 1
        links.contains("http://www.google.com")
    }

    def "Parse normal links from page with more than one link"() {
        given: "We have an HTML page with no links"
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
        List<String> links = linkParser.parse(html)

        then: "an empty list is returned"
        links.size() == 3
        links.contains("http://www.google.com")
        links.contains("http://www.raulavila.com")
        links.contains("http://wiprodigital.com")
    }
}