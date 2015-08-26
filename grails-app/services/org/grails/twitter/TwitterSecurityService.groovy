package org.grails.twitter

import org.grails.twitter.auth.Person

class TwitterSecurityService {

    static transactional = false

    def springSecurityService

    Person getCurrentUser() {
        springSecurityService.currentUser
    }

    String getCurrentUsername() {
        springSecurityService.principal.username
    }

    Person loadCurrentUser() {
        Person.load springSecurityService.principal.id
    }
}
