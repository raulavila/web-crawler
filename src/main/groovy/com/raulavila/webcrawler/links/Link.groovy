package com.raulavila.webcrawler.links

import groovy.transform.Canonical

@Canonical
class Link {
    LinkType type
    String url
}
