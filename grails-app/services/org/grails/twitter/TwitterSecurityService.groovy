package org.grails.twitter

import org.grails.twitter.auth.Person
import org.springframework.security.core.context.SecurityContextHolder

class TwitterSecurityService {

    def getPrincipal() {
        SecurityContextHolder.context?.authentication?.principal
    }

    Person getCurrentUser() {
        Person.findByUserName(principal.username)
    }
}
