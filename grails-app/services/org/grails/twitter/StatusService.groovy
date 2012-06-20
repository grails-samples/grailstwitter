package org.grails.twitter

import org.grails.twitter.auth.Person

class StatusService {

    static expose = ['jms']

    def springSecurityService
    def twitterCache

    void onMessage(newMessageUserName) {
        log.debug "Message received. New status message posted by user <${newMessageUserName}>."
        def following = Person.where {
            followed.username == newMessageUserName
        }.property('username').list()
        
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

    def currentUserTimeline() {
        def messages = twitterCache[springSecurityService.principal.username]
        if (!messages) {
            log.debug "No messages found in cache for user <${springSecurityService.principal.username}>. Querying database..."
            def per = lookupPerson()
            def query = Status.whereAny {
                author { username == per.username }
                if(per.followed) author in per.followed
            }.order 'dateCreated', 'desc'
            messages = query.list(max: 10)
            
            twitterCache[springSecurityService.principal.username] = messages
        }
        else {
            log.debug "Status messages loaded from cache for user <${springSecurityService.principal.username}>."
        }
        messages
    }
    
    private lookupPerson() {
        Person.get(springSecurityService.principal.id)
    }
}
