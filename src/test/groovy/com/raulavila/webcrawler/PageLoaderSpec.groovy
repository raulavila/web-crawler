package com.raulavila.webcrawler

import spock.lang.Specification

class PageLoaderSpec extends Specification {

    def "load Google home page"() {
        given: "we have a valid instance of PageLoader"
        PageLoader pageLoader = new PageLoader()

        when: "we load Google home page"
        def html = pageLoader.load("http://www.google.com")
        
        then: "the object returned is a valid parseable HTML object"
        html
        and: "it contains a tag title with the text 'Google'"
        findTag(html, "title").text() == "Google"
    }

    private def findTag(html, name) {
        def tags = html."**".findAll {
            it.name().toUpperCase() == name.toUpperCase()
        }
        
        assert tags.size() == 1
        
        tags[0]
    }
}