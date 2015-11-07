package com.raulavila.setup

import spock.lang.Specification

class HookUpSpec extends Specification {

    def "is this thing on"() {
        expect: "can run Spock specifications using production code"
        new HookUp()
    }
}
