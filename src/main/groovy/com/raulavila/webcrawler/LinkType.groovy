package com.raulavila.webcrawler

enum LinkType {
    NORMAL({ it.name().toUpperCase() == "A" },
            { 
                new Link(type: "Normal",
                        url: it.@href.text()) 
            }),
    IMAGE({ it.name().toUpperCase() == "IMG" },
            {
                new Link(type: "Image",
                        url: it.@src.text())
            }),

    final Closure filterClosure
    final Closure extractClosure

    LinkType(Closure filterClosure, 
             Closure extractClosure) {
        this.extractClosure = extractClosure
        this.filterClosure = filterClosure
    }
}
