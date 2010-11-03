package org.grails.twitter

import org.grails.twitter.auth.Person

class StatusService {

    static expose = ['jms']

    def springSecurityService
    def twitterCache

    void onMessage(username) {
        log.debug "Message received. New status message posted by user <${username}>."
        def following = Person.withCriteria {
            projections {
                property 'username'
            }
            followed {
                eq 'username', username
            }
        }

        following.each { uname ->
            twitterCache.remove uname
        }
    }

    void updateStatus(String message) {
        def status = new Status(message: message)
        status.author = lookupPerson()
        status.save()

        twitterCache.remove springSecurityService.principal.username
    }

    void follow(long personId) {
        def person = Person.get(personId)
        if (person) {
            def currentUser = lookupPerson()
            currentUser.addToFollowed(person)

            twitterCache.remove springSecurityService.principal.username
        }
    }

    private lookupPerson() {
        Person.get(springSecurityService.principal.id)
    }
}
