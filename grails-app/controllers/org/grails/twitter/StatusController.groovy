package org.grails.twitter

import org.grails.twitter.auth.Person

class StatusController {

    def twitterCache
    def springSecurityService
    
    def index = { 
        def messages = currentUserTimeline()
        [statusMessages: messages]
    }
    
    private lookupPerson() {
        Person.get(springSecurityService.principal.id)
    }

    private currentUserTimeline() {
        def messages = twitterCache[springSecurityService.principal.username]
        if(!messages) {
            log.debug "No messages found in cache for user <${springSecurityService.principal.username}>. Querying database..."
            def per = lookupPerson()
            messages = Status.withCriteria {
                or {
                    author {
                        eq 'username', per.username
                    }
                    if(per.followed) {
                        inList 'author', per.followed
                    }
                }
                maxResults 10
                order 'dateCreated', 'desc'
            }
            twitterCache[springSecurityService.principal.username] = messages
        } else {
            log.debug "Status messages loaded from cache for user <${springSecurityService.principal.username}>."
        }
        messages
    }
    
    def updateStatus = {
        def status = new Status(message: params.message)
        status.author = lookupPerson()
        status.save()
        twitterCache.remove springSecurityService.principal.username
        def messages = currentUserTimeline()
        render template: 'statusMessages', collection: messages, var: 'statusMessage'
    }
    
    def follow = {
        def per = Person.get(params.id)
        if(per) {
            def currentUser = lookupPerson()
            currentUser.addToFollowed(per)
            currentUser.save()
            twitterCache.remove springSecurityService.principal.username
        }
        redirect action: 'index'
    }
}
