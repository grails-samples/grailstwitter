package org.grails.twitter.pages

import geb.Module

class Status extends Module {
    
    static content = {
        message { $('.statusMessage').text() }
        author { $('.author').text() }
    }
}
