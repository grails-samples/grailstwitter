package org.grails.twitter

import grails.plugins.springsecurity.Secured

import org.grails.twitter.auth.Person

@Secured('IS_AUTHENTICATED_FULLY')
class StatusController {

    def springSecurityService
    def statusService
    def twitterCache

    def index() {
        def messages = currentUserTimeline()
        [statusMessages: messages]
    }

    def updateStatus(String message) {
        statusService.updateStatus message
        def messages = currentUserTimeline()
        render template: 'statusMessages', collection: messages, var: 'statusMessage'
    }

    def follow(long id) {
        statusService.follow id
        redirect action: 'index'
    }

    private lookupPerson() {
        Person.get(springSecurityService.principal.id)
    }

    private currentUserTimeline() {
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
}
