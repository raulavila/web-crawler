package com.raulavila.webcrawler.links

interface LinkParser {
    List<Link> parse(def html)
}