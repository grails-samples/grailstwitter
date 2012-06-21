package org.grails.twitter

import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable

import org.grails.twitter.auth.Person

class StatusService {

    static expose = ['jms']

    def springSecurityService
    def grailsApplication
    
    void onMessage(newMessageUserName) {
        log.debug "Message received. New status message posted by user <${newMessageUserName}>."
        def following = Person.where {
            followed.username == newMessageUserName
        }.property('username').list()
        def service = grailsApplication.mainContext.statusService
        following.each { uname ->
            service.clearCacheForUser(uname)
        }
    }

    @CacheEvict('timeline')
    void clearCacheForUser(String username) {}
    
    void updateStatus(String message) {
        def status = new Status(message: message)
        status.author = lookupPerson()
        status.save()
        grailsApplication.mainContext.statusService.clearCacheForUser(status.author.username)
    }

    void follow(long personId) {
        def person = Person.get(personId)
        if (person) {
            def currentUser = lookupPerson()
            currentUser.addToFollowed(person)
            grailsApplication.mainContext.statusService.clearCacheForUser(currentUser.username)
        }
    }

    def currentUserTimeline() {
        grailsApplication.mainContext.statusService.timelineForUser springSecurityService.principal.username
    }
    
    @Cacheable('timeline')
    def timelineForUser(String username) {
        println "Getting timeline for $username"
        def per = lookupPerson(username)
        def query = Status.whereAny {
            author { username == per.username }
            if(per.followed) author in per.followed
        }.order 'dateCreated', 'desc'
        def messages = query.list(max: 10)
        messages
    }
    
    def lookupPerson(String username) {
        Person.findByUsername username
    }
    
    private lookupPerson() {
        Person.get(springSecurityService.principal.id)
    }
}
