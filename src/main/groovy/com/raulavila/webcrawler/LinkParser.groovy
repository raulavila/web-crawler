package com.raulavila.webcrawler

interface LinkParser {
    List<Link> parse(def html)
}