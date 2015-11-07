package com.raulavila.webcrawler.load

import groovyx.net.http.HTTPBuilder

class PageLoader {
    def load(String url) {
        def http = new HTTPBuilder(url)
        def html = http.get([:])
        html
    }
}
