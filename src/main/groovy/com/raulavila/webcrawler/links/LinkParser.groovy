package com.raulavila.webcrawler.links

interface LinkParser {
    Set<Link> parse(def html)
}