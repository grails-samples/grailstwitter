package org.grails.twitter

import org.grails.twitter.auth.Person

class TwitterSecurityService {

    def springSecurityService

    Person getCurrentUser() {
        springSecurityService.currentUser
    }
}
