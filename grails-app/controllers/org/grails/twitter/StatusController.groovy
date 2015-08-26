package org.grails.twitter

import grails.plugin.springsecurity.annotation.Secured

@Secured('isAuthenticated()')
class StatusController {

    def statusService
    def timelineService

    def index() {
        def messages = timelineService.getTimelineForUser()
        [statusMessages: messages]
    }

    def updateStatus(String message) {
        statusService.updateStatus message
        def messages = timelineService.getTimelineForUser()

        def content = twitter.renderMessages messages: messages
        render content
    }
}
