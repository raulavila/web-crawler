package com.raulavila.webcrawler

class NormalLinkParser {
    List<String> parse(def html) {
        html."**".findAll {
            it.name().toUpperCase() == "A"
        }
        .collect {
            it.@href.text()
        }
    }
}
