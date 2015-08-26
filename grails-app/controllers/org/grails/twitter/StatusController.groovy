package org.grails.twitter

import grails.plugin.springsecurity.annotation.Secured

@Secured('isAuthenticated()')
class StatusController {

    def statusService
    def timelineService
    def springSecurityService

    def index() {
        def messages = timelineService.getTimelineForUser(springSecurityService.principal.username)
        [statusMessages: messages]
    }

    def updateStatus(String message) {
        statusService.updateStatus message
        def messages = timelineService.getTimelineForUser(springSecurityService.principal.username)

        def content = twitter.renderMessages messages: messages
        render content
    }
}
