package org.grails.twitter

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable

import org.grails.twitter.auth.Person

class StatusService {

    static expose = ['jms']

    def springSecurityService
    def timelineService
    
    void onMessage(newMessageUserName) {
        log.debug "Message received. New status message posted by user <${newMessageUserName}>."
        def following = Person.where {
            followed.username == newMessageUserName
        }.property('username').list()
        following.each { uname ->
            timelineService.clearTimelineCacheForUser(uname)
        }
    }

    void updateStatus(String message) {
        def status = new Status(message: message)
        status.author = lookupCurrentPerson()
        status.save()
        timelineService.clearTimelineCacheForUser(status.author.username)
    }

    void follow(long personId) {
        def person = Person.get(personId)
        if (person) {
            def currentUser = lookupCurrentPerson()
            currentUser.addToFollowed(person)
            timelineService.clearTimelineCacheForUser(currentUser.username)
        }
    }

    private lookupCurrentPerson() {
        Person.get(springSecurityService.principal.id)
    }
}
