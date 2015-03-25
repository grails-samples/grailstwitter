package org.grails.twitter

import grails.web.api.ServletAttributes
import org.grails.twitter.auth.Person

class TwitterSecurityService implements ServletAttributes {

    def getPrincipal() {
        def context = session.SPRING_SECURITY_CONTEXT
        context?.authentication.principal
    }

    Person getCurrentUser() {
        Person.findByUserName(principal.username)
    }
}
