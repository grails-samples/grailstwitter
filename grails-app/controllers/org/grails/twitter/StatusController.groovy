package org.grails.twitter

import grails.plugins.springsecurity.Secured

import org.grails.twitter.auth.Person

@Secured('IS_AUTHENTICATED_FULLY')
class StatusController {

    def statusService

    def index() {
        def messages = statusService.currentUserTimeline()
        [statusMessages: messages]
    }

    def updateStatus(String message) {
        statusService.updateStatus message
        def messages = statusService.currentUserTimeline()
        
        def content = twitter.renderMessages messages: messages
        render content
    }

    def follow(long id) {
        statusService.follow id
        redirect action: 'index'
    }
}
