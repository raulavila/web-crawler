# Web Crawler

Web Crawler using Groovy

# Environment

I'm not a Groovy expert, but I thought it was the best choice for this kind of problem, given all the facilities it exposes to parse, generate and manipulate HTML content.

The build tool used is Gradle (v2.5), and the tests are developed using Spock.

# Usage

If Gradle is installed in the machine: 

* `gradle run -Parguments="['<rootUrl>']"` e.g. `gradle run -Parguments="['http://raulavila.com']"`

If Gradle is not installed the Gradle wrapper is included in the project, so it could be run in any computer having JVM 1.7 or higher (I run it using JDK 1.8):

* `./gradlew run -Parguments="['<rootUrl>']"` (Linux / OSX)
* `gradlew run -Parguments="['<rootUrl>']"` (Windows)

There's no error handling associated to the arguments (except the number, must be one). The output of the process is the file `siteMap.html` generated in the same folder.

# Decisions and Tradeoffs

This is not a final version of the project, and several improvements can be made. In the current version:

* We only considered as links tags `<a>` (excluding anchors, i.e. "#"), `<img>` and `<link rel='stylesheet'../>`. However, this configuration is easily configurable, just adding a new enum value in the file `LinkType`, with indications about how to filter the tag and how to configure the link resolved (see code for details)
* The Crawler assumes that a link like `<a href="/whatever"...>` is an internal link, so it prepends the root Url and navigates to it. I'm sure there are several considerations that must be made here, and this will fail sometimes
* The Crawler considers as different pages http://page1.com and http://www.page1.com
* Same thing if the url contains query parameters, i.e. http://page1.com?param1=value, it would be considered a different page from http://page1.com
* To avoid creating infinite loops, if a page has been visited is added to the map and ignored
* If there's an error loading the page it's logged and ignored for the time being. The Crawler tries to navigate to files too (e.g. PDF's), same thing happens, error logged but link added to the map
 
 I've tried to create a generic solution, so the only classes that should be modified in the future are `LinkType` and `Crawler`
 
 
 
 


