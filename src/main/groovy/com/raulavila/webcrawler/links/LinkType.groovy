package com.raulavila.webcrawler.links

enum LinkType {
    NORMAL("normal",
            { 
             it.name().toUpperCase() == "A" &&
             !it.@href.text().isEmpty() &&
             !it.@href.text().startsWith("#")
            },
            { 
             new Link(type: LinkType.NORMAL,
                        url: it.@href.text()) 
            }),
    
    IMAGE("image",
          { 
            it.name().toUpperCase() == "IMG"
          },
          {
            new Link(type: LinkType.IMAGE,
                    url: it.@src.text())
          }),
    
    CSS("css",
        {
            it.name().toUpperCase() == "LINK" &&
            it.@rel == "stylesheet"
        },
        {
            new Link(type: LinkType.CSS,
                    url: it.@href.text())
        });
    

    final String description
    final Closure filterClosure
    final Closure extractClosure

    LinkType(String description,
             Closure filterClosure, 
             Closure extractClosure) {
        
        this.description = description
        this.extractClosure = extractClosure
        this.filterClosure = filterClosure
    }
}
